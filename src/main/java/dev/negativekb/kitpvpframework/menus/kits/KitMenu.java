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
import dev.negativekb.kitpvpframework.core.util.builder.ItemBuilder;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KitMenu extends GUI {
    public KitMenu(Profile profile, int page) {
        super("&6&lKit Selector", 5);

        List<Integer> fillerSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
        ItemStack blackFiller = new ItemBuilder(Material.STAINED_GLASS_PANE).setName("   ").setDurability((short) 15).build();
        fillerSlots.forEach(index -> setItem(index, player -> blackFiller));

        int limit = 21; // Only 21 slots of free space
        ArrayList<Kits> unlockedKits = profile.getUnlockedKits();
        if (unlockedKits == null) // unlockedKits could be null, which would screw stuff up. So I'll just make a new list if it is null.
            unlockedKits = new ArrayList<>();

        unlockedKits.stream().filter(kits -> kits.getKit().isPresent() && kits.getKit().get().getIcon() != null).skip((long) (page - 1) * limit).limit(limit).forEach(kits -> {
            assert kits.getKit().isPresent();
            Kit kit = kits.getKit().get();

            ItemStack icon = kit.getIcon();
            addItemClickEvent(player -> icon, (player, event) -> {
                // Possibly add a check to see if they need to meet some condition before applying the
                // kit? Like being in a "Spawn Region" or something.

                player.closeInventory();
                kit.applyKit(player);
                profile.setCurrentKit(kit.getType());
            });
        });
    }
}
