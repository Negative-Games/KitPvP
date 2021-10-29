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

package dev.negativekb.kitpvpframework.commands.region;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.RegionManager;
import dev.negativekb.kitpvpframework.core.command.Command;
import dev.negativekb.kitpvpframework.core.command.CommandInfo;
import dev.negativekb.kitpvpframework.core.command.SubCommand;
import dev.negativekb.kitpvpframework.core.structure.region.Region;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.negativekb.kitpvpframework.core.Locale.*;

@CommandInfo(name = "region", permission = "kitpvp.region")
public class CommandRegion extends Command {

    private final RegionManager regionManager;

    public CommandRegion() {
        regionManager = KitPvPAPI.getInstance().getRegionManager();

        addSubCommands(

        );

        setTabComplete((sender, args) -> {
            if (args.length == 1) {
                List<SubCommand> subCommands = getSubCommands();
                String lastWord = args[args.length - 1];
                List<String> allCommands = new ArrayList<>();

                for (SubCommand subCommand : subCommands) {
                    String argument = subCommand.getArgument();
                    List<String> aliases = subCommand.getAliases();

                    if (StringUtil.startsWithIgnoreCase(argument, lastWord))
                        allCommands.add(argument);

                    if (!aliases.get(0).isEmpty()) {
                        List<String> collect = aliases.stream().filter(s -> StringUtil.startsWithIgnoreCase(s, lastWord))
                                .collect(Collectors.toList());
                        allCommands.addAll(collect);
                    }
                }

                return allCommands;
            }
            return null;
        });
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        REGION_HELP.send(sender);
    }

    @CommandInfo(name = "create", aliases = {"add"}, playerOnly = true)
    private class Create extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {
            Player player = (Player) sender;

            if (args.length == 0) {
                REGION_HELP.send(player);
                return;
            }

            String name = args[0];
            Optional<Region> region = regionManager.getRegion(name);
            if (region.isPresent()) {
                REGION_ALREADY_EXISTS.replace("%name%", region.get().getName()).send(player);
                return;
            }


        }
    }

    @CommandInfo(name = "delete", aliases = {"remove"})
    private class Delete extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {

        }
    }

    @CommandInfo(name = "edit", playerOnly = true)
    private class Edit extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {

        }
    }

    @CommandInfo(name = "teleport", aliases = {"tp"}, playerOnly = true)
    private class Teleport extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {

        }
    }

    @CommandInfo(name = "list", playerOnly = true)
    private class ListRegion extends SubCommand {

        @Override
        public void runCommand(CommandSender sender, String[] args) {

        }
    }

}
