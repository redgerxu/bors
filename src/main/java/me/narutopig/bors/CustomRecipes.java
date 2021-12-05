package me.narutopig.bors;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.ArrayList;
import java.util.List;

public class CustomRecipes {
    public static final ShapedRecipe trident = tridentRecipe();
    public static final ShapedRecipe saddle = saddleRecipe();
    public static final List<ShapedRecipe> recipes = new ArrayList<>();

    static {
        recipes.add(trident);
        recipes.add(saddle);
    }

    public static ShapedRecipe tridentRecipe() {
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

    public static ShapedRecipe saddleRecipe() {
        ItemStack saddle = new ItemStack(Material.SADDLE);
        NamespacedKey key = NamespacedKey.minecraft("saddle");

        ShapedRecipe recipe = new ShapedRecipe(key, saddle);
        recipe.shape("LLL", "SUS", "S S");
        // amogus doot doot doot doot doot doot doot dududoot BUM BUM

        recipe.setIngredient('L', Material.LEATHER);
        recipe.setIngredient('U', Material.IRON_INGOT);
        recipe.setIngredient('S', Material.STRING);

        return recipe;
    }
}
