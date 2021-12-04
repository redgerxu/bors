package me.narutopig.bors.recipes;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class CustomRecipes {
    public static Recipe tridentRecipe() {
        ItemStack trident = new ItemStack(Material.TRIDENT);
        NamespacedKey key = NamespacedKey.minecraft("trident");

        ShapedRecipe recipe = new ShapedRecipe(key, trident);
        recipe.shape("IDI", "IHI", "ISI");

        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
        recipe.setIngredient('S', Material.STICK);

        return recipe;
    }
}
