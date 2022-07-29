package io.github.narutopig.bors.listeners

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.util.General
import org.bukkit.BanList
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*

class AntiCombatLog : Listener {
    private val combatManager: MutableMap<UUID, Long> = HashMap()
    private val violations: MutableMap<UUID, Int> = HashMap() // find a way to make persistent?

    @EventHandler
    fun onCombat(event: EntityDamageByEntityEvent) {
        if (!Main.configuration.getBoolean("plugins.anticombatlog")) return

        if (event.entity is Player && event.damager is Player) {
            val damaged = event.entity as Player
            val damager = event.damager as Player
            val currTime = System.currentTimeMillis()
            combatManager[damaged.uniqueId] = currTime
            combatManager[damager.uniqueId] = currTime
        }
    }

    @EventHandler
    fun onPlayerLeave(event: PlayerQuitEvent) {
        if (!Main.configuration.getBoolean("plugins.anticombatlog")) return

        val player = event.player
        val uuid = player.uniqueId
        val lastHit = combatManager.getOrDefault(uuid, 0L)
        val currTime = System.currentTimeMillis()
        if (currTime - lastHit >= 30L * 1000 * 60) { // 30 minutes
            violations[uuid] = 0
        }
        if (currTime - lastHit < 15L * 1000) { // time passed is less than some value (might be changed)
            val newViolations = violations.getOrDefault(uuid, 0) + 1
            violations[uuid] = newViolations
            if (newViolations >= 3) {
                val date = Date(currTime + 24 * 3600 * 1000) // in one day
                General.banPlayer(player, "Combat Logging", date, BanList.Type.NAME)
            }
        }
    }
}