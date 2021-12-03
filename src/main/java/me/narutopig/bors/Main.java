package me.narutopig.bors;

import me.narutopig.bors.commands.EnchantCommand;
import me.narutopig.bors.enchants.ExperienceEnchant;
import me.narutopig.bors.enchants.TelekinesisEnchant;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        CustomEnchants.register();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ExperienceEnchant(), this);
        getServer().getPluginManager().registerEvents(new TelekinesisEnchant(), this);
        getCommand("borsenchant").setExecutor(new EnchantCommand());
    }

    @Override
    public void onDisable() {
    }
}
