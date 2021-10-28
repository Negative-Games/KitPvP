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

package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.KitManager;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;

import java.util.ArrayList;
import java.util.Optional;

public class KitManagerImpl implements KitManager {

    private final ArrayList<Kit> kits = new ArrayList<>();

    @Override
    public void register(Kit kit) {
        kits.add(kit);
    }

    @Override
    public void unRegister(Kit kit) {
        kits.remove(kit);
    }

    @Override
    public void unRegister(Kits type) {
        type.getKit().ifPresent(kits::remove);
    }

    @Override
    public Optional<Kit> getKit(Kits type) {
        return kits.stream().filter(kit -> kit.getType().equals(type)).findFirst();
    }

    @Override
    public ArrayList<Kit> getKits() {
        return kits;
    }

    @Override
    public void onDisable() {
        getKits().forEach(Kit::onDisable);
    }
}
