package io.github.narutopig.bors.listeners.enchantments

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.enchanting.Enchanting
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.enchantments.Enchantment
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import kotlin.math.floor

// i hate recursion
class RecursiveThing {
    constructor(world: World, blockType: Material, hand: ItemStack, limit: Int) {
        this.world = world
        this.blockType = blockType
        this.hand = hand
        this.limit = limit
    }

    var world: World
    var blockType: Material
    private val hand: ItemStack
    private val limit: Int
    var count = 0

    fun someRecursiveThing(block: Block) {
        if (block.type != blockType  || count >= limit) {
        } else {
            world.getBlockAt(block.location).breakNaturally(hand)
            count++
            val loc = block.location
            someRecursiveThing(world.getBlockAt(loc.add(1.0, 0.0, 0.0)))
            someRecursiveThing(world.getBlockAt(loc.add(-1.0, 0.0, 0.0)))
            someRecursiveThing(world.getBlockAt(loc.add(0.0, 1.0, 0.0)))
            someRecursiveThing(world.getBlockAt(loc.add(0.0, -1.0, 0.0)))
            someRecursiveThing(world.getBlockAt(loc.add(0.0, 0.0, 1.0)))
            someRecursiveThing(world.getBlockAt(loc.add(0.0, 0.0, -1.0)))
        }
    }
}

class AutoMinerEnchant : Listener {
    // WARNING: currently bugged
    @EventHandler
    fun onPlayerBreakBlock(event: BlockBreakEvent) {
        if (!Main.configuration.getBoolean("enchantments.autominer")) return

        val player = event.player
        val hand = player.inventory.itemInMainHand

        if (hand.amount == 0) return

        val block = event.block
        if (!block.isPreferredTool(hand)) return

        if (!block.type.toString().endsWith("_ORE") && !block.type.toString().endsWith("_LOG")) return

        val enchants = Enchanting.getItemCustomEnchants(hand)

        val lvl = enchants.getOrDefault(CustomEnchants.AUTOMINER, 0)
        if (lvl == 0) return

        val recursiveThing = RecursiveThing(block.world, block.type, hand, lvl * 4)
        recursiveThing.someRecursiveThing(block)

        val count = recursiveThing.count

        if (count <= 2) return

        val durabilityCost = count * 3
        val unbreakingLevel = hand.getEnchantmentLevel(Enchantment.DURABILITY)

        val reduction = 1.0 / (unbreakingLevel + 1.0)
        val removedDurability = (durabilityCost * reduction).toInt().toShort()

        hand.durability = (hand.durability + removedDurability).toShort()

        event.player.inventory.setItemInMainHand(hand)
    }
}