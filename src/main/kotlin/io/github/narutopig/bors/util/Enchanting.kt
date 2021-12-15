package io.github.narutopig.bors.util

import com.google.gson.GsonBuilder
import io.github.narutopig.bors.EnchantmentWrapper
import io.github.narutopig.bors.util.General.romanToInteger
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class EnchantmentName(name: String, displayName: String) {
    val name: String
    val displayName: String

    init {
        this.name = name
        this.displayName = displayName
    }
}

class EnchantmentData(hasEnchant: Boolean, level: Int) {
    val hasEnchant: Boolean
    val level: Int

    init {
        this.hasEnchant = hasEnchant
        this.level = level
    }
}

object Enchanting {
    var enchantmentNames = mutableMapOf<String, String>()

    init {
        GsonBuilder().create().fromJson(Text.text(), Array<EnchantmentName>::class.java)
            .toList()
            .forEach {
                enchantmentNames[it.name] = it.displayName
            }
    }

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
                    val newLine = EnchantmentWrapper.color.toString() + enchantment.name + " " + General.toRoman(level)
                    lore[i] = newLine
                    break
                }
                i++
            }
        } else {
            val newLine = EnchantmentWrapper.color.toString() + enchantment.name + " " + General.toRoman(level)
            lore.add(newLine)
        }
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        item.removeEnchantment(enchantment)
        item.addEnchantment(enchantment, level)
    }

    fun addEnchant(itemStack: ItemStack, enchantment: EnchantmentWrapper, level: Int): ItemStack? {
        var item = itemStack.clone()
        if (!canEnchant(item, enchantment)) return null
        val itemMeta = item.itemMeta ?: return null
        var lore = itemMeta.lore
        if (lore == null) lore = ArrayList()
        if (hasEnchant(item, enchantment)) {
            var i = 0
            for (line in lore) {
                if (ChatColor.stripColor(line)!!.split(" ").toTypedArray()[0] == enchantment.name) {
                    val newLine = EnchantmentWrapper.color.toString() + enchantment.name + " " + General.toRoman(level)
                    lore[i] = newLine
                    break
                }
                i++
            }
        } else {
            val newLine = EnchantmentWrapper.color.toString() + enchantment.name + " " + General.toRoman(level)
            lore.add(newLine)
        }
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        item.removeEnchantment(enchantment)
        item.addEnchantment(enchantment, level)

        return item
    }

    fun getEnchantmentData(item: ItemStack, enchantment: String): EnchantmentData {
        val itemMeta = item.itemMeta

        if (itemMeta == null) {
            return EnchantmentData(false, 0)
        } else {
            val lore = itemMeta.lore

            return if (lore == null) {
                EnchantmentData(false, 0)
            } else {
                var hasEnchant = false
                var level = 0

                lore.forEach {
                    val stuff = ChatColor.stripColor(it)!!.split(" ")
                    if (stuff[0] == enchantment) hasEnchant = true
                    level = romanToInteger(stuff[1])
                }

                EnchantmentData(hasEnchant, level)
            }
        }
    }
}