package io.github.narutopig.bors.armor

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.inventory.PlayerInventory
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class MinerArmor : Listener {
    private fun hasMinerArmor(inventory: PlayerInventory): Boolean {
        val armor = mutableListOf(
            inventory.helmet,
            inventory.chestplate,
            inventory.leggings,
            inventory.boots
        )

        for (slot in armor) {
            if (slot == null) return false
        }

        return true
    }

    @EventHandler
    fun onArmorBreak(event: PlayerItemBreakEvent) {
        val player = event.player
        if ((ChatColor.stripColor(event.brokenItem.itemMeta?.displayName) ?: "").startsWith("Miner's")) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING)
        }
    }

    @EventHandler
    fun onInventoryChange(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val curr = hasMinerArmor(player.inventory)

        if (curr) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING)
            player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 99999, 3))
        } else {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING)
        }
    }
}