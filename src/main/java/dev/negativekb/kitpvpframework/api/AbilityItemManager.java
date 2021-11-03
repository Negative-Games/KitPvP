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

package dev.negativekb.kitpvpframework.api;

import dev.negativekb.kitpvpframework.abilityitems.AbilityItemType;
import dev.negativekb.kitpvpframework.api.options.Disableable;
import dev.negativekb.kitpvpframework.core.structure.ability.AbilityItem;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Ability Item Manager Module
 *
 * @author Negative
 * @since October 27th, 2021
 * <p>
 * This module is the manager for ability items
 * <p>
 * You can access this module by using {@link KitPvPAPI#getAbilityItemManager()}
 */
public interface AbilityItemManager extends Disableable {

    /**
     * Register a Ability Item
     *
     * @param item AbilityItem instance
     */
    void registerItem(AbilityItem item);

    /**
     * Unregister an Ability Item
     *
     * @param item AbilityItem instance
     */
    void unRegisterItem(AbilityItem item);

    /**
     * Unregister an Ability Item
     *
     * @param type AbilityItem type
     */
    void unRegisterItem(AbilityItemType type);

    /**
     * Get an AbilityItem instance from {@link AbilityItemType}
     *
     * @param type Type of the AbilityItem
     * @return If the item corresponding to the {@link AbilityItemType} is registered in the cache, return. If not, return empty.
     */
    Optional<AbilityItem> getItem(AbilityItemType type);

    /**
     * Get an AbilityItem instance from {@link ItemStack}
     *
     * @param itemStack ItemStack instance
     * @return If the item corresponding to the {@link ItemStack} matches a registered AbilityItem, return. If not, return empty.
     */
    Optional<AbilityItem> getItem(ItemStack itemStack);

}
