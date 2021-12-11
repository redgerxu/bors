package me.narutopig.bors;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static me.narutopig.bors.Util.itemStack;

public class CustomEnchants {
    public static final EnchantmentWrapper EXPERIENCE = new EnchantmentWrapper(
            "experience",
            "Experience",
            5,
            itemStack(Material.LAPIS_BLOCK, 1),
            new Enchantment[]{Enchantment.MENDING}
    );

    public static final EnchantmentWrapper AFTERMATH = new EnchantmentWrapper(
            "aftermath",
            "Aftermath",
            1,
            itemStack(Material.TNT, 1)
    );

    public static final EnchantmentWrapper TELEKINESIS = new EnchantmentWrapper(
            "telekinesis",
            "Telekinesis",
            1,
            itemStack(Material.ENDER_PEARL, 16)
    );

    public static final List<EnchantmentWrapper> customEnchants = Arrays.asList(
            AFTERMATH,
            EXPERIENCE,
            TELEKINESIS
    );

    public static void register() {
        List<Boolean> registered = new ArrayList<>();

        for (Enchantment enchantment : customEnchants) {
            registered.add(Arrays.stream(Enchantment.values()).toList().contains(enchantment));
        }

        for (int i = 0; i < customEnchants.size(); i++) {
            if (!registered.get(i)) registerEnchant(customEnchants.get(i));
        }
    }

    public static void registerEnchant(Enchantment enchantment) {
        // definitely not copied

        boolean registered = true;

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }

        if (registered) {
            System.out.println(enchantment.getName() + " was registered!");
        }
    }
}
