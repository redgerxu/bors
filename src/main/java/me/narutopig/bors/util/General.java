package me.narutopig.bors.util;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class General {
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

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println(entry.getKey().toString() + ":" + entry.getValue().toString());
        }
    }

    public static ItemStack itemStack(Material material, int amount) {
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

    public static void banPlayer(Player player, String reason, Date date, BanList.Type type) {
        Bukkit.getBanList(type).addBan(player.getName(), reason, date, "Console");
        player.kickPlayer("Banned for reason: " + reason);
    }
}
