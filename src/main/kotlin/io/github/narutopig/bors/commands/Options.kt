package io.github.narutopig.bors.commands

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.util.Messages
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class Options : CommandExecutor, TabExecutor {
    var configPaths: List<String>

    init {
        val paths = Main.configuration.getKeys(true)
        for (path in paths) {
            if (Main.configuration.isConfigurationSection(path)) {
                paths.remove(path)
            }
        }
        @Suppress("UNCHECKED_CAST")
        configPaths = paths as List<String>
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (sender is Player) {
            return configPaths
        }

        return null
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        var success = true
        if (sender is Player) {
            if (sender.isOp) {
                Main.configuration.set(args[0], args[1])
            } else {
                success = false
            }
        } else {
            Main.configuration.set(args[0], args[1])
        }
        if (success) {
            sender.sendMessage("Set option ${args[0]} to ${args[1]}")
            Main.instance.saveConfig()
        } else {
            sender.sendMessage(Messages.missingPermissions)
        }
        return success
    }
}