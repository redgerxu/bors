package io.github.narutopig.bors.listeners.enchantments

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.enchanting.Enchanting
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent

class ExperienceEnchant : Listener {
    @EventHandler
    fun onExpChange(event: PlayerExpChangeEvent) {
        if (!Main.configuration.getBoolean("enchantments.experience")) return

        val amount = event.amount
        val player = event.player
        val hand = player.inventory.itemInMainHand
        val handData = Enchanting.getItemCustomEnchants(hand)
        val experienceLevel = handData[CustomEnchants.EXPERIENCE] ?: 0

        if (amount > 0) {
            player.giveExp(amount * experienceLevel)
        }
    }
}