package io.github.narutopig.bors.enchanting.enchantments

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.util.Enchanting.getEnchantmentData
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerExpChangeEvent
import kotlin.math.max

class ExperienceEnchant : Listener {
    @EventHandler
    fun onExpChange(event: PlayerExpChangeEvent) {
        if (!Main.configuration.getBoolean("enchantments.experience")) return

        val amount = event.amount
        val player = event.player
        val hand = player.inventory.itemInMainHand
        val offHand = player.inventory.itemInOffHand
        val handData = getEnchantmentData(hand, CustomEnchants.EXPERIENCE.name)
        val offHandData = getEnchantmentData(offHand, CustomEnchants.EXPERIENCE.name)
        val experienceLevel = max(
            handData.level, offHandData.level
        )
        if (amount > 0) {
            player.giveExp(amount * experienceLevel)
        }
    }
}