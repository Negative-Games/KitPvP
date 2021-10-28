package dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum KillSoundType {
    DEFAULT("default", "Default") // Default Kill Sound
    ;
    private final String id;
    private final String name;

    public static Optional<KillSoundType> getByName(String input) {
        return Arrays.stream(values()).filter(killSoundType -> killSoundType.getName().equalsIgnoreCase(input)
                || killSoundType.getId().equalsIgnoreCase(input)).findFirst();
    }

    public Optional<KillSound> getKillSound() {
        return KitPvPAPI.getInstance().getCosmeticManager().getKillSounds()
                .stream().filter(killSound -> killSound.getType().equals(this))
                .findFirst();
    }
}
