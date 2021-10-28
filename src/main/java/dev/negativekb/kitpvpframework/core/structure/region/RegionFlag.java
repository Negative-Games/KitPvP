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

package dev.negativekb.kitpvpframework.core.structure.region;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum RegionFlag {

    NO_PVP("no-pvp", "No PvP", RegionFlagDataType.BOOLEAN, false),
    BLOCK_BREAK("block-break", "Block Break", RegionFlagDataType.BOOLEAN, true),
    BLOCK_PLACE("block-place", "Block Place", RegionFlagDataType.BOOLEAN, true),
    OPEN_CONTAINERS("open-containers", "Open Containers", RegionFlagDataType.BOOLEAN, true),
    OPEN_DOORS("open-doors", "Open Doors", RegionFlagDataType.BOOLEAN, true),
    EXPLOSIONS("explosions", "Explosions", RegionFlagDataType.BOOLEAN, true),
    ENTER_MESSAGE("enter-message", "Enter Message", RegionFlagDataType.STRING, null),
    EXIT_MESSAGE("exit-message", "Exit Message", RegionFlagDataType.STRING, null),
    ENTER_COMMAND("enter-command", "Enter Command", RegionFlagDataType.STRING, null),
    EXIT_COMMAND("exit-command", "Exit Command", RegionFlagDataType.STRING, null),
    ;

    private final String id;
    private final String name;
    private final RegionFlagDataType dataType;
    private final Object defaultValue;
    private final List<String> description;

    RegionFlag(String id, String name, RegionFlagDataType dataType, Object defaultValue) {
        this(id, name, dataType, defaultValue, null);
    }
}
