package me.narutopig.bors;

import me.narutopig.bors.enchantments.EnchantCommand;
import me.narutopig.bors.enchantments.enchants.ExperienceEnchant;
import me.narutopig.bors.enchantments.enchants.TelekinesisEnchant;
import me.narutopig.bors.listeners.AntiCombatLog;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static void addRecipes() {
        for (Recipe recipe : CustomRecipes.recipes) {
            Bukkit.addRecipe(recipe);
        }
    }

    @Override
    public void onEnable() {
        CustomEnchants.register();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ExperienceEnchant(), this);
        getServer().getPluginManager().registerEvents(new TelekinesisEnchant(), this);
        getServer().getPluginManager().registerEvents(new AntiCombatLog(), this);
        getCommand("borsenchant").setExecutor(new EnchantCommand());
        addRecipes();
    }

    @Override
    public void onDisable() {
    }
}
