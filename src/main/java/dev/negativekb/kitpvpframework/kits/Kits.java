package dev.negativekb.kitpvpframework.kits;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum Kits {

    EXAMPLE("example", "Example"),

    ;
    private final String id;
    private final String name;

    public static Optional<Kits> getByName(String input) {
        return Arrays.stream(values()).filter(kits -> kits.getId().equalsIgnoreCase(input) ||
                kits.getName().equalsIgnoreCase(input)).findFirst();
    }

    public Optional<Kit> getKit() {
        return KitPvPAPI.getInstance().getKitManager().getKit(this);
    }

}
