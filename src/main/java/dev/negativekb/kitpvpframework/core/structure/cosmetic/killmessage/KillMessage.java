package dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.Cosmetic;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.CosmeticType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public abstract class KillMessage implements Cosmetic {

    @Getter
    private final KillMessageType type;

    public abstract void send(Player victim, Player killer);

    @Override
    public String getID() {
        return type.getId();
    }

    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.KILL_MESSAGE;
    }

    @Override
    public abstract ItemStack getIcon();
}
