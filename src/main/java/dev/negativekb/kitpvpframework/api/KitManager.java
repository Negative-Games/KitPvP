package dev.negativekb.kitpvpframework.api;

import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;

import java.util.ArrayList;
import java.util.Optional;

public interface KitManager {

    void register(Kit kit);

    void unRegister(Kit kit);

    void unRegister(Kits type);

    Optional<Kit> getKit(Kits type);

    ArrayList<Kit> getKits();

    void onDisable();

}
