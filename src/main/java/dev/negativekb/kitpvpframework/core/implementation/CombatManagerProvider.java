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

import dev.negativekb.kitpvpframework.api.CombatManager;
import dev.negativekb.kitpvpframework.core.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static dev.negativekb.kitpvpframework.core.Locale.*;

public class CombatManagerProvider implements CombatManager {

    private final HashMap<UUID, Long> combatMap = new HashMap<>();
    public CombatManagerProvider(JavaPlugin plugin) {
        new CombatTagTimerTask().runTaskTimerAsynchronously(plugin, 0, 20);
    }

    @Override
    public boolean isInCombat(UUID uuid) {
        return combatMap.containsKey(uuid);
    }

    @Override
    public boolean isInCombat(Player player) {
        return isInCombat(player.getUniqueId());
    }

    @Override
    public void addOrUpdateCombat(UUID uuid, long duration, boolean message) {
        long current = System.currentTimeMillis();
        if (isInCombat(uuid))
            combatMap.replace(uuid, (current + duration));
        else {
            combatMap.put(uuid, (current + duration));

            if (message)
                Utils.getPlayer(uuid).ifPresent(COMBAT_ENGAGED::send);
        }
    }

    @Override
    public void removeFromCombat(UUID uuid, boolean message) {
        combatMap.remove(uuid);

        if (message)
            Utils.getPlayer(uuid).ifPresent(COMBAT_OUT_OF_COMBAT::send);
    }

    private class CombatTagTimerTask extends BukkitRunnable {

        @Override
        public void run() {
            List<Map.Entry<UUID, Long>> toRemove = combatMap.entrySet().stream()
                    .filter(entry -> System.currentTimeMillis() >= entry.getValue())
                    .collect(Collectors.toList());

            toRemove.forEach(entry -> removeFromCombat(entry.getKey(), true));
        }
    }
}
