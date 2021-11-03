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

package dev.negativekb.kitpvpframework.core;

import dev.negativekb.kitpvpframework.core.util.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is where ALL messages should be located.
 * <p>
 * A simple entry like the ones below is how you add a message.
 * To access or send a message to a player or broadcast it, it is just
 * doing Locale.MESSAGE.send(player);.
 */
@RequiredArgsConstructor
@Getter
public enum Locale {

    CANNOT_DO_IN_COMBAT(Collections.singletonList("&cYou cannot do this while in Combat!")),

    // Commands
    COMMAND_DISABLED(Collections.singletonList("&cThis command is disabled.")),
    COMMAND_CANNOT_DO_THIS(Collections.singletonList("&cYou cannot do this.")),
    COMMAND_NO_PERMISSION(Collections.singletonList("&cYou do not have permission to use this command.")),

    KILLSTREAK_ENDED(Collections.singletonList("&6%player% &ehas been killed on a killstreak of &c&l%amount%&e.")),
    KILLSTREAK_REACHED(Collections.singletonList("&6&l%player% &ehas reached a killstreak of &c&l%amount%&e!")),

    VIEW_KIT_SYNTAX_ERROR(Collections.singletonList("&cInvalid syntax! Do /viewkit <kit>")),
    VIEW_KIT_INVALID_KIT(Collections.singletonList("&cInvalid kit name `&e%name%&c`!")),

    KIT_CANNOT_AFFORD(Collections.singletonList("&cYou cannot afford this Kit!")),
    KIT_PURCHASED(Collections.singletonList("&aYou have purchased the &e%name% &aKit!")),

    INVALID_PLAYER(Collections.singletonList("&cUnable to find player '&e%name%&c'.")),

    SPAWN_TIMER(Collections.singletonList("&aTeleporting to spawn in &e%time% &asecond(s).")),
    SPAWN_SUCCESS(Collections.singletonList("&aSuccessfully teleported to spawn!")),
    SPAWN_ALREADY_TRANSPORTING(Collections.singletonList("&cYou are already going through a spawn transportation.")),
    SPAWN_CANCELLED(Collections.singletonList("&cYour spawn transportation has been cancelled due to movement or being attacked.")),
    SET_SPAWN_SUCCESS(Collections.singletonList("&aYou have successfully set the server's spawn at your location.")),

    COMBAT_OUT_OF_COMBAT(Collections.singletonList("&aYou are now out of combat.")),
    COMBAT_TIMER(Collections.singletonList("&7You are still in combat for &c%seconds% &7second(s).")),
    COMBAT_ENGAGED(Collections.singletonList("&6&lYOU ARE NOW IN COMBAT! &eCombat Tag has been engaged for &630 seconds&e.")),

    REGION_HELP(Arrays.asList(
            "&7------------- &bRegions &7-------------",
            "&b/region create <name> &7Create a Region",
            "&b/region delete <name> &7Delete a Region",
            "&b/region edit <name> &7Edit a Region",
            "&b/region teleport <name> &7Teleport to a Region",
            "&b/region list &7List all Regions",
            "&7------------------------------------"
    )),

    REGION_CREATED(Collections.singletonList("&7You have created the Region &b%name%&7.")),
    REGION_DELETED(Collections.singletonList("&7You have deleted the Region &b%name%&7.")),
    REGION_TELEPORT(Collections.singletonList("&7You have teleported to the Region &b%name%&b.")),
    REGION_NOT_EXISTS(Collections.singletonList("&7The Region &b%name% &7does not exists.")),
    REGION_ALREADY_EXISTS(Collections.singletonList("&7The Region &b%name% &7already exists")),

    WARP_TIMER(Collections.singletonList("&aTeleporting to &e%warp% &ain &e%time% &asecond(s).")),
    WARP_SUCCESS(Collections.singletonList("&aSuccessfully teleported to &e%warp%&a!")),
    WARP_ALREADY_TRANSPORTING(Collections.singletonList("&cYou are already going through a Warp transportation.")),
    WARP_CANCELLED(Collections.singletonList("&cYour warp transportation has been cancelled due to movement or being attacked.")),
    SET_WARP_SUCCESS(Collections.singletonList("&aYou have successfully created the warp &e%warp%&a.")),
    DELETE_WARP_SUCCESS(Collections.singletonList("&cYou have deleted the warp &e%warp%&a.")),

    WARP_COMMAND_INVALID_ARGS(Collections.singletonList("&cInvalid arguments! /warp <name>")),
    WARP_COMMAND_INVALID_WARP(Collections.singletonList("&cInvalid warp name &e%name%&c.")),

    SETWARP_COMMAND_INVALID_ARGS(Collections.singletonList("&cInvalid arguments! /setwarp <name>")),
    SETWARP_COMMAND_ALREADY_EXISTS(Collections.singletonList("&cThe Warp &e%name% &calready exists!")),

    DELETE_WARP_INVALID_ARGS(Collections.singletonList("&cInvalid arguments! /delwarp <name>")),
    ;


    private final List<String> defaultMessage;
    private Message message;

    public static void init() {
        for (Locale value : values()) {
            String[] strings = value.defaultMessage.toArray(new String[0]);
            value.message = new Message(strings);
        }
    }

    public void send(CommandSender sender) {
        message.send(sender);
    }

    public void send(List<Player> players) {
        message.send(players);
    }

    public void broadcast() {
        message.broadcast();
    }

    public Message replace(Object o1, Object o2) {
        return message.replace(o1, o2);
    }
}
