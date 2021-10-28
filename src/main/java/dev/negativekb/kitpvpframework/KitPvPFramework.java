package dev.negativekb.kitpvpframework;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.registry.CommandRegistry;
import dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry;
import dev.negativekb.kitpvpframework.api.registry.KitRegistry;
import dev.negativekb.kitpvpframework.api.registry.ListenerRegistry;
import dev.negativekb.kitpvpframework.core.Locale;
import dev.negativekb.kitpvpframework.core.implementation.APIImpl;
import dev.negativekb.kitpvpframework.core.implementation.registry.CommandRegisterImpl;
import dev.negativekb.kitpvpframework.core.implementation.registry.CosmeticRegistryImpl;
import dev.negativekb.kitpvpframework.core.implementation.registry.KitRegistryImpl;
import dev.negativekb.kitpvpframework.core.implementation.registry.ListenerRegisterImpl;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.items.BloodExplosionKillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.items.DefaultKillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.items.DefaultKillSound;
import dev.negativekb.kitpvpframework.kits.items.ExampleKit;
import dev.negativekb.kitpvpframework.listener.GUIListener;
import dev.negativekb.kitpvpframework.listener.PlayerListener;
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

        new APIImpl(this);

        CommandRegistry commandRegistry = new CommandRegisterImpl();
        ListenerRegistry listenerRegistry = new ListenerRegisterImpl(this);
        CosmeticRegistry cosmeticRegistry = new CosmeticRegistryImpl();
        KitRegistry kitRegistry = new KitRegistryImpl();

        commandRegistry.register(

        );

        listenerRegistry.register(
                new GUIListener(),
                new PlayerListener()
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
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        KitPvPAPI api = KitPvPAPI.getInstance();
        api.onDisable();
    }
}
