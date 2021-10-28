package dev.negativekb.kitpvpframework.api;

import lombok.Getter;
import lombok.Setter;

public abstract class KitPvPAPI {

    @Getter
    @Setter
    private static KitPvPAPI instance;

    public abstract ProfileManager getProfileManager();

    public abstract AbilityItemManager getAbilityItemManager();

    public abstract CosmeticManager getCosmeticManager();

    public abstract KitManager getKitManager();

    public abstract void onDisable();
}
