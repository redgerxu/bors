package io.github.narutopig.bors

import io.github.narutopig.bors.commands.BoRSEnchant
import io.github.narutopig.bors.commands.Grindstone
import io.github.narutopig.bors.commands.UpdateLore
import io.github.narutopig.bors.commands.completers.EnchantCompleter
import io.github.narutopig.bors.enchanting.*
import io.github.narutopig.bors.enchanting.enchantments.AftermathEnchant
import io.github.narutopig.bors.enchanting.enchantments.ExperienceEnchant
import io.github.narutopig.bors.enchanting.enchantments.PoisonEnchant
import io.github.narutopig.bors.enchanting.enchantments.TelekinesisEnchant
import io.github.narutopig.bors.listeners.AntiCombatLog
import io.github.narutopig.bors.listeners.MinerArmor
import io.github.narutopig.bors.listeners.RecipeUnlocker
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var instance: Main
        lateinit var configuration: FileConfiguration
    }

    override fun onEnable() {
        instance = this
        configuration = config
        CustomEnchants.register()
        registerEvent(AftermathEnchant())
        registerEvent(ExperienceEnchant())
        registerEvent(PoisonEnchant())
        registerEvent(TelekinesisEnchant())
        registerEvent(AntiCombatLog())
        registerEvent(MinerArmor())
        registerEvent(RecipeUnlocker())
        getCommand("borsenchant")!!.setExecutor(BoRSEnchant())
        getCommand("borsenchant")!!.tabCompleter = EnchantCompleter()
        getCommand("grindstone")!!.setExecutor(Grindstone())
        getCommand("updatelore")!!.setExecutor(UpdateLore())
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