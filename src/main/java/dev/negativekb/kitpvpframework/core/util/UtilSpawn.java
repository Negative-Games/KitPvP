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

package dev.negativekb.kitpvpframework.core.util;

import dev.negativekb.kitpvpframework.KitPvPFramework;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

import static dev.negativekb.kitpvpframework.core.Locale.*;

@UtilityClass
public class UtilSpawn {

    private final JavaPlugin plugin;
    @Getter
    private Location location;
    private final HashMap<UUID, BukkitTask> spawnTasks = new HashMap<>();
    static {
        plugin = KitPvPFramework.getInstance();
        location = loadLocation();
    }

    private Location loadLocation() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("spawn-location");
        String world = section.getString("world");
        double x = section.getDouble("x", 0.5D);
        double y = section.getDouble("y", 100D);
        double z = section.getDouble("z", 0.5D);
        float yaw = (float) section.getDouble("yaw", 0F);
        float pitch = (float) section.getDouble("pitch", 90F);

        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public void saveLocation(Location spawn) {
        location = spawn;

        FileConfiguration config = plugin.getConfig();
        String path = "spawn-location.";
        config.set(path + "world", spawn.getWorld().getName());
        config.set(path + "x", spawn.getX());
        config.set(path + "y", spawn.getY());
        config.set(path + "z", spawn.getZ());
        config.set(path + "yaw", spawn.getYaw());
        config.set(path + "pitch", spawn.getPitch());

        plugin.saveConfig();
    }

    public void teleport(Player player, boolean message) {
        UUID uuid = player.getUniqueId();
        spawnTasks.remove(uuid);

        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
        player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);

        if (message)
            SPAWN_SUCCESS.send(player);
    }

    public boolean commenceTeleport(Player player, int delay) {
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        if (spawnTasks.containsKey(uuid))
            return false;

        final int[] theDelay = {delay};
        spawnTasks.put(uuid, new BukkitRunnable() {

            @Override
            public void run() {
                // Checks if they are online
                if (Bukkit.getPlayer(uuid) == null) {
                    cancel();
                    return;
                }

                // Checks if they moved
                Location loc = player.getLocation();
                if (blockX != loc.getBlockX() || blockY != loc.getBlockY() || blockZ != loc.getBlockZ()) {
                    SPAWN_CANCELLED.send(player);
                    cancel();
                }

                // Checks if the timer is completed
                if (theDelay[0] == 0) {
                    teleport(player, true);
                    cancel();
                    return;
                }

                // Reduces the timer
                SPAWN_TIMER.replace("%time%", theDelay[0]).send(player);
                theDelay[0]--;
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20L * delay));
        return true;
    }
}
