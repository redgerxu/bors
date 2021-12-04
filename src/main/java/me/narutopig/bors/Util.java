package me.narutopig.bors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Util {
    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

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
            System.out.println("Does not have enchant " + enchantment.getName());
            String newLine = ChatColor.GREEN + enchantment.getName() + " " + toRoman(level);
            lore.add(newLine);
        }

        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);

        item.removeEnchantment(enchantment);
        item.addEnchantment(enchantment, level);
    }

    public static Enchantment getCustomEnchant(String key) {
        for (Enchantment enchantment : CustomEnchants.customEnchants) {
            System.out.println(enchantment.getKey().getKey());
            if (enchantment.getKey().getKey().equals(key)) {
                return enchantment;
            }
        }
        return null;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
        }
    }

    public static ItemStack generateItem(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public static int power(int base, int exponent) {
        int res = 1;

        for (int i = 0; i < exponent; i++) {
            res *= base;
        }

        return res;
    }

    public static String toRoman(int number) {
        // thanks Ben-Hur Langoni Junior
        // https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }
}
