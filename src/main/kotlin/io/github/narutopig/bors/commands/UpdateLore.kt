package io.github.narutopig.bors.commands

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class UpdateLore : CommandExecutor {
    companion object {
        fun colorToChar(color: ChatColor): Char {
            return color.toString().toCharArray()[0]
        }
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val hand = sender.inventory.itemInMainHand
            if (hand.itemMeta == null || hand.itemMeta!!.lore == null) {
                sender.sendMessage("${ChatColor.RED}This item does not have an item meta.")
            } else {
                val lore = hand.itemMeta!!.lore
                if (lore == null) {
                    sender.sendMessage(ChatColor.GREEN.toString() + "Updated lore!")
                } else {
                    for (i in lore.indices) {
                        lore[i] =
                            lore[i].replace(colorToChar(ChatColor.GREEN), colorToChar(ChatColor.BLUE))
                    }
                    sender.inventory.itemInMainHand.itemMeta!!.lore = lore
                }
            }
            sender.sendMessage("${ChatColor.GREEN}Updated lore!")
        } else {
            sender.sendMessage("${ChatColor.RED}You need to be a player to use this command.")
        }

        return true
    }
}
