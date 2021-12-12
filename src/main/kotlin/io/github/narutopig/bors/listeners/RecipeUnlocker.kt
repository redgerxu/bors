package io.github.narutopig.bors.listeners

import io.github.narutopig.bors.CustomRecipes
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RecipeUnlocker : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        for (recipe in CustomRecipes.recipes) {
            player.discoverRecipe(recipe.result.type.key)
        }
    }
}