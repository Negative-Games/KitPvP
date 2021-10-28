package dev.negativekb.kitpvpframework.listener;

import dev.negativekb.kitpvpframework.core.gui.BaseGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
import java.util.function.BiConsumer;

public class GUIListener implements Listener {

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BaseGUI))
            return;

        BaseGUI base = (BaseGUI) event.getInventory().getHolder();

        if (!base.getGui().isAllowTakeItems())
            event.setCancelled(true);

        if (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER)
            return;

        HashMap<Integer, BiConsumer<Player, InventoryClickEvent>> clickEvents = base.getGui().getClickEvents();
        int slot = event.getSlot();
        if (clickEvents.containsKey(slot))
            clickEvents.get(slot).accept((Player) event.getWhoClicked(), event);

    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (!(holder instanceof BaseGUI))
            return;

        BaseGUI base = (BaseGUI) event.getInventory().getHolder();
        base.onClose((Player) event.getPlayer(), event);
    }
}
