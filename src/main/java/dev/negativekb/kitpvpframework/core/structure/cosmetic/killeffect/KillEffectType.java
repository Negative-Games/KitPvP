package dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum KillEffectType {
    BLOOD_EXPLOSION("blood-explosion", "Blood Explosion")
    ;
    private final String id;
    private final String name;

    public static Optional<KillEffectType> getByName(String input) {
        return Arrays.stream(values()).filter(killSoundType -> killSoundType.getName().equalsIgnoreCase(input)
                || killSoundType.getId().equalsIgnoreCase(input)).findFirst();
    }

    public Optional<KillEffect> getKillEffect() {
        return KitPvPAPI.getInstance().getCosmeticManager().getKillEffects()
                .stream().filter(killEffect -> killEffect.getType().equals(this))
                .findFirst();
    }
}
