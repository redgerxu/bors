package io.github.narutopig.bors.commands

import io.github.narutopig.bors.enchantments.CostData
import io.github.narutopig.bors.enchantments.CustomEnchants
import io.github.narutopig.bors.enchantments.EnchantmentWrapper
import io.github.narutopig.bors.util.Enchanting
import io.github.narutopig.bors.util.General
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import javax.annotation.Nonnull

class BoRSEnchant : CommandExecutor {
    override fun onCommand(
        @Nonnull sender: CommandSender,
        @Nonnull command: Command,
        label: String,
        @Nonnull args: Array<String>
    ): Boolean {
        return if (sender is Player) {
            var hand = sender.inventory.itemInMainHand
            if (hand.amount == 0) {
                sender.sendMessage("${ChatColor.RED}You need to hold an item to use this command.")
                return false
            }
            val toBeAdded = getArguments(args) // stuff to add
            val handEnchants = hand.enchantments // current enchants on item
            // enchantmentwrapper, reason
            val ignored: MutableMap<EnchantmentWrapper, String> = HashMap() // ignored stuffs (for debug mainly i guess)
            val cannotEnchant = "this item cannot have this enchantment."
            val cannotAfford = "you do not have the required materials for apply this enchantment."
            val alreadyContains = "this item already has this enchantment at a higher level."
            val conflicts = "this enchantment conflicts with another enchantment on this item."
            val toBeRemoved: MutableList<EnchantmentWrapper> = ArrayList()
            val handEnchantList: List<Enchantment> = ArrayList(handEnchants.keys)

            // gets all the enchants that need to be ignored
            for ((e, level) in toBeAdded) {

                // is there a higher level
                if (level < handEnchants.getOrDefault(e, 0)) {
                    toBeRemoved.add(e)
                    ignored[e] = alreadyContains
                    continue
                }

                // can the item be enchanted
                if (!Enchanting.canEnchant(hand, e)) {
                    toBeRemoved.add(e)
                    ignored[e] = cannotEnchant
                    continue
                }

                // can the player afford
                if (getCostData(e, sender.inventory, level) == null) {
                    toBeRemoved.add(e)
                    ignored[e] = cannotAfford
                }

                // are there any conflicts
                for (enchantment in handEnchantList) {
                    if (e.conflictsWith(enchantment)) {
                        toBeRemoved.add(e)
                        ignored[e] = conflicts
                        break
                    }
                }
            }

            // remove all enchantments that should not be there
            for (e in toBeRemoved) {
                toBeAdded.remove(e)
            }
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

    private fun getArguments(args: Array<String>): MutableMap<EnchantmentWrapper, Int> {
        // returns a map with all the enchants that need to be applied
        val arguments: MutableMap<EnchantmentWrapper, Int> = HashMap()
        for (i in args.indices) {
            try {
                args[i].toInt() // illegal argument, so ignore it
            } catch (e: NumberFormatException) {
                // its an enchant (poggers)
                val enchant: EnchantmentWrapper = try {
                    CustomEnchants.customEnchants.stream().filter { pog: EnchantmentWrapper? ->
                        pog!!.name.equals(
                            args[i], ignoreCase = true
                        )
                    }.toArray()[0] as EnchantmentWrapper
                } catch (e1: ArrayIndexOutOfBoundsException) {
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

    private fun getCostData(enchantmentWrapper: EnchantmentWrapper, inventory: Inventory, level: Int): CostData? {
        // returns null if cannot afford
        val cost = enchantmentWrapper.getCost(level)
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
