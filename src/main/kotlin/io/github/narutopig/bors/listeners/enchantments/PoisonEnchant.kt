package io.github.narutopig.bors.listeners.enchantments

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.enchanting.Enchanting
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

// rename later
class PoisonEnchant : Listener {
    @EventHandler
    fun onPlayerHit(event: EntityDamageByEntityEvent) {
        if (!Main.configuration.getBoolean("enchantments.poison")) return

        if (event.damager is Player) {
            val damager = event.damager as Player
            val eData = Enchanting.getItemCustomEnchants(damager.inventory.itemInMainHand)
            val poisonLevel = eData[CustomEnchants.POISON] ?: 0
            if (poisonLevel > 0) {
                if (event.entity is Player) {
                    val player = event.entity as Player
                    val poison = PotionEffect(PotionEffectType.POISON, 20 * 5, poisonLevel)
                    player.addPotionEffect(poison)
                }
            }
        }
    }
}