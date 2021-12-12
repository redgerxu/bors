package io.github.narutopig.bors.enchantments

import io.github.narutopig.bors.CustomEnchants
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent
import kotlin.math.max

class ExperienceEnchant : Listener {
    @EventHandler
    fun onExpChange(event: PlayerExpChangeEvent) {
        val amount = event.amount
        val player = event.player
        val hand = player.inventory.itemInMainHand
        val offHand = player.inventory.itemInOffHand
        val enchants = hand.enchantments
        val experienceLevel = max(
            enchants.getOrDefault(CustomEnchants.EXPERIENCE, 0), offHand.enchantments.getOrDefault(
                CustomEnchants.EXPERIENCE, 0
            )
        )
        if (amount > 0) {
            player.giveExp(amount * experienceLevel)
        }
    }
}