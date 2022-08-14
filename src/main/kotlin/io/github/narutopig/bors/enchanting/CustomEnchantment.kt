package io.github.narutopig.bors.enchanting

import io.github.narutopig.bors.util.General
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class CustomEnchantment(
    key: String,
    private val name: String,
    private val maxLevel: Int,
    private var cost: ItemStack,
    private var target: EnchantmentTarget,
) {

    constructor(
        key: String,
        name: String,
        maxLevel: Int,
        cost: ItemStack
    ) : this(key, name, maxLevel, cost, EnchantmentTarget.BREAKABLE)

    fun getCost(level: Int): ItemStack {
        val newLevel = General.power(2, level - 1) * this.cost.amount
        val newCost = cost.clone()
        newCost.amount = newLevel
        return newCost
    }

    fun getName(): String {
        return name
    }

    fun getMaxLevel(): Int {
        return maxLevel
    }

    fun getStartLevel(): Int {
        return 1
    }

    fun getItemTarget(): EnchantmentTarget {
        return target
    }

    fun isTreasure(): Boolean {
        return false
    }

    fun isCursed(): Boolean {
        return false
    }

    fun conflictsWith(@Nonnull other: Enchantment): Boolean {
        // TODO: Actually check conflicts or something
        return false
    }

    fun canEnchantItem(@Nonnull item: ItemStack): Boolean {
        return true
    }
}