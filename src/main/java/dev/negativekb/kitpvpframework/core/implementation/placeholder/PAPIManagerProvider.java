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

package dev.negativekb.kitpvpframework.core.implementation.placeholder;

import dev.negativekb.kitpvpframework.api.placeholder.PAPIManager;
import dev.negativekb.kitpvpframework.api.placeholder.PAPIPlaceholder;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PAPIManagerProvider implements PAPIManager {

    private final ArrayList<PAPIPlaceholder> placeholders = new ArrayList<>();

    @Override
    public String request(Player player, String[] paths) {
        PAPIPlaceholder papiPlaceholder = this.placeholders.stream().filter(placeholder -> {
            String identifier = placeholder.getIdentifier();
            int i = placeholder.triggerOnArgument();

            return (paths[i].equalsIgnoreCase(identifier));
        }).findFirst().orElse(null);

        return (papiPlaceholder == null ? null : papiPlaceholder.onRequest(player, paths));
    }

    @Override
    public void addPlaceholder(PAPIPlaceholder placeholder) {
        this.placeholders.add(placeholder);
    }

}
