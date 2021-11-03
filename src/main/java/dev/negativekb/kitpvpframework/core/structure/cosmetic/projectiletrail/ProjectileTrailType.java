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

package dev.negativekb.kitpvpframework.core.structure.cosmetic.projectiletrail;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum ProjectileTrailType {
    HEARTS("hearts", "Hearts"),
    ;
    private final String id;
    private final String name;

    public static Optional<ProjectileTrailType> getByName(String input) {
        return Arrays.stream(values()).filter(projectileTrailType -> projectileTrailType.getName().equalsIgnoreCase(input)
                || projectileTrailType.getId().equalsIgnoreCase(input)).findFirst();
    }

    public Optional<ProjectileTrail> getProjectileTrail() {
        return KitPvPAPI.getInstance().getCosmeticManager().getProjectileTrails()
                .stream().filter(projectileTrail -> projectileTrail.getType().equals(this))
                .findFirst();
    }
}
