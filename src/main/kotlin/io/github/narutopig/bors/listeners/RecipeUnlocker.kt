package io.github.narutopig.bors.listeners

import io.github.narutopig.bors.CustomRecipes
import io.github.narutopig.bors.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RecipeUnlocker : Listener {
    private val enabledRecipes = mutableMapOf<String, Boolean>()

    init {
        val len = "recipes.".length
        val paths = Main.configuration.getKeys(true).filter { key -> key.startsWith("recipes.") }

        for (path in paths) {
            val key = path.slice(len until path.length)
            enabledRecipes[key] = Main.configuration.getBoolean(path)
        }
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        for (recipe in CustomRecipes.recipes) {
            try {
                if (enabledRecipes[recipe.key.key] == true) player.discoverRecipe(recipe.result.type.key)
            } catch (ignored: Exception) {
            }
        }
    }
}