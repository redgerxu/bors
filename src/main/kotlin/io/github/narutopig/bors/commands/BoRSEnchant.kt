package io.github.narutopig.bors.commands

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CostData
import io.github.narutopig.bors.enchanting.CustomEnchantment
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.enchanting.Enchanting
import io.github.narutopig.bors.util.General
import io.github.narutopig.bors.util.Messages
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class BoRSEnchant : CommandExecutor, TabExecutor {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (sender is Player) {
            val options = CustomEnchants.customEnchants.map { enchant -> enchant.key }.toMutableList()
            if (args.isNotEmpty()) {
                try {
                    val lastEnchant = CustomEnchants.getCustomEnchant(args.last())
                    val maxLevel = lastEnchant.maxLevel
                    for (i in 1..maxLevel) {
                        options.add(i.toString())
                    }
                } catch (ignored: NoSuchElementException) {
                }
            }
            return options
        }

        return null
    }

    override fun onCommand(
        @Nonnull sender: CommandSender,
        @Nonnull command: Command,
        label: String,
        @Nonnull args: Array<String>
    ): Boolean {
        if (!Main.configuration.getBoolean("commands.borsenchant")) {
            sender.sendMessage(Messages.disabledCommand)
            return false
        }

        return if (sender is Player) {
            var hand = sender.inventory.itemInMainHand
            if (hand.amount == 0) {
                sender.sendMessage("${ChatColor.RED}You need to hold an item to use this command.")
                return true
            }
            val toBeAdded = getArguments(args) // stuff to add
            val handEnchants = Enchanting.getItemCustomEnchants(hand)
            // enchantmentwrapper, reason
            val ignored: MutableMap<CustomEnchantment, String> = HashMap() // ignored stuffs (for debug mainly i guess)
            val cannotEnchant = "this item cannot have this enchantment."
            val cannotAfford = "you do not have the required materials for apply this enchantment."
            val alreadyContains = "this item already has this enchantment at a higher level."
            val conflicts = "this enchantment conflicts with another enchantment on this item."
            // gets all the enchants that need to be ignored
            for ((e, level) in toBeAdded) {

                // is there a higher level
                if (level < handEnchants.getOrDefault(e, 0)) {
                    ignored[e] = alreadyContains
                    continue
                }

                // can the item be enchanted
                if (!Enchanting.canEnchant(hand, e)) {
                    ignored[e] = cannotEnchant
                    continue
                }

                // can the player afford
                if (getCostData(e, sender.inventory, level) == null) {
                    ignored[e] = cannotAfford
                }

                // are there any conflicts (currently unused)
                /*
                for (enchantment in handEnchants.keys.toList()) {
                    if (e.conflictsWith(enchantment)) {
                        ignored[e] = conflicts
                        break
                    }
                }
                */
            }

            // remove all enchantments that should not be there
            for (e in ignored.keys) {
                toBeAdded.remove(e)
            }

            // apply cost
            for ((e, level) in toBeAdded) {
                val costData = getCostData(e, sender.inventory, level)
                if (costData != null) {
                    val indices = costData.indices
                    for (i in indices) {
                        sender.inventory.clear(i)
                    }
                    val overflow = costData.lastOverflow
                    if (overflow != 0) {
                        sender.inventory.setItem(indices[indices.size - 1], ItemStack(e.getCost(level).type, overflow))
                    }
                }
            }

            // adds enchants
            for ((e, level) in toBeAdded) {
                val newLevel = Enchanting.calculateLevel(e, level, handEnchants.getOrDefault(e, 0))
                val temp = Enchanting.addEnchant(hand, e, newLevel)
                if (temp != null) hand = temp
            }
            sender.inventory.setItemInMainHand(hand)
            val message = StringBuilder(
                """
    ${ChatColor.GREEN}Applied the following enchants:
    
    """.trimIndent()
            )
            for ((key, value) in toBeAdded) {
                message.append("+ ")
                    .append(key.name)
                    .append(" ")
                    .append(General.toRoman(value))
                    .append("\n")
            }
            message.append(ChatColor.RED)
            message.append("Ignored the following enchants:\n")
            for ((key, value) in ignored) {
                message.append("- ")
                    .append(key.name)
                    .append(" because ")
                    .append(value)
                    .append("\n")
            }
            sender.sendMessage(message.toString())
            true
        } else {
            sender.sendMessage("${ChatColor.RED}You need to be a player to use this command.")
            false
        }
    }

    private fun getArguments(args: Array<String>): MutableMap<CustomEnchantment, Int> {
        // returns a map with all the enchants that need to be applied
        val arguments: MutableMap<CustomEnchantment, Int> = HashMap()
        for (i in args.indices) {
            try {
                args[i].toInt() // illegal argument, so ignore it
            } catch (e: NumberFormatException) {
                // its an enchant (poggers)
                val enchant = try {
                    CustomEnchants.getCustomEnchant(args[i])
                } catch (e1: NoSuchElementException) {
                    // enchant is not a custom one
                    continue
                }
                val level: Int = try {
                    // next arg is a number
                    args[i + 1].toInt()
                } catch (e2: NumberFormatException) {
                    // next arg is not a number
                    1
                } catch (e2: ArrayIndexOutOfBoundsException) {
                    1
                }
                arguments[enchant] = level
            }
        }
        return arguments
    }

    private fun getCostData(customEnchantment: CustomEnchantment, inventory: Inventory, level: Int): CostData? {
        // returns null if cannot afford
        val cost = customEnchantment.getCost(level)
        val indices = mutableListOf<Int>()
        var lastOverflow = -1 // how much to remove
        val contents = inventory.contents
        var count = 0
        var c = 0
        for (slot in contents) {
            if (slot == null || slot.type != cost.type) {
                c++
                continue
            }
            count += slot.amount
            indices.add(c)
            if (count >= cost.amount) {
                lastOverflow = count - cost.amount
                break
            }
            c++
        }
        if (lastOverflow == -1) {
            return null
        }
        val poggies = IntArray(indices.size)
        for (i in poggies.indices) {
            poggies[i] = indices[i]
        }
        return CostData(poggies, lastOverflow)
    }
}
