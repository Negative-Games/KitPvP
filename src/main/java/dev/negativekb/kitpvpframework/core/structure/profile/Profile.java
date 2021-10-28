package dev.negativekb.kitpvpframework.core.structure.profile;

import dev.negativekb.kitpvpframework.kits.Kits;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This is the Profile class where all user-based
 * data will be stored
 */
@Data
public class Profile {

    private final UUID uniqueID;
    private int kills;
    private int deaths;
    private int killStreak;
    private int bestKillStreak;
    private int assists;
    private long coins;
    private ProfileCosmeticStatus cosmetics;
    private Kits currentKit;
    private ArrayList<Kits> unlockedKits;

    public Player getPlayer() {
        return Bukkit.getPlayer(uniqueID);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uniqueID);
    }

    public void addCoins(long amount) {
        setCoins(getCoins() + amount);
    }

    public void removeCoins(long amount) {
        setCoins(getCoins() - amount);
        if (getCoins() < 0)
            setCoins(0);
    }

    public boolean transactCoins(long amount) {
        boolean hasEnough = getCoins() >= amount;
        if (hasEnough) {
            removeCoins(amount);
            return true;
        } else {
            return false;
        }
    }

    public void addDeaths(int amount) {
        setDeaths(getDeaths() + amount);
    }

    public void addKills(int amount) {
        setKills(getKills() + amount);
    }

    public void addKillStreak(int amount) {
        setKillStreak(getKillStreak() + amount);
    }

    public void addUnlockedKit(Kits type) {
        if (unlockedKits == null)
            unlockedKits = new ArrayList<>();

        if (unlockedKits.contains(type))
            return;

        unlockedKits.add(type);
    }

    public void removeUnlockedKit(Kits type) {
        if (unlockedKits == null)
            return;

        unlockedKits.remove(type);
        if (unlockedKits.isEmpty())
            setUnlockedKits(null);
    }
}
