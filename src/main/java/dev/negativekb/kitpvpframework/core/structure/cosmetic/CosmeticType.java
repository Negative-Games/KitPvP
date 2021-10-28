package dev.negativekb.kitpvpframework.core.structure.cosmetic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CosmeticType {
    KILL_EFFECT("kill-effect", "Kill Effect"),
    KILL_MESSAGE("kill-message", "Kill Message"),
    KILL_SOUND("kill-effect", "Kill Sound"),
    ;
    private final String id;
    private final String name;
}
