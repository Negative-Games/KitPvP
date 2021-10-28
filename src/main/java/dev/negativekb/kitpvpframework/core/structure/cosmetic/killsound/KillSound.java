package dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.Cosmetic;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.CosmeticType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public abstract class KillSound implements Cosmetic {

    @Getter
    private final KillSoundType type;

    public abstract void send(Player victim, Player killer);

    @Override
    public String getID() {
        return type.getId();
    }

    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.KILL_SOUND;
    }

    @Override
    public abstract ItemStack getIcon();
}
