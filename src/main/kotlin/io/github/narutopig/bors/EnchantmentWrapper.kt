package io.github.narutopig.bors

import io.github.narutopig.bors.util.General
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack
import java.util.*
import javax.annotation.Nonnull

class EnchantmentWrapper : Enchantment {
    private val name: String
    private val maxLevel: Int
    private var conflicts: Array<Enchantment> = arrayOf()
    private var cost: ItemStack

    constructor(key: String, name: String, maxLevel: Int, cost: ItemStack, conflicts: Array<Enchantment>) : super(
        NamespacedKey.minecraft(
            key
        )
    ) {
        this.name = name
        this.maxLevel = maxLevel
        this.cost = cost
        this.conflicts = conflicts
    }

    constructor(key: String, name: String, maxLevel: Int, cost: ItemStack) : super(
        NamespacedKey.minecraft(
            key
        )
    ) {
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

    @Nonnull
    override fun getName(): String {
        return name
    }

    override fun getMaxLevel(): Int {
        return maxLevel
    }

    override fun getStartLevel(): Int {
        return 1
    }

    @Nonnull
    override fun getItemTarget(): EnchantmentTarget {
        return EnchantmentTarget.BREAKABLE
    }

    override fun isTreasure(): Boolean {
        return false
    }

    override fun isCursed(): Boolean {
        return false
    }

    override fun conflictsWith(@Nonnull other: Enchantment): Boolean {
        return Arrays.stream(conflicts).anyMatch { enchantment: Enchantment -> enchantment.key == other.key }
    }

    override fun canEnchantItem(@Nonnull item: ItemStack): Boolean {
        return true
    }
}