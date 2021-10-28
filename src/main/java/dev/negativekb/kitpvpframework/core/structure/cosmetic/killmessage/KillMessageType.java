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

package dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum KillMessageType {
    DEFAULT("default", "Default"), // Default Kill Message

    ;
    private final String id;
    private final String name;

    public static Optional<KillMessageType> getByName(String input) {
        return Arrays.stream(values()).filter(killSoundType -> killSoundType.getName().equalsIgnoreCase(input)
                || killSoundType.getId().equalsIgnoreCase(input)).findFirst();
    }

    public Optional<KillMessage> getKillMessage() {
        return KitPvPAPI.getInstance().getCosmeticManager().getKillMessages()
                .stream().filter(killMessage -> killMessage.getType().equals(this))
                .findFirst();
    }
}
