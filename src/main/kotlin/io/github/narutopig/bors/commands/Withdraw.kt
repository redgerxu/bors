package io.github.narutopig.bors.commands

import io.github.narutopig.bors.CustomRecipes
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Withdraw : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (args.isEmpty()) {
                sender.sendMessage("${ChatColor.RED}Insufficient arguments")
                return true
            }

            try {
                val amount = args[0].toDouble() * 2
                if (amount % 1 != 0.0 || amount < 2) { // is the amount not an integer or less than 2
                    throw NumberFormatException()
                }
                if (sender.maxHealth <= amount) throw NumberFormatException()
                sender.maxHealth -= amount
                val heart = CustomRecipes.heart.result
                heart.amount = (amount / 2).toInt()
                sender.inventory.addItem(heart)
                sender.sendMessage("${ChatColor.GREEN}Gave you ${heart.amount} hearts")
            } catch (e: NumberFormatException) {
                sender.sendMessage("${ChatColor.RED}Invalid amount")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}You need to be a player to use this command")
        }

        return true
    }
}