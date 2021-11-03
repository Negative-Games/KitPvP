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
import dev.negativekb.kitpvpframework.core.structure.region.Region;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

/**
 * Profile Manager Module
 *
 * @author Negative
 * @since October 28th, 2021
 * <p>
 * This module will manage all regions
 * <p>
 * You can access this module by using {@link KitPvPAPI#getRegionManager()}
 */
public interface RegionManager extends Disableable {

    /**
     * Create a Region with the provided name and location positions
     *
     * @param name      Region Name
     * @param position1 Position 1
     * @param position2 Position 2
     */
    void createRegion(String name, Location position1, Location position2);

    /**
     * Delete a Region instance from the cache
     *
     * @param region Region instance
     */
    void deleteRegion(Region region);

    /**
     * Get the Region with the highest priority at
     * the location provided
     *
     * @param location Location
     * @return If there is a Region at the provided location, return. If not, return empty.
     */
    Optional<Region> getRegion(Location location);

    /**
     * Get the Region with the name provided
     *
     * @param name Region Name
     * @return If there is a Region which has the name provided, return. If not, return empty.
     */
    Optional<Region> getRegion(String name);

    /**
     * Returns all the Regions at the provided location
     *
     * @param location Location
     * @return If there are regions at the location provided, return the list of regions, if not, return empty.
     */
    List<Region> getRegions(Location location);

    /**
     * Returns all the Regions at the provided player's location
     *
     * @param player Player instance
     * @return If there are regions at the provided player's location, return the list of regions, if not, return empty.
     */
    List<Region> getRegions(Player player);

    /**
     * Returns all the Regions registered inside the cache
     */
    List<Region> getAllRegions();

}
