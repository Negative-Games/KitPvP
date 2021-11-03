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

package dev.negativekb.kitpvpframework.core.util;

import dev.negativekb.kitpvpframework.KitPvPFramework;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.core.structure.cooldown.Cooldown;
import dev.negativekb.kitpvpframework.core.structure.cooldown.CooldownType;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import lombok.experimental.UtilityClass;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@UtilityClass
public class Cooldowns {

    private final ProfileManager profileManager;

    static {
        profileManager = KitPvPAPI.getInstance().getProfileManager();

        new CooldownTimerTask().runTaskTimerAsynchronously(KitPvPFramework.getInstance(), 0, 20);
    }

    public boolean checkCooldown(UUID uuid, CooldownType type) {
        Optional<Profile> profile = profileManager.getProfile(uuid);
        assert profile.isPresent();

        Profile stats = profile.get();
        return stats.getCooldowns().stream().anyMatch(cooldown -> cooldown.getType().equals(type));
    }

    public void addCooldown(UUID uuid, CooldownType type, long duration) {
        if (checkCooldown(uuid, type))
            return;

        Optional<Profile> profile = profileManager.getProfile(uuid);
        assert profile.isPresent();

        Profile stats = profile.get();
        Cooldown cooldown = new Cooldown(stats.generateCooldownID(), type, System.currentTimeMillis(), (System.currentTimeMillis() + duration));
        stats.addCooldown(cooldown);
    }

    public void removeCooldown(UUID uuid, CooldownType type) {
        if (!checkCooldown(uuid, type))
            return;

        Optional<Profile> profile = profileManager.getProfile(uuid);
        assert profile.isPresent();

        Profile stats = profile.get();
        Optional<Cooldown> cd = stats.getCooldown(type);
        assert cd.isPresent();

        Cooldown cooldown = cd.get();
        stats.removeCooldown(cooldown);
    }

    private class CooldownTimerTask extends BukkitRunnable {

        @Override
        public void run() {
            ArrayList<Profile> profiles = profileManager.getProfiles();
            profiles.stream().filter(profile -> profile.getCooldowns() != null && !profile.getCooldowns().isEmpty()).forEach(profile -> {
                ArrayList<Cooldown> cooldowns = profile.getCooldowns();
                ArrayList<Cooldown> expiredCooldowns = new ArrayList<>();
                cooldowns.stream().filter(cooldown -> (System.currentTimeMillis() >= cooldown.getDateExpired())).forEach(expiredCooldowns::add);

                expiredCooldowns.forEach(profile::removeCooldown);
            });
        }
    }
}
