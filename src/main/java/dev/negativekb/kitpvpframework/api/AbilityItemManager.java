package dev.negativekb.kitpvpframework.api;

import dev.negativekb.kitpvpframework.core.structure.ability.AbilityItem;
import dev.negativekb.kitpvpframework.core.structure.ability.AbilityItemType;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface AbilityItemManager {

    void registerItem(AbilityItem item);

    void unRegisterItem(AbilityItem item);

    void unRegisterItem(AbilityItemType type);

    Optional<AbilityItem> getItem(AbilityItemType type);

    Optional<AbilityItem> getItem(ItemStack itemStack);
}
