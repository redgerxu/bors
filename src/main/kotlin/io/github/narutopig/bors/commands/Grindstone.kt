package io.github.narutopig.bors.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Grindstone : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        return if (sender is Player) {
            val handItemMeta = sender.inventory.itemInMainHand.itemMeta

            if (handItemMeta == null) {
                sender.sendMessage(ChatColor.RED.toString() + "This item has no item meta.")
            } else {
                sender.inventory.itemInMainHand.itemMeta!!.lore = mutableListOf()
                sender.sendMessage(ChatColor.GREEN.toString() + "Removed all custom enchantments.")
            }

            true
        } else {
            sender.sendMessage(ChatColor.RED.toString() + "You need to be a player to use this command.")
            false
        }
    }
}