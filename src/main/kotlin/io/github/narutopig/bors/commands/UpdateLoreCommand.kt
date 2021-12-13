package io.github.narutopig.bors.commands

import io.github.narutopig.bors.CustomEnchants.isCustomEnchantment
import io.github.narutopig.bors.util.General.toRoman
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

class UpdateLoreCommand : CommandExecutor {
    private fun toLore(e: Enchantment, level: Int): String {
        val s: StringBuilder = StringBuilder()
        s.append(ChatColor.RESET)
        if (isCustomEnchantment(e)) s.append(ChatColor.GREEN)
        else s.append(ChatColor.GRAY)

        s.append(e.key.key)
        s.append(" ")
        s.append(toRoman(level))

        return s.toString()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val hand = sender.inventory.itemInMainHand
            val enchantments = hand.enchantments

            val lore = enchantments.entries.map { (e, l) -> toLore(e, l) }

            val itemMeta = hand.itemMeta

            itemMeta!!.lore = lore

            hand.itemMeta = itemMeta

            sender.inventory.setItemInMainHand(hand)

            sender.sendMessage("${ChatColor.GREEN}Updated lore!")

            return true
        }

        return false
    }
}