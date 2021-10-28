package dev.negativekb.kitpvpframework.core.structure.cosmetic;

import org.bukkit.inventory.ItemStack;

public interface Cosmetic {

    String getID();

    CosmeticType getCosmeticType();

    ItemStack getIcon();

}
