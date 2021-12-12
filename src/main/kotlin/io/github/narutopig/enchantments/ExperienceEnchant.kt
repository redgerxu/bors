package io.github.narutopig.enchantments

import io.github.narutopig.CustomEnchants
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent

class ExperienceEnchant : Listener {
    @EventHandler
    fun onExpChange(event: PlayerExpChangeEvent) {
        val amount = event.amount
        val player = event.player
        val hand = player.inventory.itemInMainHand
        if (amount > 0) {
            val enchants = hand.enchantments
            player.giveExp(amount * enchants.getOrDefault(CustomEnchants.EXPERIENCE, 0))
        }
    }
}