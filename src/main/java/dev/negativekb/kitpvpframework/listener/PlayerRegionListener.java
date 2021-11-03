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

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.RegionManager;
import dev.negativekb.kitpvpframework.core.structure.region.Region;
import dev.negativekb.kitpvpframework.core.structure.region.RegionFlag;
import dev.negativekb.kitpvpframework.core.util.Message;
import dev.negativekb.kitpvpframework.core.util.Utils;
import dev.negativekb.kitpvpframework.events.items.region.RegionEnterEvent;
import dev.negativekb.kitpvpframework.events.items.region.RegionExitEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

public class PlayerRegionListener implements Listener {

    private final RegionManager regionManager;

    public PlayerRegionListener() {
        regionManager = KitPvPAPI.getInstance().getRegionManager();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        Location from = event.getFrom();

        Optional<Region> regionTo = regionManager.getRegion(to);
        Optional<Region> regionFrom = regionManager.getRegion(from);

        if (regionFrom.isPresent() && !regionTo.isPresent()) {
            // The player has left the region
            RegionExitEvent e = new RegionExitEvent(player, regionFrom.get());
            e.call();

        }

        if (regionFrom.isPresent() && regionTo.isPresent()) {
            // Check to see if it is the same region

            if (!regionTo.get().equals(regionFrom.get())) {
                // They have entered a new Region inside another Region
                RegionEnterEvent e = new RegionEnterEvent(player, regionTo.get());
                e.call();
            }
        }

        if (!regionFrom.isPresent() && regionTo.isPresent()) {
            // The player has entered a Region
            RegionEnterEvent e = new RegionEnterEvent(player, regionTo.get());
            e.call();
        }
    }

    @EventHandler
    public void onEnter(RegionEnterEvent event) {
        Player player = event.getPlayer();
        Region region = event.getRegion();

        region.getFlagString(RegionFlag.ENTER_MESSAGE)
                .ifPresent(msg -> new Message(msg).replace("%player%", player.getName()).send(player));

        region.getFlagString(RegionFlag.ENTER_COMMAND).ifPresent(cmd -> {
            String message = new Message(cmd).replace("%player%", player.getName()).getMessage();
            Utils.executeConsoleCommand(message);
        });
    }

    @EventHandler
    public void onExit(RegionExitEvent event) {
        Player player = event.getPlayer();
        Region region = event.getRegion();

        region.getFlagString(RegionFlag.EXIT_MESSAGE)
                .ifPresent(msg -> new Message(msg).replace("%player%", player.getName()).send(player));

        region.getFlagString(RegionFlag.EXIT_COMMAND).ifPresent(cmd -> {
            String message = new Message(cmd).replace("%player%", player.getName()).getMessage();
            Utils.executeConsoleCommand(message);
        });
    }
}


