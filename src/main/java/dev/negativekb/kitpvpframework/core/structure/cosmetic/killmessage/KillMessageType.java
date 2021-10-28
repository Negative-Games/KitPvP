package dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum KillMessageType {
    DEFAULT("default", "Default"), // Default Kill Message

    ;
    private final String id;
    private final String name;

    public static Optional<KillMessageType> getByName(String input) {
        return Arrays.stream(values()).filter(killSoundType -> killSoundType.getName().equalsIgnoreCase(input)
                || killSoundType.getId().equalsIgnoreCase(input)).findFirst();
    }

    public Optional<KillMessage> getKillMessage() {
        return KitPvPAPI.getInstance().getCosmeticManager().getKillMessages()
                .stream().filter(killMessage -> killMessage.getType().equals(this))
                .findFirst();
    }
}
