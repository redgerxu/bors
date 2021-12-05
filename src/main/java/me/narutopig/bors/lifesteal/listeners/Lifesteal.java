package me.narutopig.bors.lifesteal.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Lifesteal implements Listener {
    private final Map<UUID, Long> killManager = new HashMap<>(); // keeps tracked of how long ago you were killed

    public static void setMaxHealth(Player player, double hearts) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = killed.getKiller();

        if (killer == null) return;

        UUID uuid = killed.getUniqueId();

        long currTime = System.currentTimeMillis();
        if (currTime - killManager.getOrDefault(uuid, 0L) >= 15L * 1000) {
            // runs only if the last time the victim was killed was more than 15 seconds ago
            setMaxHealth(killed, killed.getMaxHealth() - 2);
            setMaxHealth(killed, killer.getMaxHealth() + 2);
        }
        
        killManager.put(uuid, currTime);
    }
}
