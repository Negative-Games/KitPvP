package dev.negativekb.kitpvpframework.core.structure.region;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

@Data
public class DataPoint {

    private String world;
    private double x;
    private double y;
    private double z;

    public DataPoint(Location location) {
        this.world = Objects.requireNonNull(location.getWorld()).getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }
}
