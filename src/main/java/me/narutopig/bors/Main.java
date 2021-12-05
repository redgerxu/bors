package me.narutopig.bors;

import me.narutopig.bors.enchantments.EnchantCommand;
import me.narutopig.bors.enchantments.enchantments.AftermathEnchant;
import me.narutopig.bors.enchantments.enchantments.ExperienceEnchant;
import me.narutopig.bors.enchantments.enchantments.TelekinesisEnchant;
import me.narutopig.bors.listeners.AntiCombatLog;
import me.narutopig.bors.listeners.RecipeUnlocker;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        CustomEnchants.register();
        getServer().getPluginManager().registerEvents(this, this);
        registerEvent(new AftermathEnchant());
        registerEvent(new ExperienceEnchant());
        registerEvent(new TelekinesisEnchant());
        registerEvent(new TelekinesisEnchant());
        registerEvent(new AntiCombatLog());
        registerEvent(new RecipeUnlocker());
        getCommand("borsenchant").setExecutor(new EnchantCommand());
        addRecipes();
    }

    @Override
    public void onDisable() {
    }

    public static void addRecipes() {
        for (Recipe recipe : CustomRecipes.recipes) {
            Bukkit.addRecipe(recipe);
        }
    }

    public void registerEvent(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }
}
