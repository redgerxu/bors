package me.narutopig.bors;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

import static me.narutopig.bors.Util.power;


public class EnchantmentWrapper extends Enchantment {
    private final String name;
    private final int maxLevel;
    public ItemStack cost;

    public EnchantmentWrapper(String key, String name, int level) {
        super(NamespacedKey.minecraft(key));
        this.name = name;
        this.maxLevel = level;
    }

    public EnchantmentWrapper(String key, String name, int level, ItemStack cost) {
        super(NamespacedKey.minecraft(key));
        this.name = name;
        this.maxLevel = level;
        this.cost = cost;
    }

    public ItemStack getCost() {
        return cost;
    }

    public ItemStack getCost(int level) {
        int newLevel = power(2, level - 1);

        ItemStack newCost = cost.clone();

        newCost.setAmount(newLevel);

        return newCost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.BREAKABLE;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }
}