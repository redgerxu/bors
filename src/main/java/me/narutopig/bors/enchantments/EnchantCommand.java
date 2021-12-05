package me.narutopig.bors.enchantments;

import me.narutopig.bors.CustomEnchants;
import me.narutopig.bors.EnchantmentWrapper;
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

class CostData {
    final int[] indices;
    final int lastOverflow;

    public CostData(int[] indices, int lastOverflow) {
        this.indices = indices;
        this.lastOverflow = lastOverflow;
    }
}

public class EnchantCommand implements CommandExecutor {
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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ItemStack hand = player.getInventory().getItemInMainHand();
            if (hand.getAmount() == 0) {
                player.sendMessage(ChatColor.RED + "You need to hold an item to use this command.");
                return false;
            }

            Map<EnchantmentWrapper, Integer> newEnchants = getArguments(args); // stuff to add
            Map<Enchantment, Integer> handEnchants = hand.getEnchantments(); // current enchants on item
            Map<EnchantmentWrapper, Integer> toBeAdded = new HashMap<>(); // stores the changes
            // enchantmentwrapper, reason
            Map<EnchantmentWrapper, String> ignored = new HashMap<>(); // ignored stuffs (for debug mainly i guess)
            final String cannotEnchant = "this item cannot have this enchant.";
            final String cannotAfford = "you do not have the required materials for this enchant.";

            for (Map.Entry<EnchantmentWrapper, Integer> entry : newEnchants.entrySet()) {
                EnchantmentWrapper enchantment = entry.getKey();
                int level = entry.getValue();
                if (!canEnchant(hand, enchantment)) {
                    ignored.put(entry.getKey(), cannotEnchant);
                    continue;
                }

                // calculated level
                int currentLevel = handEnchants.getOrDefault(enchantment, 0);
                int lvl = calculateLevel(enchantment, currentLevel, level);

                toBeAdded.put(enchantment, lvl);
            }

            List<EnchantmentWrapper> toBeRemoved = new ArrayList<>(); // b/c i cant modify map in this loop

            // checks if player can afford it
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
                } else {
                    toBeRemoved.add(e);
                    ignored.put(e, cannotAfford);
                }
            }

            for (EnchantmentWrapper e : toBeRemoved) {
                toBeAdded.remove(e);
            }

            for (Map.Entry<EnchantmentWrapper, Integer> entry : toBeAdded.entrySet()) {
                addEnchant(hand, entry.getKey(), entry.getValue());
            }

            player.getInventory().setItemInMainHand(hand);

            StringBuilder message = new StringBuilder(ChatColor.GREEN + "Applied the following enchants:\n");

            // wip
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
}
