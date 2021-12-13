package io.github.narutopig.bors.enchantments

import io.github.narutopig.bors.CustomEnchants
import io.github.narutopig.bors.util.Enchanting
import io.github.narutopig.bors.util.General
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.Inventory

class AftermathEnchant : Listener {
    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity
        if (player.killer != null) {
            val killer = player.killer
            val amount = calculateDamage(player.inventory)
            killer!!.damage(amount.toDouble())
        }
    }

    companion object {
        fun calculateDamage(inventory: Inventory): Int {
            var res = 0
            val contents = inventory.contents
            for (itemStack in contents) {
                if (itemStack == null) continue
                if (Enchanting.hasEnchant(itemStack, CustomEnchants.AFTERMATH)) {
                    res += General.power(2, itemStack.enchantments.getOrDefault(CustomEnchants.AFTERMATH, 1) - 1)
                }
            }
            res = res.coerceAtMost(10)
            return res
        }
    }
}