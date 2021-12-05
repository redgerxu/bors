package me.narutopig.bors.enchantments.enchantments;

import me.narutopig.bors.CustomEnchants;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ExperienceEnchant implements Listener {
    @EventHandler
    public void onExpChange(PlayerExpChangeEvent event) {
        int amount = event.getAmount();
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();
        if (amount > 0) {
            Map<Enchantment, Integer> enchants = hand.getEnchantments();
            player.giveExp(amount * enchants.getOrDefault(CustomEnchants.EXPERIENCE, 0));
        }
    }
}