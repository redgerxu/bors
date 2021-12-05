package me.narutopig.bors;

import me.narutopig.bors.enchantments.commands.EnchantCommand;
import me.narutopig.bors.enchantments.enchants.ExperienceEnchant;
import me.narutopig.bors.enchantments.enchants.TelekinesisEnchant;
import me.narutopig.bors.lifesteal.commands.SetHealth;
import me.narutopig.bors.lifesteal.listeners.Lifesteal;
import me.narutopig.bors.listeners.AntiCombatLog;
import me.narutopig.bors.recipes.CustomRecipes;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        CustomEnchants.register();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ExperienceEnchant(), this);
        getServer().getPluginManager().registerEvents(new TelekinesisEnchant(), this);
        getServer().getPluginManager().registerEvents(new Lifesteal(), this);
        getServer().getPluginManager().registerEvents(new AntiCombatLog(), this);
        getCommand("borsenchant").setExecutor(new EnchantCommand());
        getCommand("sethealth").setExecutor(new SetHealth());
        Bukkit.addRecipe(CustomRecipes.tridentRecipe());
    }

    @Override
    public void onDisable() {
    }
}
