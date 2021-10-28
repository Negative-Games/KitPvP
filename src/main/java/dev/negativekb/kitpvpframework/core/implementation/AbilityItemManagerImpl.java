package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.AbilityItemManager;
import dev.negativekb.kitpvpframework.core.structure.ability.AbilityItem;
import dev.negativekb.kitpvpframework.core.structure.ability.AbilityItemType;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Map.Entry;

public class AbilityItemManagerImpl implements AbilityItemManager {

    @Getter
    private final HashMap<AbilityItemType, AbilityItem> map = new HashMap<>();

    @Override
    public void registerItem(AbilityItem item) {
        map.put(item.getType(), item);
    }

    @Override
    public void unRegisterItem(AbilityItem item) {
        unRegisterItem(item.getType());
    }

    @Override
    public void unRegisterItem(AbilityItemType type) {
        map.remove(type);
    }

    @Override
    public Optional<AbilityItem> getItem(AbilityItemType type) {
        return Optional.ofNullable(map.get(type));
    }

    @Override
    public Optional<AbilityItem> getItem(ItemStack itemStack) {
        if (itemStack == null)
            return Optional.empty();

        Optional<Map.Entry<AbilityItemType, AbilityItem>> entry = map.entrySet()
                .stream()
                .filter(e -> e.getValue().getItem().isSimilar(itemStack))
                .findFirst();
        return entry.map(Entry::getValue);
    }
}
