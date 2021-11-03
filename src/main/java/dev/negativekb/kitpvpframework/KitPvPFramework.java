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

package dev.negativekb.kitpvpframework;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.registry.*;
import dev.negativekb.kitpvpframework.commands.kits.CommandKit;
import dev.negativekb.kitpvpframework.commands.kits.CommandViewKit;
import dev.negativekb.kitpvpframework.commands.spawn.CommandSetSpawn;
import dev.negativekb.kitpvpframework.commands.spawn.CommandSpawn;
import dev.negativekb.kitpvpframework.commands.warps.CommandDeleteWarp;
import dev.negativekb.kitpvpframework.commands.warps.CommandSetWarp;
import dev.negativekb.kitpvpframework.commands.warps.CommandWarp;
import dev.negativekb.kitpvpframework.core.Locale;
import dev.negativekb.kitpvpframework.core.implementation.APIProvider;
import dev.negativekb.kitpvpframework.core.implementation.registry.*;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.items.BloodExplosionKillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.items.DefaultKillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.items.DefaultKillSound;
import dev.negativekb.kitpvpframework.kits.items.ExampleKit;
import dev.negativekb.kitpvpframework.listener.GUIListener;
import dev.negativekb.kitpvpframework.listener.PlayerListener;
import dev.negativekb.kitpvpframework.listener.PlayerRegionListener;
import dev.negativekb.kitpvpframework.listener.ProfileInitializerListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class KitPvPFramework extends JavaPlugin {

    @Getter
    private static KitPvPFramework instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Locale.init();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        new APIProvider(this);

        CommandRegistry commandRegistry = new CommandRegisterProvider();
        ListenerRegistry listenerRegistry = new ListenerRegisterProvider(this);
        CosmeticRegistry cosmeticRegistry = new CosmeticRegistryProvider();
        KitRegistry kitRegistry = new KitRegistryProvider();
        AbilityItemRegistry abilityItemRegistry = new AbilityItemRegistryProvider();

        commandRegistry.register(
                new CommandKit(),
                new CommandViewKit(),
                new CommandSpawn(),
                new CommandSetSpawn(),
                new CommandDeleteWarp(),
                new CommandSetWarp(),
                new CommandWarp()
        );

        listenerRegistry.register(
                new GUIListener(),
                new PlayerListener(this),
                new ProfileInitializerListener(),
                new PlayerRegionListener()
        );

        cosmeticRegistry.register(
                // Kill Effects
                new BloodExplosionKillEffect(),

                // Kill Messages
                new DefaultKillMessage(),

                // Kill Sounds
                new DefaultKillSound()
        );

        kitRegistry.register(
                new ExampleKit()
        );

        abilityItemRegistry.register(

        );
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        KitPvPAPI api = KitPvPAPI.getInstance();
        api.onDisable();
    }
}
