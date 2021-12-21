package io.github.narutopig.bors.commands.completers

import io.github.narutopig.bors.enchantments.CustomEnchants
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class EnchantCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (sender is Player) {
            return CustomEnchants.customEnchants.map { enchant -> enchant.key.key }.toMutableList()
        }

        return null
    }
}