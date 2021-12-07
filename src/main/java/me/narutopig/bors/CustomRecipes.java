package me.narutopig.bors;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.List;

public class CustomRecipes {
    public static final ShapedRecipe bundle = bundleRecipe();
    public static final ShapedRecipe experienceBottle = experienceBottleRecipe();
    public static final ShapedRecipe saddle = saddleRecipe();
    public static final ShapedRecipe trident = tridentRecipe();
    public static final List<ShapedRecipe> recipes = Arrays.asList(bundle, experienceBottle, saddle, trident);

    public static ShapedRecipe bundleRecipe() {
        ItemStack bundle = new ItemStack(Material.BUNDLE);
        NamespacedKey key = NamespacedKey.minecraft("bundle");

        ShapedRecipe recipe = new ShapedRecipe(key, bundle);
        recipe.shape("SUS", "U U", "UUU"); // sussus amogus

        recipe.setIngredient('S', Material.STRING);
        recipe.setIngredient('U', Material.LEATHER);

        return recipe;
    }

    public static ShapedRecipe experienceBottleRecipe() {
        ItemStack expBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
        NamespacedKey key = NamespacedKey.minecraft("experience_bottle");

        ShapedRecipe recipe = new ShapedRecipe(key, expBottle);
        recipe.shape(" G ", "GLG", "GGG");

        recipe.setIngredient('G', Material.GLASS);
        recipe.setIngredient('L', Material.LAPIS_LAZULI);

        return recipe;
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
