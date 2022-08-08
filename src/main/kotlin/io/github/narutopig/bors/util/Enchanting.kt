package io.github.narutopig.bors.util

import io.github.narutopig.bors.enchanting.CustomEnchantment
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.util.General.romanToInteger
import io.github.narutopig.bors.util.General.toRoman
import org.bukkit.ChatColor
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

object Enchanting {
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

    fun addEnchant(itemStack: ItemStack, enchantment: CustomEnchantment, level: Int): ItemStack? {
        val item = itemStack.clone()
        if (!canEnchant(item, enchantment)) return null
        val itemMeta = item.itemMeta ?: return null
        val lore = ArrayList<String>()
        val enchants = getItemCustomEnchants(item)

        enchants[enchantment] = level

        for ((enchant, lvl) in enchants) {
            lore.add("${ChatColor.GRAY}${enchant.name} ${toRoman(lvl)}")
        }

        itemMeta.lore = lore
        item.itemMeta = itemMeta

        return item
    }

    // returns all custom enchants with level
    fun getItemCustomEnchants(item: ItemStack?): MutableMap<CustomEnchantment, Int> {
        val res = mutableMapOf<CustomEnchantment, Int>()

        if (item == null) return res
        val itemMeta = item.itemMeta ?: return res
        val lore = itemMeta.lore ?: return res
        if (lore.isEmpty()) return res

        for (line in lore) {
            val l = ChatColor.stripColor(line) ?: continue
            if (l.trim().isEmpty()) continue
            val stuff = l.split(" ")
            var enchant: CustomEnchantment
            try {
                enchant =
                    CustomEnchants.getCustomEnchantByName(stuff.subList(0, stuff.size - 1).joinToString(" ").trim())
            } catch (e: NoSuchElementException) {
                continue
            }
            val level = romanToInteger(stuff.last().trim())

            if (res.getOrDefault(enchant, 0) < level) {
                res[enchant] = level
            }
        }

        return res
    }
}