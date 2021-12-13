package io.github.narutopig.bors.enchantments

import io.github.narutopig.bors.CustomEnchants
import org.bukkit.GameMode
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent

class TelekinesisEnchant : Listener {
    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        // entity drops telekinesis
        val killer = event.entity.killer
        if (event.entity is Player) {
            return
        }

        val player = event.entity.killer

        if (killer == null) return
        val enchantments = killer.inventory.itemInMainHand.enchantments
        if (enchantments.getOrDefault(CustomEnchants.TELEKINESIS, 0) == 0) return
        val drops = event.drops
        drops.forEach {
            player?.inventory?.addItem(it)
        }

        event.drops.clear() // does this even work lol
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        // block drops telekinesis
        val player = event.player
        val block = event.block
        val enchants = player.inventory.itemInMainHand.enchantments
        val level = enchants.getOrDefault(CustomEnchants.TELEKINESIS, -1)
        if (player.inventory.firstEmpty() == -1) return
        if (level == -1) return
        if (player.gameMode == GameMode.CREATIVE || player.gameMode == GameMode.SPECTATOR) return
        if (block.state is Container) return
        event.isDropItems = false
        val drops = block.getDrops(player.inventory.itemInMainHand, player)
        drops.forEach { player.inventory.addItem(it) }
        event.block.drops.clear()
    }
}