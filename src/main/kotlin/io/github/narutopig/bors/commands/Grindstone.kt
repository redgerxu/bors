package io.github.narutopig.bors.commands

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.util.Messages
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Grindstone : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (!Main.configuration.getBoolean("commands.grindstone")) {
            sender.sendMessage(Messages.disabledCommand)
            return false
        }

        if (sender is Player) {
            val handItemMeta = sender.inventory.itemInMainHand.itemMeta

            if (handItemMeta == null) {
                sender.sendMessage("${ChatColor.RED}This item has no item meta.")
            } else {
                sender.inventory.itemInMainHand.itemMeta!!.lore = mutableListOf()
                sender.sendMessage("${ChatColor.GREEN}Removed all custom enchantments.")
            }
        } else {
            sender.sendMessage("${ChatColor.RED}You need to be a player to use this command.")
        }

        return true
    }
}