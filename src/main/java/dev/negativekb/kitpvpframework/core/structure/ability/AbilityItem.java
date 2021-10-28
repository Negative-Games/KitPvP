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

package dev.negativekb.kitpvpframework.core.structure.ability;

import dev.negativekb.kitpvpframework.abilityitems.AbilityItemType;
import lombok.Getter;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class AbilityItem {

    @Getter
    private final AbilityItemType type;
    private Consumer<PlayerInteractEvent> rightClickEventConsumer;
    private Consumer<PlayerInteractEvent> leftClickEventConsumer;
    private Consumer<PlayerInteractEvent> interactEventConsumer;
    private Consumer<PlayerInteractAtEntityEvent> rightClickEntityEventConsumer;
    private Consumer<EntityDamageByEntityEvent> damagePlayerEventConsumer;

    protected AbilityItem(AbilityItemType type) {
        this.type = type;
    }

    public abstract ItemStack getItem();

    public void setInteractEvent(Consumer<PlayerInteractEvent> function) {
        this.interactEventConsumer = function;
    }

    public void setDamagePlayerEvent(Consumer<EntityDamageByEntityEvent> function) {
        this.damagePlayerEventConsumer = function;
    }

    public void setLeftClickEvent(Consumer<PlayerInteractEvent> function) {
        this.leftClickEventConsumer = function;
    }

    public void setRightClickEntityEvent(Consumer<PlayerInteractAtEntityEvent> function) {
        this.rightClickEntityEventConsumer = function;
    }

    public void setRightClickEvent(Consumer<PlayerInteractEvent> function) {
        this.rightClickEventConsumer = function;
    }

    public void onRightClick(PlayerInteractEvent event) {
        if (rightClickEventConsumer == null)
            return;

        rightClickEventConsumer.accept(event);
    }

    public void onLeftClick(PlayerInteractEvent event) {
        if (leftClickEventConsumer == null)
            return;

        leftClickEventConsumer.accept(event);
    }

    public void onRightClickEntity(PlayerInteractAtEntityEvent event) {
        if (rightClickEntityEventConsumer == null)
            return;

        rightClickEntityEventConsumer.accept(event);
    }

    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (damagePlayerEventConsumer == null)
            return;

        damagePlayerEventConsumer.accept(event);
    }

    public void onInteract(PlayerInteractEvent event) {
        if (interactEventConsumer == null)
            return;

        interactEventConsumer.accept(event);
    }

    public abstract void onDisable();

}
