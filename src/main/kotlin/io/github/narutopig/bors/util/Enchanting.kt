package io.github.narutopig.bors.util

import com.google.gson.GsonBuilder
import io.github.narutopig.bors.EnchantmentWrapper
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.IntTag
import org.bukkit.*
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

class EnchantmentData(name: String, displayName: String) {
    val name: String
    val displayName: String

    init {
        this.name = name
        this.displayName = displayName
    }
}

object Enchanting {
    var enchantmentNames = mutableMapOf<String, String>()

    init {
        GsonBuilder().create().fromJson(Text.text(), Array<EnchantmentData>::class.java)
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
        val craftItemStack = CraftItemStack.asNMSCopy(item)
        val tagCompound =
            if (craftItemStack.hasTag() && craftItemStack.tag != null) craftItemStack.tag else CompoundTag()
        tagCompound?.put(enchantment.key.key, IntTag.valueOf(level))
        craftItemStack.tag = tagCompound
        item = CraftItemStack.asBukkitCopy(craftItemStack)

        return item
    }
}