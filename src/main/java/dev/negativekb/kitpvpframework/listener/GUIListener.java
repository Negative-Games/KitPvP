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

package dev.negativekb.kitpvpframework.listener;

import dev.negativekb.kitpvpframework.core.gui.BaseGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class GUIListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BaseGUI))
            return;

        BaseGUI base = (BaseGUI) event.getInventory().getHolder();

        if (!base.getGui().isAllowTakeItems())
            event.setCancelled(true);

        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER)
            return;

        HashMap<Integer, BiConsumer<Player, InventoryClickEvent>> clickEvents = base.getGui().getClickEvents();
        int slot = event.getSlot();
        if (clickEvents.containsKey(slot))
            clickEvents.get(slot).accept((Player) event.getWhoClicked(), event);

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BaseGUI))
            return;

        BaseGUI base = (BaseGUI) event.getInventory().getHolder();
        base.onClose((Player) event.getPlayer(), event);
    }
}
