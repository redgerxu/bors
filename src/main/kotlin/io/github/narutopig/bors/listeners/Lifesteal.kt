package io.github.narutopig.bors.listeners

import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.meta.SkullMeta
import java.util.*


class Lifesteal : Listener {
    companion object {
        fun setMaxHealth(player: Player, hearts: Double) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = hearts
        }
    }

    @EventHandler
    fun onItemUse(event: PlayerInteractEvent) {
        val player = event.player

        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            if (event.clickedBlock == null) return
            val drops = event.clickedBlock!!.drops
            if (drops.size != 1) return
            val head = drops.toList()[0]
            if (head != null) {
                val meta = head.itemMeta as SkullMeta

                if (meta.owner == "A_HOMO_SAPIEN") {
                    setMaxHealth(player, player.maxHealth + 2)
                    event.clickedBlock!!.setType(Material.AIR, false)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val killed = event.entity
        val killer = killed.killer ?: return
        setMaxHealth(killed, killed.maxHealth - 2)
        setMaxHealth(killed, killer.maxHealth + 2)
    }
}