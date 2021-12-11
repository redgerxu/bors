package me.narutopig.bors.commands;

import me.narutopig.bors.CustomEnchants;
import me.narutopig.bors.EnchantmentWrapper;
import me.narutopig.bors.enchantments.CostData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static me.narutopig.bors.Util.*;

public class EnchantCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand.getAmount() == 0) {
                player.sendMessage(ChatColor.RED + "You need to hold an item to use this command.");
                return false;
            }

            Map<EnchantmentWrapper, Integer> toBeAdded = getArguments(args); // stuff to add
            Map<Enchantment, Integer> handEnchants = hand.getEnchantments(); // current enchants on item
            // enchantmentwrapper, reason
            Map<EnchantmentWrapper, String> ignored = new HashMap<>(); // ignored stuffs (for debug mainly i guess)
            final String cannotEnchant = "this item cannot have this enchantment.";
            final String cannotAfford = "you do not have the required materials for apply this enchantment.";
            final String alreadyContains = "this item already has this enchantment at a higher level.";
            final String conflicts = "this enchantment conflicts with another enchantment on this item.";

            List<EnchantmentWrapper> toBeRemoved = new ArrayList<>();
            List<Enchantment> handEnchantList = handEnchants.keySet().stream().toList();

            // gets all the enchants that need to be ignored
            for (Map.Entry<EnchantmentWrapper, Integer> entry : toBeAdded.entrySet()) {
                EnchantmentWrapper e = entry.getKey();
                int level = entry.getValue();

                // is there a higher level
                if (level < handEnchants.getOrDefault(e, 0)) {
                    toBeRemoved.add(e);
                    ignored.put(e, alreadyContains);
                    continue;
                }

                // can the item be enchanted
                if (!canEnchant(hand, e)) {
                    toBeRemoved.add(e);
                    ignored.put(e, cannotEnchant);
                    continue;
                }

                // can the player afford
                if (e.getCost() != null && getCostData(e, player.getInventory(), level) == null) {
                    toBeRemoved.add(e);
                    ignored.put(e, cannotAfford);
                }

                // are there any conflicts
                for (Enchantment enchantment : handEnchantList) {
                    if (e.conflictsWith(enchantment)) {
                        toBeRemoved.add(e);
                        ignored.put(e, conflicts);
                        break;
                    }
                }
            }

            // remove all enchantments that should not be there
            for (EnchantmentWrapper e : toBeRemoved) {
                toBeAdded.remove(e);
            }

            for (Map.Entry<EnchantmentWrapper, Integer> entry : toBeAdded.entrySet()) {
                EnchantmentWrapper e = entry.getKey();
                CostData costData = getCostData(e, player.getInventory(), entry.getValue());

                if (costData != null) {
                    int[] indices = costData.indices;

                    for (int i : indices) {
                        player.getInventory().clear(i);
                    }

                    int overflow = costData.lastOverflow;

                    if (overflow != 0) {
                        player.getInventory().setItem(indices[indices.length - 1], new ItemStack(e.getCost().getType(), overflow));
                    }
                }
            }

            for (Map.Entry<EnchantmentWrapper, Integer> entry : toBeAdded.entrySet()) {
                EnchantmentWrapper e = entry.getKey();
                int level = entry.getValue();
                int newLevel = calculateLevel(e, level, handEnchants.getOrDefault(e, 0));
                addEnchant(hand, e, newLevel);
            }

            player.getInventory().setItemInMainHand(hand);

            StringBuilder message = new StringBuilder(ChatColor.GREEN + "Applied the following enchants:\n");

            for (Map.Entry<EnchantmentWrapper, Integer> entry : toBeAdded.entrySet()) {
                message.append("+ ")
                        .append(entry.getKey().getName())
                        .append(" ")
                        .append(toRoman(entry.getValue()))
                        .append("\n");
            }

            message.append(ChatColor.RED + "Ignored the following enchants:\n");

            for (Map.Entry<EnchantmentWrapper, String> entry : ignored.entrySet()) {
                message.append("- ")
                        .append(entry.getKey().getName())
                        .append(" because ")
                        .append(entry.getValue())
                        .append("\n");
            }

            player.sendMessage(message.toString());

            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command.");
            return false;
        }
    }

    // utility functions
    public static Map<EnchantmentWrapper, Integer> getArguments(String[] args) {
        // returns a map with all the enchants that need to be applied
        Map<EnchantmentWrapper, Integer> arguments = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            try {
                parseInt(args[i]); // illegal argument, so ignore it
            } catch (NumberFormatException e) {
                // its an enchant (poggers)
                EnchantmentWrapper enchant;
                try {
                    int finalI = i; // fuck you java also intellij sugondese nuts
                    enchant = (EnchantmentWrapper) CustomEnchants.customEnchants.stream().filter(pog -> pog.getName().equalsIgnoreCase(args[finalI])).toArray()[0];
                } catch (ArrayIndexOutOfBoundsException e1) {
                    // enchant is not a custom one
                    continue;
                }
                int level;
                try {
                    // next arg is a number
                    level = parseInt(args[i + 1]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e2) {
                    // next arg is not a number
                    level = 1;
                }
                arguments.put(enchant, level);
            }
        }

        return arguments;
    }

    @Nullable
    public static CostData getCostData(EnchantmentWrapper enchantmentWrapper, Inventory inventory, int level) {
        // returns null if cannot afford

        ItemStack cost = enchantmentWrapper.getCost(level);
        List<Integer> indices = new ArrayList<>();
        int lastOverflow = -1; // how much to remove

        ItemStack[] contents = inventory.getContents();
        int count = 0;

        int c = 0;
        for (ItemStack slot : contents) {
            if (slot == null || !slot.getType().equals(cost.getType())) {
                c++;
                continue;
            }

            count += slot.getAmount();
            indices.add(c);

            if (count >= cost.getAmount()) {
                lastOverflow = count - cost.getAmount();
                break;
            }

            c++;
        }

        if (lastOverflow == -1) {
            return null;
        }

        int[] poggies = new int[indices.size()];

        for (int i = 0; i < poggies.length; i++) {
            poggies[i] = indices.get(i);
        }

        return new CostData(poggies, lastOverflow);
    }
}