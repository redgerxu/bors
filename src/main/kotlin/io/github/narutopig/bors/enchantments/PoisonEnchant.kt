package io.github.narutopig.bors.enchantments

import io.github.narutopig.bors.util.Enchanting.getEnchantmentData
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
        if (event.damager is Player) {
            val damager = event.damager as Player
            val eData = getEnchantmentData(damager.inventory.itemInMainHand, CustomEnchants.POISON.name)
            if (eData.hasEnchant) {
                if (event.entity is Player) {
                    val player = event.entity as Player
                    val poison = PotionEffect(PotionEffectType.POISON, 20 * 5, eData.level)
                    player.addPotionEffect(poison)
                }
            }
        }
    }
}