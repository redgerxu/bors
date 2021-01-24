package io.github.narutopig.bors.listeners

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import java.util.*


class Lifesteal : Listener {
    private val killManager: MutableMap<UUID, Long> = HashMap() // keeps tracked of how long ago you were killed


    private fun setMaxHealth(player: Player, hearts: Double) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = hearts
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val killed = event.entity
        val killer = killed.killer ?: return
        val uuid = killed.uniqueId
        val currTime = System.currentTimeMillis()
        if (currTime - killManager.getOrDefault(uuid, 0L) >= 15L * 1000) {
            // runs only if the last time the victim was killed was more than 15 seconds ago
            setMaxHealth(killed, killed.maxHealth - 2)
            setMaxHealth(killed, killer.maxHealth + 2)
        }
        killManager[uuid] = currTime
    }
}