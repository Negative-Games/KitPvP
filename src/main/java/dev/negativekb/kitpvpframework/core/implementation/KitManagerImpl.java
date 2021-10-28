package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.KitManager;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;

import java.util.ArrayList;
import java.util.Optional;

public class KitManagerImpl implements KitManager {

    private final ArrayList<Kit> kits = new ArrayList<>();

    @Override
    public void register(Kit kit) {
        kits.add(kit);
    }

    @Override
    public void unRegister(Kit kit) {
        kits.remove(kit);
    }

    @Override
    public void unRegister(Kits type) {
        type.getKit().ifPresent(kit -> kits.remove(kit));
    }

    @Override
    public Optional<Kit> getKit(Kits type) {
        return kits.stream().filter(kit -> kit.getType().equals(type)).findFirst();
    }

    @Override
    public ArrayList<Kit> getKits() {
        return kits;
    }

    @Override
    public void onDisable() {
        getKits().forEach(kit -> {
            kit.onDisable();
        });
    }
}
