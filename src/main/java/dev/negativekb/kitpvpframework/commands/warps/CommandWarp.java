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

package dev.negativekb.kitpvpframework.commands.warps;

import dev.negativekb.kitpvpframework.api.CombatManager;
import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.RegionManager;
import dev.negativekb.kitpvpframework.api.WarpManager;
import dev.negativekb.kitpvpframework.core.command.Command;
import dev.negativekb.kitpvpframework.core.command.CommandInfo;
import dev.negativekb.kitpvpframework.core.structure.region.Region;
import dev.negativekb.kitpvpframework.core.structure.warp.Warp;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.negativekb.kitpvpframework.core.Locale.*;

@CommandInfo(name = "warp", aliases = {"warps"}, playerOnly = true)
public class CommandWarp extends Command {

    private final WarpManager warpManager;
    private final RegionManager regionManager;
    private final CombatManager combatManager;

    public CommandWarp() {
        KitPvPAPI api = KitPvPAPI.getInstance();
        warpManager = api.getWarpManager();
        regionManager = api.getRegionManager();
        combatManager = api.getCombatManager();

        setTabComplete((sender, args) -> {
            // Send all warp names in tab-complete
            if (args.length == 1) {
                String lastWord = args[args.length - 1];
                List<String> warpNames = new ArrayList<>();
                warpManager.getWarps().stream()
                        // Checks if the warp contains or equals the last word
                        .filter(warp -> StringUtil.startsWithIgnoreCase(warp.getName(), lastWord))
                        // Checks if the warp has a permission, if not, let it go through
                        // If the warp has a permission, check if the sender has the permission.
                        // If they have the permission, let the warp go through, if not, block it.
                        .filter(warp -> !warp.getPermission().isPresent() || (sender.hasPermission(warp.getPermission().get())))
                        // Print names to list
                        .forEach(warp -> warpNames.add(warp.getName()));

                return warpNames;
            }
            return null;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            WARP_COMMAND_INVALID_ARGS.send(player);
            return;
        }

        Optional<Warp> warp = warpManager.getWarp(args[0]);
        if (!warp.isPresent()) {
            WARP_COMMAND_INVALID_WARP.replace("%name%", args[0]).send(player);
            return;
        }

        Optional<Region> spawn = regionManager.getRegions(player)
                .stream()
                .filter(region -> region.getName().equalsIgnoreCase("spawn"))
                .findFirst();

        Warp theWarp = warp.get();

        boolean inCombat = combatManager.isInCombat(player);
        if (inCombat) {
            CANNOT_DO_IN_COMBAT.send(player);
            return;
        }

        // Permission check
        if (theWarp.getPermission().isPresent()) {
            String perm = theWarp.getPermission().get();
            if (!player.hasPermission(perm)) {
                COMMAND_NO_PERMISSION.send(player);
                return;
            }
        }

        // Checks if they are in spawn
        if (spawn.isPresent()) {
            warpManager.teleport(theWarp, player, true);
        } else {
            if (!warpManager.teleport(theWarp, player, 5))
                WARP_ALREADY_TRANSPORTING.send(player);
        }
    }
}
