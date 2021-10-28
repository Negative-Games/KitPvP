package dev.negativekb.kitpvpframework.core.structure.profile;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffectType;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessageType;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSoundType;
import lombok.Setter;

import java.util.Optional;

public class ProfileCosmeticStatus {

    @Setter
    private KillEffectType killEffect;
    @Setter
    private KillMessageType killMessage;
    @Setter
    private KillSoundType killSound;

    public Optional<KillEffectType> getKillEffect() {
        return Optional.ofNullable(killEffect);
    }

    public Optional<KillMessageType> getKillMessage() {
        return Optional.ofNullable(killMessage);
    }

    public Optional<KillSoundType> getKillSound() {
        return Optional.ofNullable(killSound);
    }
}
