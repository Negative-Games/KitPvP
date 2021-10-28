/*
 * MIT License
 *
 * Copyright (c) 2021 Negative
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    @Override
    public void onDisable() {
        map.forEach((abilityItemType, abilityItem) -> abilityItem.onDisable());
    }
}
