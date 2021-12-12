package io.github.narutopig

import io.github.narutopig.util.General
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import java.util.*
import java.util.stream.Collectors

object CustomEnchants {
    val EXPERIENCE = EnchantmentWrapper(
        "experience",
        "Experience",
        5,
        General.itemStack(Material.LAPIS_BLOCK, 1), arrayOf(Enchantment.MENDING)
    )
    val AFTERMATH = EnchantmentWrapper(
        "aftermath",
        "Aftermath",
        1,
        General.itemStack(Material.TNT, 1)
    )
    val TELEKINESIS = EnchantmentWrapper(
        "telekinesis",
        "Telekinesis",
        1,
        General.itemStack(Material.ENDER_PEARL, 16)
    )
    val customEnchants: MutableList<EnchantmentWrapper> = mutableListOf(
        AFTERMATH,
        EXPERIENCE,
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
}