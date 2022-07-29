package io.github.narutopig.bors.enchanting

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.stream.Collectors

object CustomEnchants {
    val EXPERIENCE = EnchantmentWrapper(
        "experience",
        "Experience",
        3,
        ItemStack(Material.LAPIS_BLOCK, 2)
    )

    val POISON = EnchantmentWrapper(
        "poison",
        "Poison",
        3,
        ItemStack(Material.SPIDER_EYE, 4)
    )

    // might be removed later
    val TELEKINESIS = EnchantmentWrapper(
        "telekinesis",
        "Telekinesis",
        1,
        ItemStack(Material.ENDER_PEARL, 4)
    )

    val customEnchants: MutableList<EnchantmentWrapper> = mutableListOf(
        EXPERIENCE,
        POISON,
        TELEKINESIS
    )

    fun register() {
        val registered: MutableList<Boolean> = ArrayList()
        for (enchantment in customEnchants) {
            registered.add(Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchantment))
        }
        for (i in customEnchants.indices) {
            if (!registered[i]) registerEnchant(customEnchants[i])
        }
    }

    private fun registerEnchant(enchantment: Enchantment) {
        // definitely not copied
        var registered = true
        try {
            val f = Enchantment::class.java.getDeclaredField("acceptingNew")
            f.isAccessible = true
            f[null] = true
            Enchantment.registerEnchantment(enchantment)
        } catch (e: Exception) {
            registered = false
            e.printStackTrace()
        }
        if (registered) {
            println(enchantment.key.toString() + " was registered!")
        }
    }

    fun isCustomEnchantment(e: Enchantment): Boolean {
        return customEnchants.any { it.key == e.key }
    }

    fun getCustomEnchant(key: String, case: Boolean = false): EnchantmentWrapper {
        if (case) return customEnchants.first { it.key.key == key }
        return customEnchants.first { it.key.key.lowercase() == key.lowercase() }
    }
}