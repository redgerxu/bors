package me.narutopig.bors.enchantments.enchantments;

import me.narutopig.bors.CustomEnchants;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TelekinesisEnchant implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        // entity drops telekinesis
        Player killer = event.getEntity().getKiller();
        if (event.getEntity() instanceof Player) {
            return;
        }
        if (killer == null) return;
        Map<Enchantment, Integer> enchantments = killer.getInventory().getItemInMainHand().getEnchantments();
        if (enchantments.getOrDefault(CustomEnchants.TELEKINESIS, -1) == -1) return;
        List<ItemStack> drops = event.getDrops();
        for (ItemStack drop : drops) killer.getInventory().addItem(drop);
        event.getDrops().clear(); // does this even work lol
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        // block drops telekinesis
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Map<Enchantment, Integer> enchants = player.getInventory().getItemInMainHand().getEnchantments();
        int level = enchants.getOrDefault(CustomEnchants.TELEKINESIS, -1);

        if (player.getInventory().firstEmpty() == -1) return;
        if (level == -1) return;
        if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) return;
        if (block.getState() instanceof Container) return;

        event.setDropItems(false);

        Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());

        if (drops.isEmpty()) return;

        player.getInventory().addItem(drops.iterator().next());
    }
}