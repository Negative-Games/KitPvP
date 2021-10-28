package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.*;
import org.bukkit.plugin.java.JavaPlugin;

public class APIImpl extends KitPvPAPI {

    private final ProfileManager profileManager;
    private final AbilityItemManager abilityItemManager;
    private final CosmeticManager cosmeticManager;
    private final KitManager kitManager;

    public APIImpl(JavaPlugin plugin) {
        profileManager = new ProfileManagerImpl(plugin);
        abilityItemManager = new AbilityItemManagerImpl();
        cosmeticManager = new CosmeticManagerImpl();
        kitManager = new KitManagerImpl();
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
    public void onDisable() {
        profileManager.onDisable();
        cosmeticManager.onDisable();
        kitManager.onDisable();
    }
}
