package io.github.narutopig.bors.enchanting.enchantments

import io.github.narutopig.bors.Main
import io.github.narutopig.bors.enchanting.CustomEnchants
import io.github.narutopig.bors.util.Enchanting
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

// i hate recursion
class RecursiveThing {
    constructor(world: World, blockType: Material) {
        this.world = world
        this.blockType = blockType
    }

    var world: World
    var blockType: Material
    val blocks = mutableSetOf<Block>()


    fun someRecursiveThing(block: Block) {
        if (block.type != blockType || blocks.contains(block)) {
            return
        } else {
            blocks.add(block)
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

class BlockDistComparator : Comparator<Block> {
    lateinit var center: Block

    override fun compare(p0: Block, p1: Block): Int {
        val d0 = p0.location.distance(center.location)
        val d1 = p1.location.distance(center.location)
        if (d0 == d1) return 0
        return d0.compareTo(d1)
    }
}

class VeinMinerEnchant : Listener {
    // WARNING: currently bugged
    @EventHandler
    fun onPlayerBreakBlock(event: BlockBreakEvent) {
        if (!Main.configuration.getBoolean("enchantments.veinminer")) return

        val player = event.player
        val hand = player.inventory.itemInMainHand

        if (hand.amount == 0) return

        val block = event.block
        if (!block.isPreferredTool(hand)) return
        if (!block.type.toString().endsWith("_ORE")) return

        val enchants = Enchanting.getItemCustomEnchants(hand)

        val lvl = enchants.getOrDefault(CustomEnchants.VEINMINER, 0)
        if (lvl == 0) return

        val temp = RecursiveThing(block.world, block.type)
        temp.world = block.world
        temp.blockType = block.type
        temp.someRecursiveThing(block)
        val comp = BlockDistComparator()
        comp.center = block
        val blocks = temp.blocks.sortedWith(comp).toMutableList()

        while (blocks.size > lvl * 3) {
            blocks.removeLast()
        }

        player.sendMessage("Mined ${blocks.size} blocks")
        for (block in blocks) {
            block.breakNaturally(hand)
        }
    }
}