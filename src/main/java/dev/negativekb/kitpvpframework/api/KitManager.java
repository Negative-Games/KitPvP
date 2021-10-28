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
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Kit Manager Module
 *
 * @author Negative
 * @since October 27th, 2021
 * <p>
 * This module is used for managing and registering Kits.
 * <p>
 * You can access this module using {@link KitPvPAPI#getKitManager()}
 */
public interface KitManager extends Disableable {

    /**
     * Register a Kit instance to the cache
     *
     * @param kit Kit instance
     */
    void register(Kit kit);

    /**
     * Unregister a Kit instance from the cache
     *
     * @param kit Kit instance
     */
    void unRegister(Kit kit);

    /**
     * Unregister a Kit instance using {@link Kits} type.
     *
     * @param type Kit Type
     */
    void unRegister(Kits type);

    /**
     * Gets a Kit from the corresponding {@link Kits} type.
     *
     * @param type Kit Type
     * @return If the corresponding {@link Kits} Kit instance is in the cache, return. If not, return empty.
     */
    Optional<Kit> getKit(Kits type);

    /**
     * Returns the Kit instance cache
     */
    ArrayList<Kit> getKits();

}
