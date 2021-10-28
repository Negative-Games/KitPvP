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

package dev.negativekb.kitpvpframework.kits.items;

import dev.negativekb.kitpvpframework.core.util.builder.ItemBuilder;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ExampleKit extends Kit {
    public ExampleKit() {
        super(Kits.EXAMPLE, 0);

        setAutomaticallyUnlocked(true);
        // Will automatically unlock this kit upon login if you do not have this kit!


        // This kit has no special abilities.
    }

    @Override
    public ItemStack getHelmet() {
        // Will create an unbreakable iron helmet
        return new ItemBuilder(Material.IRON_HELMET).setUnbreakable().build();
    }

    @Override
    public ItemStack getChestplate() {
        // Will create an unbreakable iron chestplate
        return new ItemBuilder(Material.IRON_CHESTPLATE).setUnbreakable().build();
    }

    @Override
    public ItemStack getLeggings() {
        // Will create an unbreakable iron legging
        return new ItemBuilder(Material.IRON_LEGGINGS).setUnbreakable().build();
    }

    @Override
    public ItemStack getBoots() {
        // Will create an unbreakable iron boots
        return new ItemBuilder(Material.IRON_BOOTS).setUnbreakable().build();
    }

    @Override
    public HashMap<Integer, ItemStack> kitContents() {
        HashMap<Integer, ItemStack> items = new HashMap<>();

        ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1)
                .setUnbreakable().build();

        items.put(0, sword);
        return items;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemBuilder(Material.IRON_CHESTPLATE).setName("&aExample Kit").build();
    }

    @Override
    public void onDisable() {

    }
}
