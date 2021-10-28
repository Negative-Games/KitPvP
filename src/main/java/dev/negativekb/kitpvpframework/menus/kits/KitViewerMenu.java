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
import dev.negativekb.kitpvpframework.core.util.UtilPotionEffect;
import dev.negativekb.kitpvpframework.core.util.Utils;
import dev.negativekb.kitpvpframework.core.util.builder.ItemBuilder;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class KitViewerMenu extends GUI {
    public KitViewerMenu(Kits type) {
        super("&6&lViewing Kit &e&l" + type.getName(), 6);

        Optional<Kit> theKit = type.getKit();
        if (!theKit.isPresent()) {
            System.out.println("There was an error while trying to view a kit without a Kit object paired to the type " + type + ".");
            return;
        }

        List<Integer> fillerSlots = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44, 49, 50, 51, 52);
        ItemStack blackFiller = new ItemBuilder(Material.STAINED_GLASS_PANE).setName("   ").setDurability((short) 15).build();
        fillerSlots.forEach(index -> setItem(index, player -> blackFiller));

        Kit kit = theKit.get();
        ItemStack helmet = kit.getHelmet();
        if (helmet == null || helmet.getType().equals(Material.AIR))
            helmet = new ItemBuilder(Material.BARRIER).setName("&cNo Helmet").build();

        ItemStack finalHelmet = helmet;
        setItem(45, player -> finalHelmet);

        ItemStack chestplate = kit.getChestplate();
        if (chestplate == null || chestplate.getType().equals(Material.AIR))
            chestplate = new ItemBuilder(Material.BARRIER).setName("&cNo Chestplate").build();

        ItemStack finalChestplate = chestplate;
        setItem(46, player -> finalChestplate);

        ItemStack leggings = kit.getLeggings();
        if (leggings == null || leggings.getType().equals(Material.AIR))
            leggings = new ItemBuilder(Material.BARRIER).setName("&cNo Leggings").build();

        ItemStack finalLeggings = leggings;
        setItem(47, player -> finalLeggings);

        ItemStack boots = kit.getBoots();
        if (boots == null || boots.getType().equals(Material.AIR))
            boots = new ItemBuilder(Material.BARRIER).setName("&cNo Boots").build();

        ItemStack finalBoots = boots;
        setItem(48, player -> finalBoots);

        List<PotionEffect> potionEffects = kit.getPermanentPotionEffects();
        ItemBuilder potionEffect = new ItemBuilder(Material.GLASS_BOTTLE);
        if (potionEffects == null || potionEffects.isEmpty())
            potionEffect.setName("&bPotion Effects").setLore("&7&oNo Potion Effects.");
        else {
            potionEffects.stream().filter(effect -> UtilPotionEffect.getByType(effect.getType()).isPresent()).forEach(effect -> {
                Optional<String> byType = UtilPotionEffect.getByType(effect.getType());
                assert byType.isPresent();

                String name = byType.get();
                int amp = (effect.getAmplifier() + 1);

                potionEffect.addLoreLine("&9" + name + " &f" + Utils.decimalFormat(amp));
            });
        }

        setItem(53, player -> potionEffect.build());

        HashMap<Integer, ItemStack> contents = kit.kitContents();
        contents.forEach((index, itemStack) -> setItem(index, player -> itemStack));
    }
}
