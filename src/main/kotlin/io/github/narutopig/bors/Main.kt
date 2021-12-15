package io.github.narutopig.bors

import io.github.narutopig.bors.commands.EnchantCommand
import io.github.narutopig.bors.commands.UpdateLoreCommand
import io.github.narutopig.bors.enchantments.AftermathEnchant
import io.github.narutopig.bors.enchantments.ExperienceEnchant
import io.github.narutopig.bors.enchantments.TelekinesisEnchant
import io.github.narutopig.bors.listeners.AntiCombatLog
import io.github.narutopig.bors.listeners.RecipeUnlocker
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        lateinit var instance: Main
    }

    override fun onEnable() {
        instance = this
        CustomEnchants.register()
        registerEvent(AftermathEnchant())
        registerEvent(ExperienceEnchant())
        registerEvent(TelekinesisEnchant())
        registerEvent(AntiCombatLog())
        registerEvent(RecipeUnlocker())
        getCommand("borsenchant")!!.setExecutor(EnchantCommand())
        getCommand("updatelore")!!.setExecutor(UpdateLoreCommand())
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