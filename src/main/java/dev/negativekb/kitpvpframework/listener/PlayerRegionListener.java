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
import dev.negativekb.kitpvpframework.core.datastructure.BiMap;
import dev.negativekb.kitpvpframework.core.structure.region.Region;
import dev.negativekb.kitpvpframework.core.structure.region.RegionFlag;
import dev.negativekb.kitpvpframework.core.util.Message;
import dev.negativekb.kitpvpframework.core.util.NMSParticle;
import dev.negativekb.kitpvpframework.core.util.Utils;
import dev.negativekb.kitpvpframework.events.items.region.RegionCreateEvent;
import dev.negativekb.kitpvpframework.events.items.region.RegionEnterEvent;
import dev.negativekb.kitpvpframework.events.items.region.RegionExitEvent;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class PlayerRegionListener implements Listener {

    private final BiMap<UUID, Location, Location> regionWandMap = new BiMap<>();
    private final RegionManager regionManager;

    public PlayerRegionListener(JavaPlugin plugin) {
        regionManager = KitPvPAPI.getInstance().getRegionManager();

        new CoolWandParticleTask().runTaskTimerAsynchronously(plugin, 0, 20);
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

    @EventHandler
    public void onCreation(RegionCreateEvent event) {
        Player player = event.getPlayer();
        regionWandMap.remove(player.getUniqueId());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand == null || itemInHand.getType() != Material.STONE_AXE)
            return;

        ItemMeta itemMeta = itemInHand.getItemMeta();
        boolean isName = itemMeta.getDisplayName().contains("Region Wand");
        boolean hasValidEnchants = itemMeta.getEnchants().entrySet().stream().anyMatch(enchEntry -> (enchEntry.getKey().equals(Enchantment.DURABILITY) &&
                enchEntry.getValue() == 10) || (enchEntry.getKey().equals(Enchantment.DAMAGE_ALL) && enchEntry.getValue() == 10));

        if (isName && hasValidEnchants) {
            Action action = event.getAction();
            event.setCancelled(true);
            UUID uuid = player.getUniqueId();
            Location location = event.getClickedBlock().getLocation();
            if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                addOrReplace(uuid, location, 1);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 2);
            }

            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                addOrReplace(uuid, location, 2);
                player.playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 2);
            }
        }
    }

    private void addOrReplace(UUID uuid, Location location, int position) {
        boolean b = regionWandMap.containsKey(uuid);
        if (position == 1) {
            if (b)
                regionWandMap.replaceFirst(uuid, location);
            else
                regionWandMap.putFirst(uuid, location);
        } else {
            if (b)
                regionWandMap.replaceSecond(uuid, location);
            else
                regionWandMap.putSecond(uuid, location);
        }
    }

    private class CoolWandParticleTask extends BukkitRunnable {

        @Override
        public void run() {
            regionWandMap.stream().filter(wandEntry -> (Utils.getPlayer(wandEntry.getKey()).isPresent()))
                    .filter(wandEntry -> wandEntry.getValue1() != null && wandEntry.getValue2() != null).forEach(wandEntry -> {
                        UUID key = wandEntry.getKey();
                        Location loc1 = wandEntry.getValue1();
                        Location loc2 = wandEntry.getValue2();

                        Optional<Player> playerObject = Utils.getPlayer(key);
                        assert playerObject.isPresent();

                        Player player = playerObject.get();

                        int startX = Math.min(loc1.getBlockX(), loc2.getBlockX());
                        int startY = Math.min(loc1.getBlockY(), loc2.getBlockY());
                        int startZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
                        int endX = Math.max(loc1.getBlockX(), loc2.getBlockX());
                        int endY = Math.max(loc1.getBlockX(), loc2.getBlockY());
                        int endZ = Math.max(loc1.getBlockX(), loc2.getBlockZ());
                        for (double x = startX; x <= endX + 1; x++) {
                            for (double y = startY; y <= endY + 1; y++) {
                                for (double z = startZ; z <= endZ + 1; z++) {
                                    boolean edge = ((int) x == startX || (int) x == endX + 1) &&
                                            ((int) y == startY || (int) y == endY + 1);
                                    if (((int) z == startZ || (int) z == endZ + 1) &&
                                            ((int) y == startY || (int) y == endY + 1)) edge = true;
                                    if (((int) x == startX || (int) x == endX + 1) &&
                                            ((int) z == startZ || (int) z == endZ + 1)) edge = true;

                                    if (edge) {
                                        Location location = new Location(loc1.getWorld(), x, y, z);
                                        new NMSParticle(EnumParticle.BLOCK_DUST, location, true, 0, 0, 0, 0, 1)
                                                .send(player);
                                    }
                                }
                            }
                        }
                    });
        }
    }
}


