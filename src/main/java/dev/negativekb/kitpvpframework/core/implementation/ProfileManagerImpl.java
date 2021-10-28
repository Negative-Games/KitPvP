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

import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.core.cache.ObjectCache;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class ProfileManagerImpl extends ObjectCache<Profile> implements ProfileManager {

    private ArrayList<Profile> profiles;

    @SneakyThrows
    public ProfileManagerImpl(JavaPlugin plugin) {
        super(plugin.getDataFolder().getPath() + "/data/profiles.json", Profile[].class);

        profiles = load();

        new ProfileSaveTask().runTaskTimerAsynchronously(plugin, 0, 20 * 60);
    }

    @Override
    public Profile addProfile(UUID uuid) {
        if (getProfile(uuid).isPresent())
            return getProfile(uuid).get();

        Profile profile = new Profile(uuid);
        profiles.add(profile);
        return profile;
    }

    @Override
    public void removeProfile(Profile profile) {
        profiles.remove(profile);
    }

    @Override
    public Optional<Profile> getProfile(UUID uuid) {
        return profiles.stream().filter(profile -> profile.getUniqueID().equals(uuid)).findFirst();
    }

    @Override
    public Optional<Profile> getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }

    @Override
    public Optional<Profile> getProfile(OfflinePlayer player) {
        return getProfile(player.getUniqueId());
    }

    @Override
    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        save(profiles);
    }

    private class ProfileSaveTask extends BukkitRunnable {

        @SneakyThrows
        @Override
        public void run() {
            save(profiles);
            profiles = load();
        }
    }
}
