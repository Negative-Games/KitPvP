/*
 * MIT License
 *
 * Copyright (c) 2021 Negative
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
