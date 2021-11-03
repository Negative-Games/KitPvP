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

package dev.negativekb.kitpvpframework.api;

import dev.negativekb.kitpvpframework.api.options.Disableable;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killeffect.KillEffect;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.projectiletrail.ProjectileTrail;

import java.util.ArrayList;

/**
 * Cosmetic Manager Module
 *
 * @author Negative
 * @since October 27th, 2021
 * <p>
 * This module is the manager for cosmetics such as {@link KillEffect},
 * {@link KillMessage} or {@link KillSound}.
 *
 * You can access this module by using {@link KitPvPAPI#getCosmeticManager()}
 */
public interface CosmeticManager extends Disableable {

    /**
     * Returns the cache arraylist of {@link KillEffect}
     */
    ArrayList<KillEffect> getKillEffects();

    /**
     * Returns the cache arraylist of {@link KillMessage}
     */
    ArrayList<KillMessage> getKillMessages();

    /**
     * Returns the cache arraylist of {@link KillSound}
     */
    ArrayList<KillSound> getKillSounds();

    /**
     * Returns the cache arraylist of {@link ProjectileTrail}
     */
    ArrayList<ProjectileTrail> getProjectileTrails();

    /**
     * Register a KillEffect instance to the cache
     *
     * @param killEffect Kill Effect instance
     * @apiNote Use {@link dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry} to register Kill Effects
     */
    void register(KillEffect killEffect);

    /**
     * Register a KillMessage instance to the cache
     *
     * @param killMessage Kill Message instance
     * @apiNote Use {@link dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry} to register Kill Messages
     */
    void register(KillMessage killMessage);

    /**
     * Register a KillSound instance to the cache
     *
     * @param killSound Kill Sound instance
     * @apiNote Use {@link dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry} to register Kill Sounds
     */
    void register(KillSound killSound);

    /**
     * Register a KillSound instance to the cache
     *
     * @param projectileTrail Kill Sound instance
     * @apiNote Use {@link dev.negativekb.kitpvpframework.api.registry.CosmeticRegistry} to register Projectile Trails
     */
    void register(ProjectileTrail projectileTrail);

}
