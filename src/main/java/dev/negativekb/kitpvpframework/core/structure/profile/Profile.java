package dev.negativekb.kitpvpframework.core.structure.profile;

import dev.negativekb.kitpvpframework.kits.Kits;
import lombok.Data;

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

    public void addCoins(long amount) {
        setCoins(getCoins() + amount);
    }

    public void removeCoins(long amount) {
        setCoins(getCoins() - amount);
        if (getCoins() >= 0)
            setCoins(0);
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
}
