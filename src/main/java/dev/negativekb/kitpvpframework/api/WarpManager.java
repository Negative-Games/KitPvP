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

import dev.negativekb.kitpvpframework.api.options.Disableable;
import dev.negativekb.kitpvpframework.core.structure.warp.Warp;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Warp Manager Module
 *
 * @author Negative
 * @since November 3rd, 2021
 * <p>
 * This module will manage all Warps and Warp teleportations
 * <p>
 * You can access this module by using {@link KitPvPAPI#getWarpManager()}
 */
public interface WarpManager extends Disableable {

    /**
     * Teleport
     *
     * @param warp    Warp instance
     * @param player  Player to be teleported
     * @param message Will the server send the {@link dev.negativekb.kitpvpframework.core.Locale#WARP_SUCCESS} message?
     */
    void teleport(Warp warp, Player player, boolean message);

    /**
     * Teleport
     *
     * @param warp   Warp instance
     * @param player Player to be teleported
     * @param delay  Delay in seconds until teleport is commenced
     * @return true if the teleport task has started
     */
    boolean teleport(Warp warp, Player player, int delay);

    /**
     * Teleport
     *
     * @param warp          Warp instance
     * @param player        Player to be teleported
     * @param delay         Delay in seconds until the teleport is commenced
     * @param afterTeleport Code to be run after teleport has completed
     * @return true if the teleport task has started
     */
    boolean teleport(Warp warp, Player player, int delay, Runnable afterTeleport);

    /**
     * Teleport
     *
     * @param warp          Warp instance
     * @param player        Player to be teleported
     * @param afterTeleport Code to be run after teleport has completed
     * @param message       Will the server send the {@link dev.negativekb.kitpvpframework.core.Locale#WARP_SUCCESS} message?
     */
    void teleport(Warp warp, Player player, Runnable afterTeleport, boolean message);

    /**
     * Create a Warp with the provided name and location
     *
     * @param name     Name of the Warp
     * @param location Location of the Warp
     */
    void createWarp(String name, Location location);

    /**
     * Delete/Remove a Warp from the cache
     *
     * @param warp Warp instance
     */
    void deleteWarp(Warp warp);

    /**
     * Get a Warp instance from the name provided
     *
     * @param name Name of the potential Warp
     * @return If the warp exists, return. If not, return empty.
     */
    Optional<Warp> getWarp(String name);

    /**
     * Return a list of all the Warp instances
     */
    ArrayList<Warp> getWarps();
}
