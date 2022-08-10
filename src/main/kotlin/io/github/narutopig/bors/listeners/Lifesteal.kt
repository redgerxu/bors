package io.github.narutopig.bors.listeners

import io.github.narutopig.bors.Main
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent

class Lifesteal : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!Main.configuration.getBoolean("plugins.lifesteal")) return

        if (event.action == Action.RIGHT_CLICK_AIR) {
            val hand = event.player.inventory.itemInMainHand
            if (hand.type == Material.NETHER_STAR) {
                if (hand.amount > 0 && event.player.maxHealth <= 38) {
                    event.player.inventory.itemInMainHand.amount -= 1
                    event.player.maxHealth += 2
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        if (!Main.configuration.getBoolean("plugins.lifesteal")) return

        val deadPlayer = event.entity
        val killer = deadPlayer.killer
        if (killer == null) {
            val hp = deadPlayer.maxHealth
            if (hp >= 4) {
                event.entity.maxHealth = hp - 2
            }
        } else {
            if (deadPlayer.maxHealth >= 4 && killer.maxHealth <= 38) { // hearts change only if killed player has at least two hearts and killer has nineteen hearts
                val hp = deadPlayer.maxHealth
                event.entity.maxHealth = hp - 2
                if (event.entity.killer != null) {
                    val hp2 = killer.maxHealth
                    event.entity.killer!!.maxHealth = hp2 + 2
                }
            }
        }
    }
}