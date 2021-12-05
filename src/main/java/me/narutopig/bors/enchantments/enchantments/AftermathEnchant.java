package me.narutopig.bors.enchantments.enchantments;

import me.narutopig.bors.CustomEnchants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.narutopig.bors.Util.hasEnchant;
import static me.narutopig.bors.Util.power;

public class AftermathEnchant implements Listener {
    public static int calculateDamage(Inventory inventory) {
        int res = 0;
        ItemStack[] contents = inventory.getContents();

        for (ItemStack itemStack : contents) {
            if (itemStack == null) continue;
            if (hasEnchant(itemStack, CustomEnchants.AFTERMATH)) {
                res += power(2, itemStack.getEnchantments().getOrDefault(CustomEnchants.AFTERMATH, 1) - 1);
            }
        }

        res = Math.min(res, 10);

        return res;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            int amount = calculateDamage(player.getInventory());
            killer.damage(amount);
        }
    }
}
