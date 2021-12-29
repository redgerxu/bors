package io.github.narutopig.bors.commands

import io.github.narutopig.bors.listeners.Lifesteal
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

class SetHealth : CommandExecutor, TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String> {
        val result = mutableListOf<String>()

        if (sender !is Player) {
            return result
        }

        if (args.isEmpty()) {
            sender.world.players.forEach { p -> result.add(p.name) }
        }

        return result
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("${ChatColor.RED}You need to be an operator to use this command")
            return false
        }

        if (args.size < 2) {
            sender.sendMessage("${ChatColor.RED}Insufficient arguments")
            return false
        }

        val target = Bukkit.getPlayer(args[0])

        if (target == null) {
            sender.sendMessage("${ChatColor.RED}Player was not found")
        }

        try {
            val amount = args[1].toDouble()
            Lifesteal.setMaxHealth(target!!, amount)
            sender.sendMessage("${ChatColor.GREEN}Set ${target.name}'s health to $amount")
        } catch (e: NumberFormatException) {
            sender.sendMessage("${ChatColor.RED}Invalid number")
            return false
        }

        return true
    }
}