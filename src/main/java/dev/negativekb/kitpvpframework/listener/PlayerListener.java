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

import dev.negativekb.kitpvpframework.api.AbilityItemManager;
import dev.negativekb.kitpvpframework.api.CombatManager;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.core.structure.ability.AbilityItem;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffectType;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessageType;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSoundType;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import dev.negativekb.kitpvpframework.core.structure.profile.ProfileCosmeticStatus;
import dev.negativekb.kitpvpframework.core.util.Utils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

import static dev.negativekb.kitpvpframework.core.Locale.KILLSTREAK_ENDED;
import static dev.negativekb.kitpvpframework.core.Locale.KILLSTREAK_REACHED;

public class PlayerListener implements Listener {

    private final ProfileManager profileManager;
    private final AbilityItemManager abilityItemManager;
    private final CombatManager combatManager;

    public PlayerListener() {
        KitPvPAPI api = KitPvPAPI.getInstance();
        profileManager = api.getProfileManager();
        abilityItemManager = api.getAbilityItemManager();
        combatManager = api.getCombatManager();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        event.setDeathMessage(null);
        event.getDrops().clear();

        Player player = event.getEntity();
        Optional<Profile> stats = profileManager.getProfile(player);
        if (!stats.isPresent()) // Somehow this player has no stats…
            return;

        Profile victimProfile = stats.get();
        victimProfile.addDeaths(1);
        victimProfile.getCurrentKit().getKit().ifPresent(kit -> kit.onDeathEvent(event));

        if (victimProfile.getKillStreak() >= 10) {
            KILLSTREAK_ENDED.replace("%player%", player.getName())
                    .replace("%amount%", Utils.decimalFormat(victimProfile.getKillStreak()))
                    .broadcast();
        }

        victimProfile.setKillStreak(0);

        if (player.getKiller() == null)
            return;

        Player killer = player.getKiller();
        Optional<Profile> kStats = profileManager.getProfile(killer);
        if (!kStats.isPresent())
            return; // Somehow the killer has no stats...?

        Profile killerProfile = kStats.get();
        killerProfile.addKills(1);
        killerProfile.addKillStreak(1);

        int ks = killerProfile.getKillStreak();
        if (killerProfile.getBestKillStreak() < ks)
            killerProfile.setBestKillStreak(ks);

        if (ks % 10 == 0) {
            KILLSTREAK_REACHED.replace("%player%", killer.getName())
                    .replace("%amount%", Utils.decimalFormat(ks))
                    .broadcast();
        }

        ProfileCosmeticStatus killerCosmetics = killerProfile.getCosmetics();
        killerCosmetics.getKillEffect().flatMap(KillEffectType::getKillEffect)
                .ifPresent(killEffect -> killEffect.apply(killer, player, player.getLocation()));

        killerCosmetics.getKillMessage().flatMap(KillMessageType::getKillMessage)
                .ifPresent(killMessage -> killMessage.send(player, killer));

        killerCosmetics.getKillSound().flatMap(KillSoundType::getKillSound)
                .ifPresent(killSound -> killSound.send(player, killer));

        if (!killerCosmetics.getKillMessage().isPresent()) {
            KillMessageType.DEFAULT.getKillMessage().ifPresent(killMessage -> killMessage.send(player, killer));
        }

        if (!killerCosmetics.getKillSound().isPresent()) {
            KillSoundType.DEFAULT.getKillSound().ifPresent(killSound -> killSound.send(player, killer));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        ItemStack itemInHand = player.getItemInHand();
        Optional<AbilityItem> item = abilityItemManager.getItem(itemInHand);
        if (!item.isPresent())
            return;

        AbilityItem abilityItem = item.get();

        if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            abilityItem.onLeftClick(event);
            return;
        }

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            abilityItem.onRightClick(event);
            return;
        }

        abilityItem.onInteract(event);
    }

    @EventHandler
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        Optional<AbilityItem> item = abilityItemManager.getItem(itemInHand);
        if (!item.isPresent())
            return;

        AbilityItem abilityItem = item.get();
        abilityItem.onRightClickEntity(event);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Player))
            return;

        if (!(event.getDamager() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();
        combatTag(damager, (Player) entity);

        ItemStack itemInHand = damager.getItemInHand();
        Optional<AbilityItem> item = abilityItemManager.getItem(itemInHand);
        if (!item.isPresent())
            return;

        AbilityItem abilityItem = item.get();
        abilityItem.onPlayerDamage(event);
    }

    private void combatTag(Player victim, Player damager) {
        long duration = (30 * 1000L); // 30 seconds

        combatManager.addOrUpdateCombat(damager.getUniqueId(), duration, true);
        combatManager.addOrUpdateCombat(victim.getUniqueId(), duration, true);
    }
}
