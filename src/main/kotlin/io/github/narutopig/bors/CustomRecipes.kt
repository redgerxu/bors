package io.github.narutopig.bors

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe


object CustomRecipes {
    private val saddle = saddleRecipe()
    private val trident = tridentRecipe()
    private val netherStar = netherStar()
    private val shulkerShell = shulkerShell()

    val recipes = listOf(
        netherStar,
        saddle,
        shulkerShell,
        trident,
    )

    private fun tridentRecipe(): ShapedRecipe {
        val trident = ItemStack(Material.TRIDENT)
        val key = NamespacedKey.minecraft("trident")
        val recipe = ShapedRecipe(key, trident)
        recipe.shape("IDI", "IHI", "ISI")
        recipe.setIngredient('I', Material.IRON_BLOCK)
        recipe.setIngredient('D', Material.DIAMOND)
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA)
        recipe.setIngredient('S', Material.STICK)
        return recipe
    }

    private fun saddleRecipe(): ShapedRecipe {
        val saddle = ItemStack(Material.SADDLE)
        val key = NamespacedKey.minecraft("saddle")
        val recipe = ShapedRecipe(key, saddle)
        recipe.shape("LLL", "SUS", "S S")
        // amogus doot doot doot doot doot doot doot dududoot BUM BUM
        recipe.setIngredient('L', Material.LEATHER)
        recipe.setIngredient('U', Material.IRON_INGOT)
        recipe.setIngredient('S', Material.STRING)
        return recipe
    }

    private fun netherStar(): ShapedRecipe {
        val netherStar = ItemStack(Material.NETHER_STAR)
        val key = NamespacedKey.minecraft("nether_star")
        val recipe = ShapedRecipe(key, netherStar)
        recipe.shape("IDI", "DND", "IDI")
        recipe.setIngredient('I', Material.IRON_BLOCK)
        recipe.setIngredient('D', Material.DIAMOND_BLOCK)
        recipe.setIngredient('N', Material.NETHERITE_INGOT)
        return recipe
    }

    private fun shulkerShell(): ShapedRecipe {
        val shulkerShell = ItemStack(Material.SHULKER_SHELL)
        val key = NamespacedKey.minecraft("shulker_shell")
        val recipe = ShapedRecipe(key, shulkerShell)
        recipe.shape("DPD")
        recipe.setIngredient('D', Material.DIAMOND)
        recipe.setIngredient('P', Material.ENDER_PEARL)
        return recipe
    }
}