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

package dev.negativekb.kitpvpframework.core.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;


/**
 * Custom GUI
 *
 * @author Negative
 * @since May 24th, 2021
 */
public class GUI {

    // Item placement map
    @Getter
    private final Map<Integer, Function<Player, ItemStack>> items;
    // Rows amount (9 * rows)
    @Getter
    private final int rows;
    // Title of the GUI
    @Getter
    private final String title;
    // Inventory Click event map
    @Getter
    private final HashMap<Integer, BiConsumer<Player, InventoryClickEvent>> clickEvents;

    // Map of active inventories
    @Getter
    private final HashMap<Player, Inventory> activeInventories;
    // Are people allowed to take items from the GUI?
    @Getter
    private final boolean allowTakeItems;
    @Getter
    @Setter
    private BiConsumer<Player, InventoryCloseEvent> onClose;
    @Getter
    private BukkitTask autoRefreshTask;

    /**
     * Constructor for GUI
     *
     * @param title Title
     * @param rows  Number of rows
     * @apiNote The title supports color codes automatically!
     * @apiNote By default, allowTakeItems is false.
     */
    public GUI(String title, int rows) {
        this(title, rows, false);
    }

    /**
     * Main constructor for the GUI
     *
     * @param title          Title
     * @param rows           Number of rows
     * @param allowTakeItems Allowed taking items from the menu?
     * @apiNote The title supports color codes automatically!
     */
    public GUI(String title, int rows, boolean allowTakeItems) {
        this.rows = rows;
        this.title = title;
        this.allowTakeItems = allowTakeItems;

        items = new HashMap<>();
        clickEvents = new HashMap<>();
        activeInventories = new HashMap<>();
    }

    /**
     * Open the GUI for the provided player
     *
     * @param player Player
     */
    public void open(Player player) {
        BaseGUI holder = new BaseGUI(this);
        Inventory inv = Bukkit.createInventory(holder, (9 * rows), ChatColor.translateAlternateColorCodes('&', title));

        player.openInventory(inv);
        activeInventories.put(player, inv);

        // Will simply put the items in the corresponding slots
        refresh(player);
    }

    /**
     * Set Item to a certain index in the GUI
     *
     * @param index        Index/Placement of the Item in the GUI
     * @param itemFunction ItemStack Function
     * @apiNote There is no click event linked to this item
     * @apiNote First slot of GUIs are 0
     */
    public void setItem(int index, Function<Player, ItemStack> itemFunction) {
        setItemClickEvent(index, itemFunction, null);
    }

    /**
     * Set Item Click Event to a certain index in the GUI
     *
     * @param index        Index/Placement of the Item in the GUI
     * @param itemFunction ItemStack
     * @param function     Click Event of the Item
     */
    public void setItemClickEvent(int index, Function<Player, ItemStack> itemFunction, BiConsumer<Player, InventoryClickEvent> function) {
        Optional.ofNullable(function).ifPresent(func -> clickEvents.put(index, func));
        items.put(index, itemFunction);
    }

    /**
     * Add Item Click Event to the GUI
     *
     * @param itemFunction ItemStack
     * @param function     Click Event of the Item
     * @apiNote This adds the Item to the next available slot
     */
    public void addItemClickEvent(Function<Player, ItemStack> itemFunction, BiConsumer<Player, InventoryClickEvent> function) {
        int i;
        for (i = 0; i < (9 * rows); i++) {
            if (function != null && !clickEvents.containsKey(i) && !items.containsKey(i))
                break;

            if (!items.containsKey(i))
                break;

        }
        setItemClickEvent(i, itemFunction, function);
    }

    /**
     * Add Item to the GUI
     *
     * @param itemFunction ItemStack
     * @apiNote This adds the Item to the next available slot
     */
    public void addItem(Function<Player, ItemStack> itemFunction) {
        int i;
        for (i = 0; i < (9 * rows); i++) {
            if (!items.containsKey(i))
                break;
        }
        setItem(i, itemFunction);
    }

    /**
     * Refreshes the menu for the provided player
     *
     * @param player Player
     */
    public void refresh(Player player) {
        Optional.ofNullable(activeInventories.get(player))
                .ifPresent(inventory -> items.forEach((slot, item) -> {
                    try {
                        inventory.setItem(slot, item.apply(player));
                    } catch (Exception ignored) {
                    }
                }));
    }

    /**
     * Sets the automatic refresh task interval to the provided time
     * in seconds.
     * <p>
     * Once the menu is closed, the task will be deleted.
     *
     * @param plugin  Plugin instance
     * @param player  Player
     * @param seconds Time in seconds
     */
    public void setAutoRefreshInterval(JavaPlugin plugin, Player player, int seconds) {
        BukkitTask autoRefreshTask = this.autoRefreshTask;
        Optional.ofNullable(autoRefreshTask).ifPresent(BukkitTask::cancel);

        this.autoRefreshTask = new BukkitRunnable() {

            @Override
            public void run() {
                refresh(player);
            }
        }.runTaskTimer(plugin, 0, 20L * seconds);
    }

}