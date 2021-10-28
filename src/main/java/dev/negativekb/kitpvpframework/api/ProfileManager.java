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
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

/**
 * Profile Manager Module
 *
 * @author Negative
 * @since October 27th, 2021
 * <p>
 * This module will manage and contain all player data / profiles
 */
public interface ProfileManager extends Disableable {

    /**
     * Adds a new user to the Profile cache
     *
     * @param uuid UUID of the new Profile
     * @return Creates a new Profile there is no existing profile. Returns the Profile instance
     */
    Profile addProfile(UUID uuid);

    /**
     * Removes a Profile from the cache, which will essentially "delete" it
     *
     * @param profile Profile instance
     */
    void removeProfile(Profile profile);

    /**
     * Gets the corresponding Profile from the provided {@link UUID}
     *
     * @param uuid UUID of the potential Profile
     * @return If the corresponding {@link UUID} exists inside the Profile cache, return. If not, return empty.
     */
    Optional<Profile> getProfile(UUID uuid);

    /**
     * Gets the corresponding Profile from the provided {@link Player}
     *
     * @param player Player instance of the potential Profile
     * @return If the corresponding {@link Player} exists inside the Profile cache, return. If not, return empty.
     */
    Optional<Profile> getProfile(Player player);

    /**
     * Gets the corresponding Profile from the provided {@link OfflinePlayer}
     *
     * @param player Player instance of the potential Profile
     * @return If the corresponding {@link OfflinePlayer} exists inside the Profile cache, return. If not, return empty.
     */
    Optional<Profile> getProfile(OfflinePlayer player);

    /**
     * Returns the Profile cache
     */
    ArrayList<Profile> getProfiles();

}
