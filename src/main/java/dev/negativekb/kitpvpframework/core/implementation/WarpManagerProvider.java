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

package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.WarpManager;
import dev.negativekb.kitpvpframework.core.cache.ObjectCache;
import dev.negativekb.kitpvpframework.core.structure.warp.Warp;
import dev.negativekb.kitpvpframework.core.structure.warp.WarpLocation;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static dev.negativekb.kitpvpframework.core.Locale.*;

public class WarpManagerProvider extends ObjectCache<Warp> implements WarpManager {

    private ArrayList<Warp> warps;
    private final HashMap<UUID, BukkitTask> warpTasks = new HashMap<>();
    private final JavaPlugin plugin;

    @SneakyThrows
    public WarpManagerProvider(JavaPlugin plugin) {
        super(plugin.getDataFolder().getPath() + "/data/warps.json", Warp[].class);
        this.plugin = plugin;

        warps = load();

        new WarpSaveTask().runTaskTimerAsynchronously(plugin, 0, 20 * 120);
    }

    @Override
    public void teleport(Warp warp, Player player, boolean message) {
        teleport(warp, player, null, message);
    }

    @Override
    public boolean teleport(Warp warp, Player player, int delay) {
        return teleport(warp, player, delay, null);
    }

    @Override
    public boolean teleport(Warp warp, Player player, int delay, Runnable afterTeleport) {
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        if (warpTasks.containsKey(uuid))
            return false;

        final int[] theDelay = {delay};
        warpTasks.put(uuid, new BukkitRunnable() {

            @Override
            public void run() {
                // Checks if they are online
                if (!Optional.ofNullable(Bukkit.getPlayer(uuid)).isPresent()) {
                    cancel();
                    return;
                }

                // Checks if they moved
                Location loc = player.getLocation();
                if (blockX != loc.getBlockX() || blockY != loc.getBlockY() || blockZ != loc.getBlockZ()) {
                    WARP_CANCELLED.send(player);
                    cancel();
                }

                // Checks if the timer is completed
                if (theDelay[0] == 0) {
                    teleport(warp, player, afterTeleport, true);
                    cancel();
                    return;
                }

                // Reduces the timer
                WARP_TIMER.replace("%warp%", warp.getName())
                        .replace("%time%", theDelay[0]).send(player);
                theDelay[0]--;
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20L));
        return true;
    }

    @Override
    public void teleport(Warp warp, Player player, Runnable afterTeleport, boolean message) {
        warpTasks.remove(player.getUniqueId());
        warp.teleport(player, afterTeleport);

        if (message)
            WARP_SUCCESS.replace("%warp%", warp.getName()).send(player);
    }


    @Override
    public void createWarp(String name, Location location) {
        if (getWarp(name).isPresent())
            return;

        Warp warp = new Warp();
        warp.setName(name);
        warp.setLocation(new WarpLocation(location.getWorld().getName(), location.getX(),
                location.getY(), location.getZ(), location.getYaw(), location.getPitch()));
    }

    @Override
    public void deleteWarp(Warp warp) {
        warps.remove(warp);
    }

    @Override
    public Optional<Warp> getWarp(String name) {
        return warps.stream().filter(warp -> warp.getName().equalsIgnoreCase(name)).findAny();
    }

    @Override
    public ArrayList<Warp> getWarps() {
        return warps;
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        save(warps);
    }

    private class WarpSaveTask extends BukkitRunnable {

        @SneakyThrows
        @Override
        public void run() {
            save(warps);
            warps = load();
        }
    }

}
