package io.github.narutopig.bors.util

import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Enchanting {
    fun hasEnchant(item: ItemStack, enchantment: Enchantment): Boolean {
        return item.enchantments[enchantment] != null
    }

    fun calculateLevel(e: Enchantment, a: Int, b: Int): Int {
        /*
        Calculates result of combining the two levels
         */
        val res: Int = if (a != b) a.coerceAtLeast(b) else a + 1
        return res.coerceAtMost(e.maxLevel)
    }

    fun canEnchant(item: ItemStack?, enchantment: Enchantment): Boolean {
        return enchantment.canEnchantItem(item!!) && enchantment.itemTarget.includes(item)
    }

    fun addEnchant(item: ItemStack, enchantment: Enchantment, level: Int) {
        if (!canEnchant(item, enchantment)) return
        val itemMeta = item.itemMeta ?: return
        var lore = itemMeta.lore
        if (lore == null) lore = ArrayList()
        if (hasEnchant(item, enchantment)) {
            var i = 0
            for (line in lore) {
                if (ChatColor.stripColor(line)!!.split(" ").toTypedArray()[0] == enchantment.name) {
                    val newLine = ChatColor.GREEN.toString() + enchantment.name + " " + General.toRoman(level)
                    lore[i] = newLine
                    break
                }
                i++
            }
        } else {
            val newLine = ChatColor.GREEN.toString() + enchantment.name + " " + General.toRoman(level)
            lore.add(newLine)
        }
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        item.removeEnchantment(enchantment)
        item.addEnchantment(enchantment, level)
    }
}