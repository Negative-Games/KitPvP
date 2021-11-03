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

package dev.negativekb.kitpvpframework.placeholders;

import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.api.placeholder.PAPIPlaceholder;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import dev.negativekb.kitpvpframework.core.util.Utils;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DeathsPlaceholder extends PAPIPlaceholder {
    private final ProfileManager profileManager;

    public DeathsPlaceholder() {
        profileManager = getApi().getProfileManager();
    }

    @Override
    public String getIdentifier() {
        return "deaths";
    }

    @Override
    public int triggerOnArgument() {
        return 0;
    }

    @Override
    public String onRequest(Player player, String[] paths) {
        Optional<Profile> profile = profileManager.getProfile(player);
        return (profile.isPresent() ? Utils.decimalFormat(profile.get().getDeaths()) : String.valueOf(0));
    }
}
