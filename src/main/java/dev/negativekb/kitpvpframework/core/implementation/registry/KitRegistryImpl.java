package dev.negativekb.kitpvpframework.core.implementation.registry;

import dev.negativekb.kitpvpframework.api.KitManager;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.registry.KitRegistry;
import dev.negativekb.kitpvpframework.kits.Kit;

import java.util.Arrays;

public class KitRegistryImpl implements KitRegistry {
    @Override
    public void register(Kit... kits) {
        KitPvPAPI api = KitPvPAPI.getInstance();
        KitManager kitManager = api.getKitManager();

        Arrays.stream(kits).forEach(kitManager::register);
    }
}
