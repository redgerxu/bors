package me.narutopig.bors.listeners;

import me.narutopig.bors.CustomRecipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Recipe;

public class RecipeUnlocker implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        System.out.println("potato");

        for (Recipe recipe : CustomRecipes.recipes) {
            player.discoverRecipe(recipe.getResult().getType().getKey());
        }
    }
}
