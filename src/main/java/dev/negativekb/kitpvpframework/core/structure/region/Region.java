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

package dev.negativekb.kitpvpframework.core.structure.region;

import dev.negativekb.kitpvpframework.core.structure.region.exceptions.InvalidFlagDataTypeException;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

@Data
public class Region {

    private String name;
    private HashMap<RegionFlag, Object> flags;
    private int priority;
    private DataPoint position1;
    private DataPoint position2;

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        Location loc1 = parsePosition1();
        Location loc2 = parsePosition2();

        int topBlockX = (Math.max(loc1.getBlockX(), loc2.getBlockX()));
        int bottomBlockX = (Math.min(loc1.getBlockX(), loc2.getBlockX()));

        int topBlockY = (Math.max(loc1.getBlockY(), loc2.getBlockY()));
        int bottomBlockY = (Math.min(loc1.getBlockY(), loc2.getBlockY()));

        int topBlockZ = (Math.max(loc1.getBlockZ(), loc2.getBlockZ()));
        int bottomBlockZ = (Math.min(loc1.getBlockZ(), loc2.getBlockZ()));

        for (int x = bottomBlockX; x <= topBlockX; x++) {
            for (int z = bottomBlockZ; z <= topBlockZ; z++) {
                for (int y = bottomBlockY; y <= topBlockY; y++) {
                    Block block = Objects.requireNonNull(loc1.getWorld()).getBlockAt(x, y, z);
                    if (block.getType().equals(Material.AIR))
                        continue;

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> isInside(player.getLocation()))
                .forEach(players::add);

        return players;
    }


    public Location parsePosition1() {
        return position1.toLocation();
    }

    public Location parsePosition2() {
        return position2.toLocation();
    }

    public boolean isInside(Location location) {
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        Location l1 = parsePosition1();
        Location l2 = parsePosition2();
        int x1 = Math.min(l1.getBlockX(), l2.getBlockX());
        int y1 = Math.min(l1.getBlockY(), l2.getBlockY());
        int z1 = Math.min(l1.getBlockZ(), l2.getBlockZ());
        int x2 = Math.max(l1.getBlockX(), l2.getBlockX());
        int y2 = Math.max(l1.getBlockY(), l2.getBlockY());
        int z2 = Math.max(l1.getBlockZ(), l2.getBlockZ());

        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public void addFlag(RegionFlag flag, Object value) {
        if (flags == null)
            flags = new HashMap<>();
        boolean contains = flags.containsKey(flag);
        if (contains)
            flags.replace(flag, value);
        else
            flags.put(flag, value);

        setFlags(flags);
    }

    public Object getFlagObject(RegionFlag flag) {
        if (flags == null)
            return flag.getDefaultValue();

        return flags.getOrDefault(flag, flag.getDefaultValue());
    }

    public int getFlagInt(RegionFlag flag) {
        if (!flag.getDataType().equals(RegionFlagDataType.INT))
            throw new InvalidFlagDataTypeException("This flag type is not an Integer.");

        Object flagObject = getFlagObject(flag);
        return Integer.parseInt(String.valueOf(flagObject));
    }

    public double getFlagDouble(RegionFlag flag) {
        if (!flag.getDataType().equals(RegionFlagDataType.DOUBLE))
            throw new InvalidFlagDataTypeException("This flag type is not a Double.");

        Object flagObject = getFlagObject(flag);
        return Double.parseDouble(String.valueOf(flagObject));
    }

    public boolean getFlagBoolean(RegionFlag flag) {
        if (!flag.getDataType().equals(RegionFlagDataType.BOOLEAN))
            throw new InvalidFlagDataTypeException("This flag type is not a Boolean.");

        Object flagObject = getFlagObject(flag);
        return Boolean.parseBoolean(String.valueOf(flagObject));
    }

    public Optional<String> getFlagString(RegionFlag flag) {
        if (!flag.getDataType().equals(RegionFlagDataType.STRING))
            throw new InvalidFlagDataTypeException("This flag type is not a String.");

        Object flagObject = getFlagObject(flag);
        return Optional.ofNullable(String.valueOf(flagObject));
    }
}
