package io.github.narutopig.bors.enchanting

import io.github.narutopig.bors.util.General
import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class EnchantmentWrapper(
    key: String,
    private val name: String,
    private val maxLevel: Int,
    private var cost: ItemStack,
    private var target: EnchantmentTarget,
) : Enchantment(
    NamespacedKey.minecraft(
        key
    )
) {

    constructor(
        key: String,
        name: String,
        maxLevel: Int,
        cost: ItemStack
    ) : this(key, name, maxLevel, cost, EnchantmentTarget.BREAKABLE)

    companion object {
        val color = ChatColor.GRAY
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
        return target
    }

    override fun isTreasure(): Boolean {
        return false
    }

    override fun isCursed(): Boolean {
        return false
    }

    override fun conflictsWith(@Nonnull other: Enchantment): Boolean {
        // TODO: Actually check conflicts or something
        return false
    }

    override fun canEnchantItem(@Nonnull item: ItemStack): Boolean {
        return true
    }
}