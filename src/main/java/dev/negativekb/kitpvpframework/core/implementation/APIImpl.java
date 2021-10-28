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

import dev.negativekb.kitpvpframework.api.*;
import dev.negativekb.kitpvpframework.api.options.Disableable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class APIImpl extends KitPvPAPI {

    private final ProfileManager profileManager;
    private final AbilityItemManager abilityItemManager;
    private final CosmeticManager cosmeticManager;
    private final KitManager kitManager;
    private final CombatManager combatManager;

    private final ArrayList<Object> disableableCache = new ArrayList<>();

    public APIImpl(JavaPlugin plugin) {
        profileManager = new ProfileManagerImpl(plugin);
        attemptAddDisableable(profileManager);

        abilityItemManager = new AbilityItemManagerImpl();
        attemptAddDisableable(abilityItemManager);

        cosmeticManager = new CosmeticManagerImpl();
        attemptAddDisableable(cosmeticManager);

        kitManager = new KitManagerImpl();
        attemptAddDisableable(kitManager);

        combatManager = new CombatManagerImpl(plugin);
        attemptAddDisableable(combatManager);
    }

    @Override
    public ProfileManager getProfileManager() {
        return profileManager;
    }

    @Override
    public AbilityItemManager getAbilityItemManager() {
        return abilityItemManager;
    }

    @Override
    public CosmeticManager getCosmeticManager() {
        return cosmeticManager;
    }

    @Override
    public KitManager getKitManager() {
        return kitManager;
    }

    @Override
    public CombatManager getCombatManager() {
        return combatManager;
    }

    @Override
    public void onDisable() {
        // In theory should work...
        disableableCache.forEach(o -> {
            Disableable disableable = (Disableable) o;
            disableable.onDisable();
        });
//        profileManager.onDisable();
//        cosmeticManager.onDisable();
//        kitManager.onDisable();
    }

    private void attemptAddDisableable(Object o) {
        if (o instanceof Disableable)
            disableableCache.add(o);
    }

}
