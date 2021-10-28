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

package dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.items;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSoundType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefaultKillSound extends KillSound {

    private final Sound sound;

    public DefaultKillSound() {
        super(KillSoundType.DEFAULT);

        sound = Sound.LEVEL_UP;
    }

    @Override
    public void send(Player victim, Player killer) {
        killer.playSound(victim.getLocation(), sound, 1, 1);
    }

    // No icon needed for this.
    // For non-default Kill Sounds you need an icon for
    // them to show up in the menu!
    @Override
    public ItemStack getIcon() {
        return null;
    }
}
