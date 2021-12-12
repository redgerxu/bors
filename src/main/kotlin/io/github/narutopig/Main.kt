package io.github.narutopig

import io.github.narutopig.commands.EnchantCommand
import io.github.narutopig.enchantments.AftermathEnchant
import io.github.narutopig.enchantments.ExperienceEnchant
import io.github.narutopig.enchantments.TelekinesisEnchant
import io.github.narutopig.listeners.AntiCombatLog
import io.github.narutopig.listeners.RecipeUnlocker
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {
    override fun onEnable() {
        CustomEnchants.register()
        server.pluginManager.registerEvents(this, this)
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

    companion object {
        fun addRecipes() {
            for (recipe in CustomRecipes.recipes) {
                Bukkit.addRecipe(recipe)
            }
        }
    }
}