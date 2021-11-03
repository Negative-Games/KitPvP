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

package dev.negativekb.kitpvpframework.kits;

import dev.negativekb.kitpvpframework.api.options.Disableable;
import dev.negativekb.kitpvpframework.core.util.UtilPlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class Kit implements Disableable {

    @Getter
    private final Kits type;
    @Getter
    private final long cost;
    @Getter
    @Setter
    private boolean automaticallyUnlocked;

    private Consumer<EntityDamageByEntityEvent> damagePlayerEventConsumer;
    private Consumer<PlayerDeathEvent> deathEventConsumer;
    private Consumer<PlayerMoveEvent> moveEventConsumer;
    private Consumer<PlayerInteractEvent> interactEventConsumer;
    private Consumer<PlayerInteractEvent> leftClickEventConsumer;
    private Consumer<PlayerInteractEvent> rightClickEventConsumer;
    private Consumer<PlayerInteractAtEntityEvent> rightClickEntityEventConsumer;

    @Getter
    @Setter
    private List<PotionEffect> permanentPotionEffects;

    public Kit(Kits type, long cost) {
        this.type = type;
        this.cost = cost;
    }


    /**
     * @return - Returns helmet itemstack
     */
    public abstract ItemStack getHelmet();

    /**
     * @return - Returns chestplate itemstack
     */
    public abstract ItemStack getChestplate();

    /**
     * @return - Returns leggings itemstack
     */
    public abstract ItemStack getLeggings();

    /**
     * @return - Returns boots itemstack
     */
    public abstract ItemStack getBoots();

    /**
     * @return - Returns hashmap of kit contents, Integer is the index of the soon-to-be inventory
     */
    public abstract HashMap<Integer, ItemStack> kitContents();

    /**
     * @return - Returns icon of the kit for the Kit Selector
     */
    public abstract ItemStack getIcon();

    public void applyKit(Player player) {
        UtilPlayer.reset(player);

        PlayerInventory inv = player.getInventory();

        if (getHelmet() != null && getHelmet().getType() != Material.AIR)
            inv.setHelmet(getHelmet());

        if (getChestplate() != null && getChestplate().getType() != Material.AIR)
            inv.setChestplate(getChestplate());

        if (getLeggings() != null && getLeggings().getType() != Material.AIR)
            inv.setLeggings(getLeggings());

        if (getBoots() != null && getBoots().getType() != Material.AIR)
            inv.setBoots(getBoots());

        if (!kitContents().isEmpty() || kitContents() != null)
            kitContents().forEach(inv::setItem);

        if (getPermanentPotionEffects() != null)
            getPermanentPotionEffects().forEach(player::addPotionEffect);

        player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 1);
    }


    public void setRightClickEntityEvent(Consumer<PlayerInteractAtEntityEvent> function) {
        this.rightClickEntityEventConsumer = function;
    }

    public void onRightClickEntityEvent(PlayerInteractAtEntityEvent event) {
        if (rightClickEntityEventConsumer == null)
            return;

        rightClickEntityEventConsumer.accept(event);
    }

    public void setLeftClickEvent(Consumer<PlayerInteractEvent> function) {
        this.leftClickEventConsumer = function;
    }

    public void onLeftClickEvent(PlayerInteractEvent event) {
        if (leftClickEventConsumer == null)
            return;

        leftClickEventConsumer.accept(event);
    }

    public void setDamagePlayerEvent(Consumer<EntityDamageByEntityEvent> function) {
        this.damagePlayerEventConsumer = function;
    }

    public void onRightClickEvent(PlayerInteractEvent event) {
        if (rightClickEventConsumer == null)
            return;

        rightClickEventConsumer.accept(event);
    }

    public void setInteractEvent(Consumer<PlayerInteractEvent> function) {
        this.interactEventConsumer = function;
    }

    public void onInteractEvent(PlayerInteractEvent event) {
        if (interactEventConsumer == null)
            return;

        interactEventConsumer.accept(event);
    }


    public void setDeathEvent(Consumer<PlayerDeathEvent> function) {
        this.deathEventConsumer = function;
    }

    public void onDeathEvent(PlayerDeathEvent event) {
        if (deathEventConsumer == null)
            return;

        deathEventConsumer.accept(event);
    }

    public void setMoveEvent(Consumer<PlayerMoveEvent> function) {
        this.moveEventConsumer = function;
    }

    public void onMoveEvent(PlayerMoveEvent event) {
        if (moveEventConsumer == null)
            return;

        moveEventConsumer.accept(event);
    }

    public void setRightClickEvent(Consumer<PlayerInteractEvent> function) {
        this.rightClickEventConsumer = function;
    }

    public void onDamagePlayer(EntityDamageByEntityEvent event) {
        if (damagePlayerEventConsumer == null)
            return;

        damagePlayerEventConsumer.accept(event);
    }

    @Override
    public abstract void onDisable();
}
