package dev.negativekb.kitpvpframework.api;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;

import java.util.ArrayList;

public interface CosmeticManager {

    ArrayList<KillEffect> getKillEffects();

    ArrayList<KillMessage> getKillMessages();

    ArrayList<KillSound> getKillSounds();

    void register(KillEffect killEffect);

    void register(KillMessage killMessage);

    void register(KillSound killSound);

    void onDisable();

}
