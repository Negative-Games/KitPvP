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

package dev.negativekb.kitpvpframework.core.implementation.registry;

import dev.negativekb.kitpvpframework.api.CosmeticManager;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;

import java.util.Arrays;

public class CosmeticRegistryImpl implements CosmeticRegistry {
    @Override
    public void register(Object... clazzes) {
        CosmeticManager manager = KitPvPAPI.getInstance().getCosmeticManager();

        Arrays.stream(clazzes.clone()).filter(o -> o instanceof KillEffect).forEach(o -> {
            KillEffect effect = (KillEffect) o;
            manager.register(effect);
        });

        Arrays.stream(clazzes.clone()).filter(o -> o instanceof KillMessage).forEach(o -> {
            KillMessage effect = (KillMessage) o;
            manager.register(effect);
        });

        Arrays.stream(clazzes.clone()).filter(o -> o instanceof KillSound).forEach(o -> {
            KillSound effect = (KillSound) o;
            manager.register(effect);
        });
    }
}
