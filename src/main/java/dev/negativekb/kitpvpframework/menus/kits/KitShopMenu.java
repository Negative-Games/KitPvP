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

package dev.negativekb.kitpvpframework.menus.kits;

import dev.negativekb.kitpvpframework.core.gui.GUI;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import dev.negativekb.kitpvpframework.core.util.Utils;
import dev.negativekb.kitpvpframework.core.util.builder.ItemBuilder;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static dev.negativekb.kitpvpframework.core.Locale.*;

public class KitShopMenu extends GUI {
    public KitShopMenu(Profile profile, int page) {
        super("&6&lKit Shop", 5);

        List<Integer> fillerSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
        ItemStack blackFiller = new ItemBuilder(Material.STAINED_GLASS_PANE).setName("   ").setDurability((short) 15).build();
        fillerSlots.forEach(index -> setItem(index, player -> blackFiller));

        int limit = 21;
        Kits[] values = Kits.values();
        Arrays.stream(values).filter(kits -> kits.getKit().isPresent() && !profile.getUnlockedKits().contains(kits)).skip((long) (page - 1) * limit).forEach(kits -> {
            Optional<Kit> theKit = kits.getKit();
            assert theKit.isPresent();

            Kit kit = theKit.get();
            ItemStack icon = kit.getIcon();
            ItemMeta itemMeta = icon.getItemMeta();
            List<String> lore = itemMeta.getLore();
            lore.add(" ");
            lore.add("&d&lPurchase Details");
            lore.add("&bCost: &f" + Utils.decimalFormat(kit.getCost()));
            lore.add(" ");
            lore.add("&d&lActions");
            lore.add("&bLeft-Click &fto purchase");
            lore.add("&bRight-Click &fto view");
            itemMeta.setLore(Utils.color(lore));
            icon.setItemMeta(itemMeta);

            addItemClickEvent(player -> icon, (player, event) -> {
                if (event.isLeftClick()) {
                    if (profile.transactCoins(kit.getCost())) {
                        player.closeInventory();
                        KIT_PURCHASED.replace("%name%", kits.getName()).send(player);
                        profile.addUnlockedKit(kits);

                        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 3);
                    } else
                        KIT_CANNOT_AFFORD.send(player);
                    return;
                }

                if (event.isRightClick())
                    new KitViewerMenu(kits).open(player);

            });
        });

        if (values.length > page * limit) {
            ItemStack item = new ItemBuilder(Material.ARROW).setName("&a&lNext Page").build();
            setItemClickEvent(26, player -> item, (player, event) -> new KitShopMenu(profile, page + 1).open(player));
        }

        if (page > 1) {
            ItemStack item = new ItemBuilder(Material.ARROW).setName("&c&lPrevious Page").build();
            setItemClickEvent(18, player -> item, (player, event) -> new KitShopMenu(profile, page - 1).open(player));
        }
    }
}
