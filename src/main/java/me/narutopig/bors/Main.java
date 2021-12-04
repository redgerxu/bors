package me.narutopig.bors;

import me.narutopig.bors.enchantments.commands.EnchantCommand;
import me.narutopig.bors.enchantments.enchants.ExperienceEnchant;
import me.narutopig.bors.enchantments.enchants.TelekinesisEnchant;
import me.narutopig.bors.lifesteal.commands.SetHealth;
import me.narutopig.bors.lifesteal.listeners.PlayerDeath;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        CustomEnchants.register();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ExperienceEnchant(), this);
        getServer().getPluginManager().registerEvents(new TelekinesisEnchant(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getCommand("borsenchant").setExecutor(new EnchantCommand());
        getCommand("sethealth").setExecutor(new SetHealth());
    }

    @Override
    public void onDisable() {
    }
}
