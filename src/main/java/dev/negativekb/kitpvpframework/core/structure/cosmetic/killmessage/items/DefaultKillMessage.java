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

package dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.items;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessageType;
import dev.negativekb.kitpvpframework.core.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefaultKillMessage extends KillMessage {

    private final Message killerMessage;
    private final Message victimMessage;

    public DefaultKillMessage() {
        super(KillMessageType.DEFAULT);

        killerMessage = new Message("&7You have killed &c%victim&7!%");
        victimMessage = new Message("&7You have been killed by &c%killer%");
    }

    @Override
    public void send(Player victim, Player killer) {
        victimMessage.replace("%killer%", killer.getName()).send(victim);
        killerMessage.replace("%victim%", victim.getName()).send(killer);
    }

    // No icon needed for this.
    // For non-default Kill Messages you need an icon for
    // them to show up in the menu!
    @Override
    public ItemStack getIcon() {
        return null;
    }
}
