package me.narutopig.bors.listeners;

import org.bukkit.BanList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.narutopig.bors.Util.banPlayer;

public class AntiCombatLog implements Listener {
    private final Map<UUID, Long> combatManager = new HashMap<>();
    private final Map<UUID, Integer> violations = new HashMap<>(); // find a way to make persistent?

    @EventHandler
    public void onCombat(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player damaged && event.getDamager() instanceof Player damager) {
            long currTime = System.currentTimeMillis();
            combatManager.put(damaged.getUniqueId(), currTime);
            combatManager.put(damager.getUniqueId(), currTime);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        long lastHit = combatManager.getOrDefault(uuid, 0L);
        long currTime = System.currentTimeMillis();
        if (currTime - lastHit < 15L * 1000) { // time passed is less than some value (might be changed)
            int newViolations = violations.getOrDefault(uuid, 0) + 1;
            violations.put(uuid, newViolations);
            if (newViolations >= 3) {
                Date date = new Date(currTime + 24 * 3600 * 1000); // in one day
                banPlayer(player, "Combat Logging", date, BanList.Type.NAME);
            }
        }
    }
}
