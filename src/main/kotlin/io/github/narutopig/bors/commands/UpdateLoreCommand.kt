package io.github.narutopig.bors.commands

import io.github.narutopig.bors.CustomEnchants.isCustomEnchantment
import io.github.narutopig.bors.EnchantmentWrapper
import io.github.narutopig.bors.util.Enchanting
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
        if (isCustomEnchantment(e)) {
            s.append(EnchantmentWrapper.color)
            s.append(e.name)
        } else {
            s.append(ChatColor.GRAY)
            val enchantmentName = Enchanting.enchantmentNames[e.key.key]
            if (enchantmentName != null) {
                s.append(enchantmentName)
            } else {
                s.append("Unknown enchantment")
            }
        }

        s.append(" ")
        s.append(toRoman(level))

        return s.toString()
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val hand = sender.inventory.itemInMainHand
            val enchantments = hand.enchantments

            val lore = mutableListOf<String>()
            enchantments.entries.forEach { (e, l) -> if (isCustomEnchantment(e)) lore.add(toLore(e, l)) }

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