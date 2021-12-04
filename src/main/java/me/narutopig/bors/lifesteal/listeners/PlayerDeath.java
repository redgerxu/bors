package me.narutopig.bors.lifesteal.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    public static void setMaxHealth(Player player, double hearts) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        Player killer = killed.getKiller();

        if (killer == null) return;

        setMaxHealth(killed, killed.getMaxHealth() - 2);
        setMaxHealth(killed, killer.getMaxHealth() + 2);
    }
}
