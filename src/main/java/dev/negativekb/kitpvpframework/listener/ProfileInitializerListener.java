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

package dev.negativekb.kitpvpframework.listener;

import dev.negativekb.kitpvpframework.api.KitManager;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileInitializerListener implements Listener {

    private final ProfileManager profileManager;
    private final KitManager kitManager;

    public ProfileInitializerListener() {
        KitPvPAPI api = KitPvPAPI.getInstance();
        profileManager = api.getProfileManager();
        kitManager = api.getKitManager();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // If the profile already exists, it will not
        // create a new Profile instance.
        Profile profile = profileManager.addProfile(player.getUniqueId());
        checkKits(profile);
    }

    /**
     * Checks all the kits that register automatically
     * and if you do not have that kit unlocked, it will
     * unlock it for you.
     *
     * @param profile Profile instance
     */
    private void checkKits(Profile profile) {
        ArrayList<Kits> unlockedKits = profile.getUnlockedKits();
        List<Kit> automaticallyUnlockedKits = kitManager.getKits().stream()
                .filter(kit -> kit.isAutomaticallyUnlocked() && !unlockedKits.contains(kit.getType()))
                .collect(Collectors.toList());

        automaticallyUnlockedKits.forEach(kit -> profile.addUnlockedKit(kit.getType()));
    }
}
