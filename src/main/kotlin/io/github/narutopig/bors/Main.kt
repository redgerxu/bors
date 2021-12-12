package io.github.narutopig.bors

import io.github.narutopig.bors.commands.EnchantCommand
import io.github.narutopig.bors.enchantments.AftermathEnchant
import io.github.narutopig.bors.enchantments.ExperienceEnchant
import io.github.narutopig.bors.enchantments.TelekinesisEnchant
import io.github.narutopig.bors.listeners.AntiCombatLog
import io.github.narutopig.bors.listeners.RecipeUnlocker
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        CustomEnchants.register()
        registerEvent(AftermathEnchant())
        registerEvent(ExperienceEnchant())
        registerEvent(TelekinesisEnchant())
        registerEvent(AntiCombatLog())
        registerEvent(RecipeUnlocker())
        getCommand("borsenchant")!!.setExecutor(EnchantCommand())
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