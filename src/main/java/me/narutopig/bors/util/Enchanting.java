package me.narutopig.bors.util;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static me.narutopig.bors.util.General.toRoman;

public class Enchanting {
    public static boolean hasEnchant(ItemStack item, Enchantment enchantment) {
        return item.getEnchantments().get(enchantment) != null;
    }

    public static boolean hasEnchant(Map<Enchantment, Integer> enchants, Enchantment enchantment) {
        return enchants.get(enchantment) != null;
    }

    public static boolean hasConflict(Enchantment enchantment, Map<Enchantment, Integer> enchants) {
        for (Enchantment e : enchants.keySet()) {
            if (enchantment.conflictsWith(e)) return true;
        }
        return false;
    }

    public static int calculateLevel(Enchantment e, int a, int b) {
        /*
        Calculates result of combining the two levels
         */
        int res;
        if (a != b) res = Math.max(a, b);
        else res = a + 1;
        return Math.min(res, e.getMaxLevel());
    }

    public static boolean canEnchant(ItemStack item, Enchantment enchantment) {
        return enchantment.canEnchantItem(item) && enchantment.getItemTarget().includes(item);
    }

    public static void addEnchant(ItemStack item, Enchantment enchantment, int level) {
        if (!canEnchant(item, enchantment)) return;
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null) return;

        List<String> lore = itemMeta.getLore();

        if (lore == null) lore = new ArrayList<>();

        if (hasEnchant(item, enchantment)) {
            int i = 0;
            for (String line : lore) {
                if (ChatColor.stripColor(line).split(" ")[0].equals(enchantment.getName())) {
                    String newLine = ChatColor.GREEN + enchantment.getName() + " " + toRoman(level);
                    lore.set(i, newLine);
                    break;
                }
                i++;
            }
        } else {
            String newLine = ChatColor.GREEN + enchantment.getName() + " " + toRoman(level);
            lore.add(newLine);
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        item.removeEnchantment(enchantment);
        item.addEnchantment(enchantment, level);
    }
}
