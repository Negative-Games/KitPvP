package dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.items;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffectType;
import dev.negativekb.kitpvpframework.core.util.builder.ItemBuilder;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BloodExplosionKillEffect extends KillEffect {
    public BloodExplosionKillEffect() {
        super(KillEffectType.BLOOD_EXPLOSION);
    }

    @Override
    public void apply(Player killer, Player victim, Location location) {
        World world = victim.getWorld();
        world.playEffect(location, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        world.playEffect(location.clone().subtract(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        world.playEffect(location.clone().add(0, 1, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.REDSTONE)
                .setName("&4&lBlood Explosion").build();
    }
}
