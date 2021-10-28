package dev.negativekb.kitpvpframework.core.implementation.registry;

import dev.negativekb.kitpvpframework.api.CosmeticManager;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;

import java.util.Arrays;

public class CosmeticRegistryImpl implements CosmeticRegistry {
    @Override
    public void register(Object... clazzes) {
        CosmeticManager manager = KitPvPAPI.getInstance().getCosmeticManager();

        Arrays.stream(clazzes.clone()).filter(o -> o instanceof KillEffect).forEach(o -> {
            KillEffect effect = (KillEffect) o;
            manager.register(effect);
        });

        Arrays.stream(clazzes.clone()).filter(o -> o instanceof KillMessage).forEach(o -> {
            KillMessage effect = (KillMessage) o;
            manager.register(effect);
        });

        Arrays.stream(clazzes.clone()).filter(o -> o instanceof KillSound).forEach(o -> {
            KillSound effect = (KillSound) o;
            manager.register(effect);
        });
    }
}
