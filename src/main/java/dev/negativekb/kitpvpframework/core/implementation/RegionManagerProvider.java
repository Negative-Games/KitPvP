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

import dev.negativekb.kitpvpframework.api.RegionManager;
import dev.negativekb.kitpvpframework.core.cache.ObjectCache;
import dev.negativekb.kitpvpframework.core.structure.region.DataPoint;
import dev.negativekb.kitpvpframework.core.structure.region.Region;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RegionManagerProvider extends ObjectCache<Region> implements RegionManager {

    private ArrayList<Region> regions;
    @SneakyThrows
    public RegionManagerProvider(JavaPlugin plugin) {
        super(plugin.getDataFolder().getPath() + "/data/regions.json", Region[].class);

        regions = load();

        new RegionSaveTask().runTaskTimerAsynchronously(plugin, 0, 20 * 120);
    }

    @Override
    public void createRegion(String name, Location position1, Location position2) {
        if (getRegion(name).isPresent())
            return;

        Region region = new Region();
        region.setName(name);
        region.setPosition1(new DataPoint(position1));
        region.setPosition2(new DataPoint(position2));
        region.setPriority(1);
        regions.add(region);
    }

    @Override
    public void deleteRegion(Region region) {
        regions.remove(region);
    }

    @Override
    public Optional<Region> getRegion(Location location) {
        List<Region> sortedRegions = getRegions(location);
        if (sortedRegions.isEmpty())
            return Optional.empty();
        else
            return Optional.of(sortedRegions.get(0));
    }

    @Override
    public Optional<Region> getRegion(String name) {
        return regions.stream().filter(region -> region.getName().equalsIgnoreCase(name)).findAny();
    }

    @Override
    public List<Region> getRegions(Location location) {
        return regions.stream().filter(region -> region.isInside(location))
                .sorted(Comparator.comparingInt(Region::getPriority).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Region> getRegions(Player player) {
        return getRegions(player.getLocation());
    }

    @Override
    public List<Region> getAllRegions() {
        return regions;
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        save(regions);
    }

    private class RegionSaveTask extends BukkitRunnable {

        @SneakyThrows
        @Override
        public void run() {
            save(regions);
            regions = load();
        }
    }
}
