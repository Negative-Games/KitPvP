package dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.Cosmetic;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.CosmeticType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public abstract class KillEffect implements Cosmetic {

    @Getter
    private final KillEffectType type;

    public abstract void apply(Player killer, Player victim, Location location);

    public abstract void onDisable();

    @Override
    public String getID() {
        return type.getId();
    }

    @Override
    public CosmeticType getCosmeticType() {
        return CosmeticType.KILL_EFFECT;
    }

    @Override
    public abstract ItemStack getIcon();
}
