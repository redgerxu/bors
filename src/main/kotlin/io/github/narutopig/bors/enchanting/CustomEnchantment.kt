package io.github.narutopig.bors.enchanting

import io.github.narutopig.bors.util.General
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class CustomEnchantment {
    val key: String
    val name: String
    val maxLevel: Int
    private val cost: ItemStack
//    private var target: EnchantmentTarget

    constructor(
        key: String,
        name: String,
        maxLevel: Int,
        cost: ItemStack
    ) {
        this.key = key
        this.name = name
        this.maxLevel = maxLevel
        this.cost = cost
    }

    fun getCost(level: Int): ItemStack {
        val newLevel = General.power(2, level - 1) * this.cost.amount
        val newCost = cost.clone()
        newCost.amount = newLevel
        return newCost
    }

    /*
    fun getItemTarget(): EnchantmentTarget {
        return target
    }
    */

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