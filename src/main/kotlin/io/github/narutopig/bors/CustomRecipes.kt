package io.github.narutopig.bors

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe

object CustomRecipes {
    private val bundle = bundleRecipe()
    private val experienceBottle = experienceBottleRecipe()
    private val minerHelmet = minerHelmetRecipe()
    private val minerChestplate = minerChestplateRecipe()
    private val minerLeggings = minerLeggingsRecipe()
    private val minerBoots = minerBootsRecipe()
    private val saddle = saddleRecipe()
    private val trident = tridentRecipe()

    val recipes = listOf(
        bundle,
        experienceBottle,
        minerHelmet,
        minerChestplate,
        minerLeggings,
        minerBoots,
        saddle,
        trident
    )

    private fun bundleRecipe(): ShapedRecipe {
        val bundle = ItemStack(Material.BUNDLE)
        val key = NamespacedKey.minecraft("bundle")
        val recipe = ShapedRecipe(key, bundle)
        recipe.shape("SUS", "U U", "UUU") // sussus amogus
        recipe.setIngredient('S', Material.STRING)
        recipe.setIngredient('U', Material.LEATHER)
        return recipe
    }

    private fun experienceBottleRecipe(): ShapedRecipe {
        val expBottle = ItemStack(Material.EXPERIENCE_BOTTLE)
        val key = NamespacedKey.minecraft("experience_bottle")
        val recipe = ShapedRecipe(key, expBottle)
        recipe.shape(" G ", "GLG", "GGG")
        recipe.setIngredient('G', Material.GLASS)
        recipe.setIngredient('L', Material.LAPIS_LAZULI)
        return recipe
    }

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

    private fun minerHelmetRecipe(): ShapedRecipe {
        val item = ItemStack(Material.IRON_HELMET)
        // itemmeta is only null when it is air
        val itemMeta = item.itemMeta
        itemMeta?.setDisplayName("${ChatColor.DARK_AQUA}Miner's Helmet")
        item.setItemMeta(itemMeta) // IMPORTANT: must be setItemMeta
        val key = NamespacedKey(Main.instance, "miner_helmet")
        val recipe = ShapedRecipe(key, item)
        recipe.shape("DID", "D D")

        recipe.setIngredient('D', Material.COBBLED_DEEPSLATE)
        recipe.setIngredient('I', Material.IRON_BLOCK)

        return recipe
    }

    private fun minerChestplateRecipe(): ShapedRecipe {
        val item = ItemStack(Material.IRON_CHESTPLATE)
        val itemMeta = item.itemMeta
        itemMeta?.setDisplayName("${ChatColor.DARK_AQUA}Miner's Chestplate")
        item.setItemMeta(itemMeta) // IMPORTANT: must be setItemMeta
        val key = NamespacedKey(Main.instance, "miner_chestplate")
        val recipe = ShapedRecipe(key, item)
        recipe.shape("D D", "DID", "DDD")

        recipe.setIngredient('D', Material.COBBLED_DEEPSLATE)
        recipe.setIngredient('I', Material.IRON_BLOCK)

        return recipe
    }

    private fun minerLeggingsRecipe(): ShapedRecipe {
        val item = ItemStack(Material.IRON_LEGGINGS)
        val itemMeta = item.itemMeta
        itemMeta?.setDisplayName("${ChatColor.DARK_AQUA}Miner's Leggings")
        item.setItemMeta(itemMeta) // IMPORTANT: must be setItemMeta
        val key = NamespacedKey(Main.instance, "miner_leggings")
        val recipe = ShapedRecipe(key, item)
        recipe.shape("DID", "D D", "D D")

        recipe.setIngredient('D', Material.COBBLED_DEEPSLATE)
        recipe.setIngredient('I', Material.IRON_BLOCK)

        return recipe
    }

    private fun minerBootsRecipe(): ShapedRecipe {
        val item = ItemStack(Material.IRON_BOOTS)
        val itemMeta = item.itemMeta
        itemMeta?.setDisplayName("${ChatColor.DARK_AQUA}Miner's Boots")
        item.setItemMeta(itemMeta) // IMPORTANT: must be setItemMeta
        val key = NamespacedKey(Main.instance, "miner_boots")
        val recipe = ShapedRecipe(key, item)
        recipe.shape("I I", "I I")

        recipe.setIngredient('I', Material.IRON_BLOCK)

        return recipe
    }
}