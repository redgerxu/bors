package io.github.narutopig.bors.enchanting

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object CustomEnchants {
    val EXPERIENCE = CustomEnchantment(
        "experience",
        "Experience",
        3,
        ItemStack(Material.LAPIS_BLOCK, 2)
    )

    val POISON = CustomEnchantment(
        "poison",
        "Poison",
        3,
        ItemStack(Material.SPIDER_EYE, 4)
    )

    // might be removed later
    val TELEKINESIS = CustomEnchantment(
        "telekinesis",
        "Telekinesis",
        1,
        ItemStack(Material.ENDER_PEARL, 4)
    )

    val AUTOMINER = CustomEnchantment(
        "autominer",
        "Auto Miner",
        3,
        ItemStack(Material.REDSTONE_BLOCK, 4)
    )

    val customEnchants: MutableList<CustomEnchantment> = mutableListOf(
        EXPERIENCE,
        POISON,
        TELEKINESIS,
        AUTOMINER
    )

    fun isCustomEnchantment(e: Enchantment): Boolean {
        return customEnchants.any { it.key == e.key.key }
    }

    fun getCustomEnchant(key: String, case: Boolean = false): CustomEnchantment {
        if (case) return customEnchants.first { it.key == key }
        return customEnchants.first { it.key.lowercase() == key.lowercase() }
    }

    fun getCustomEnchantByName(name: String, case: Boolean = false): CustomEnchantment {
        if (case) return customEnchants.first { it.name == name }
        return customEnchants.first { it.name.lowercase() == name.lowercase() }
    }


    fun hasEnchant(item: ItemStack, enchant: CustomEnchantment): Boolean {
        val enchants = Enchanting.getItemCustomEnchants(item)
        val level = enchants[enchant] ?: return false
        return level > 0
    }
}