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
