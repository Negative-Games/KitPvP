package dev.negativekb.kitpvpframework.core.structure.ability;

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

}
