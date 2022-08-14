package io.github.narutopig.bors

import io.github.narutopig.bors.commands.BoRSEnchant
import io.github.narutopig.bors.commands.Grindstone
import io.github.narutopig.bors.commands.Options
import io.github.narutopig.bors.commands.UpdateLore
import io.github.narutopig.bors.listeners.AntiCombatLog
import io.github.narutopig.bors.listeners.ConsoleCommand
import io.github.narutopig.bors.listeners.Lifesteal
import io.github.narutopig.bors.listeners.RecipeUnlocker
import io.github.narutopig.bors.listeners.enchantments.ExperienceEnchant
import io.github.narutopig.bors.listeners.enchantments.PoisonEnchant
import io.github.narutopig.bors.listeners.enchantments.TelekinesisEnchant
import io.github.narutopig.bors.listeners.enchantments.AutoMinerEnchant
import org.bukkit.Bukkit
import org.bukkit.Bukkit.broadcastMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var instance: Main
        lateinit var configuration: FileConfiguration

        fun broadcast(message: String) {
            broadcastMessage(message)
        }
    }

    override fun onEnable() {
        saveDefaultConfig()
        instance = this
        configuration = config
        // enchants
        registerEvent(ExperienceEnchant())
        registerEvent(PoisonEnchant())
        registerEvent(TelekinesisEnchant())
        registerEvent(AutoMinerEnchant())

        // fun modules and stuff
        registerEvent(AntiCombatLog())
        registerEvent(ConsoleCommand())
        registerEvent(Lifesteal())
        registerEvent(RecipeUnlocker())

        // commands
        getCommand("borsenchant")!!.setExecutor(BoRSEnchant())
        getCommand("borsenchant")!!.tabCompleter = BoRSEnchant()
        getCommand("grindstone")!!.setExecutor(Grindstone())
        getCommand("updatelore")!!.setExecutor(UpdateLore())
        getCommand("options")!!.setExecutor(Options())
        addRecipes()
    }

    override fun onDisable() {}

    private fun registerEvent(listener: Listener) {
        server.pluginManager.registerEvents(listener, this)
    }

    private fun addRecipes() {
        for (recipe in CustomRecipes.recipes) {
            Bukkit.addRecipe(recipe)
        }
    }
}