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

package dev.negativekb.kitpvpframework.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Optional;

@UtilityClass
public class UtilPotionEffect {

    private final HashMap<PotionEffectType, String> nameMap;
    static {
        nameMap = new HashMap<>();
        nameMap.put(PotionEffectType.ABSORPTION, "Absorption");
        nameMap.put(PotionEffectType.BLINDNESS, "Blindness");
        nameMap.put(PotionEffectType.CONFUSION, "Nausea");
        nameMap.put(PotionEffectType.DAMAGE_RESISTANCE, "Resistance");
        nameMap.put(PotionEffectType.FAST_DIGGING, "Haste");
        nameMap.put(PotionEffectType.FIRE_RESISTANCE, "Fire Resistance");
        nameMap.put(PotionEffectType.HARM, "Damage");
        nameMap.put(PotionEffectType.HEAL, "Health");
        nameMap.put(PotionEffectType.HEALTH_BOOST, "Health Boost");
        nameMap.put(PotionEffectType.HUNGER, "Hunger");
        nameMap.put(PotionEffectType.INCREASE_DAMAGE, "Strength");
        nameMap.put(PotionEffectType.INVISIBILITY, "Invisibility");
        nameMap.put(PotionEffectType.JUMP, "Jump Boost");
        nameMap.put(PotionEffectType.NIGHT_VISION, "Night Vision");
        nameMap.put(PotionEffectType.POISON, "Poison");
        nameMap.put(PotionEffectType.REGENERATION, "Regeneration");
        nameMap.put(PotionEffectType.SATURATION, "Saturation");
        nameMap.put(PotionEffectType.SLOW, "Slowness");
        nameMap.put(PotionEffectType.SLOW_DIGGING, "Mining Fatigue");
        nameMap.put(PotionEffectType.SPEED, "Speed");
        nameMap.put(PotionEffectType.WATER_BREATHING, "Water Breathing");
        nameMap.put(PotionEffectType.WEAKNESS, "Weakness");
        nameMap.put(PotionEffectType.WITHER, "Wither");
    }

    public Optional<String> getByType(PotionEffectType type) {
        return Optional.ofNullable(nameMap.get(type));
    }
}
