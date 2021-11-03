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

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Combat Manager Module
 *
 * @author Negative
 * @since October 28th, 2021
 *
 * This module will manage all the users in Combat Tag
 *
 * You can access this module by using {@link KitPvPAPI#getCombatManager()}
 */
public interface CombatManager {

    /**
     * Checks if a UUID is in Combat
     * @param uuid UUID
     * @return If the UUID instance is in the combat tag map, return true.
     */
    boolean isInCombat(UUID uuid);

    /**
     * Checks if a Player is in Combat
     * @param player Player
     * @return If the Player instance is in the combat tag map, return true.
     */
    boolean isInCombat(Player player);

    /**
     * Adds a UUID to the combat map or updates the UUID in the combat map to the new time
     * @param uuid UUID
     * @param duration Duration in {@link java.util.concurrent.TimeUnit#MILLISECONDS}
     */
    void addOrUpdateCombat(UUID uuid, long duration, boolean message);

    /**
     * Removes a UUID from the combat map
     * @param uuid UUID
     * @param message Message the player if they are online
     */
    void removeFromCombat(UUID uuid, boolean message);

}
