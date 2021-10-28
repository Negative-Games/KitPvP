package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.CosmeticManager;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;

import java.util.ArrayList;

public class CosmeticManagerImpl implements CosmeticManager {

    private final ArrayList<KillEffect> killEffects = new ArrayList<>();
    private final ArrayList<KillMessage> killMessages = new ArrayList<>();
    private final ArrayList<KillSound> killSounds = new ArrayList<>();

    @Override
    public ArrayList<KillEffect> getKillEffects() {
        return killEffects;
    }

    @Override
    public ArrayList<KillMessage> getKillMessages() {
        return killMessages;
    }

    @Override
    public ArrayList<KillSound> getKillSounds() {
        return killSounds;
    }

    @Override
    public void register(KillEffect killEffect) {
        killEffects.add(killEffect);
    }

    @Override
    public void register(KillMessage killMessage) {
        killMessages.add(killMessage);
    }

    @Override
    public void register(KillSound killSound) {
        killSounds.add(killSound);
    }

    @Override
    public void onDisable() {
        killEffects.forEach(KillEffect::onDisable);
    }
}
