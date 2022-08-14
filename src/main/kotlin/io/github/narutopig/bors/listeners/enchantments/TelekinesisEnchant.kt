package io.github.narutopig.bors.listeners.enchantments

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.enchanting.Enchanting
import org.bukkit.GameMode
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.entity.Wolf
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent

class TelekinesisEnchant : Listener {
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (!Main.configuration.getBoolean("enchantments.telekinesis")) return

        // entity drops telekinesis
        val killer = event.entity.killer
        if (event.entity is Player) {
            return
        }

        val player = event.entity.killer

        if (killer == null) return
        val hand = killer.inventory.itemInMainHand
        val eData = Enchanting.getItemCustomEnchants(hand)
        if ((eData[CustomEnchants.TELEKINESIS] ?: 0) <= 0) return
        val drops = event.drops
        drops.forEach {
            player?.inventory?.addItem(it)
        }

        event.drops.clear() // does this even work lol
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        if (!Main.configuration.getBoolean("enchantments.telekinesis")) return

        // block drops telekinesis
        val player = event.player
        val block = event.block
        val hand = player.inventory.itemInMainHand
        if (player.inventory.firstEmpty() == -1) return
        val eData = Enchanting.getItemCustomEnchants(hand)
        if ((eData[CustomEnchants.TELEKINESIS] ?: 0) <= 0) return
        if (player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) return
        if (block.state is Container) return
        event.isDropItems = false
        val drops = block.getDrops(player.inventory.itemInMainHand, player)
        drops.forEach { player.inventory.addItem(it) }
        event.block.drops.clear()
    }
}