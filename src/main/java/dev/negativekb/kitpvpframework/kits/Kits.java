package dev.negativekb.kitpvpframework.kits;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;

import java.util.Optional;

public enum Kits {

    EXAMPLE,

    ;

    public Optional<Kit> getKit() {
        return KitPvPAPI.getInstance().getKitManager().getKit(this);
    }

}
