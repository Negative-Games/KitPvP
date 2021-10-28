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

package dev.negativekb.kitpvpframework.core.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import static dev.negativekb.kitpvpframework.core.Locale.*;

public abstract class Command extends org.bukkit.command.Command {
    @Getter
    private final List<SubCommand> subCommands = new ArrayList<>();
    @Getter
    @Setter
    public boolean consoleOnly = false;
    @Getter
    @Setter
    public boolean playerOnly = false;
    @Getter
    @Setter
    public boolean disabled = false;
    @Getter
    @Setter
    public String permissionNode = "";
    private TabCompleter completer;

    public Command() {
        this("1");
    }

    public Command(String name) {
        this(name, "", Collections.emptyList());
    }

    public Command(String name, String description) {
        this(name, description, Collections.emptyList());
    }

    public Command(String name, List<String> aliases) {
        this(name, "", aliases);
    }

    public Command(String name, String description, List<String> aliases) {
        super(name, description, "/" + name, aliases);

        boolean hasInfo = getClass().isAnnotationPresent(CommandInfo.class);
        if (hasInfo) {
            CommandInfo annotation = getClass().getAnnotation(CommandInfo.class);
            setName(annotation.name());

            if (annotation.consoleOnly())
                setConsoleOnly(true);

            if (annotation.playerOnly())
                setPlayerOnly(true);

            if (annotation.disabled())
                setDisabled(true);

            if (!annotation.description().isEmpty())
                setDescription(annotation.description());

            List<String> a = new ArrayList<>(Arrays.asList(annotation.aliases()));
            // There will always be an empty index even if no arguments are
            // set. So the way you identify if there are actual arguments in the command
            // is you check if the first index is empty.
            if (!a.get(0).isEmpty()) {
                setAliases(a);
            }

            if (!annotation.permission().isEmpty())
                setPermissionNode(annotation.permission());
        }

    }

    public abstract void onCommand(CommandSender sender, String[] args);

    public void onCommand(CommandSender sender, String label, String[] args) {
        onCommand(sender, args);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        // If the Command is disabled, send this message
        if (isDisabled()) {
            COMMAND_DISABLED.send(sender);
            return true;
        }

        if (isPlayerOnly() && !(sender instanceof Player)) {
            COMMAND_CANNOT_DO_THIS.send(sender);
            return true;
        }

        if (isConsoleOnly() && sender instanceof Player) {
            COMMAND_CANNOT_DO_THIS.send(sender);
            return true;
        }

        // If the permission node is not null and not empty
        // but, if the user doesn't have permission for the command
        // send this message
        String permNode = getPermissionNode();
        if (!permNode.isEmpty() && !sender.hasPermission(permNode)) {
            COMMAND_NO_PERMISSION.send(sender);
            return true;
        }


        // If there are no SubCommands for this Command
        // execute the regular command
        List<SubCommand> subCommands = getSubCommands();
        if (args.length == 0 || subCommands.isEmpty()) {
            onCommand(sender, label, args);
            return true;
        }

        // Gets argument 0
        String arg = args[0];
        // Removes argument 0
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        Optional<SubCommand> command = subCommands.stream().filter(subCmd -> {
            if (subCmd.getArgument().equalsIgnoreCase(arg))
                return true;

            List<String> aliases = subCmd.getAliases();
            if (aliases == null || aliases.isEmpty())
                return false;

            return aliases.contains(arg.toLowerCase());
        }).findFirst();

        if (command.isPresent())
            runSubCommand(command.get(), sender, newArgs);
        else
            onCommand(sender, label, args);

        return true;
    }

    /**
     * Run SubCommand Function
     *
     * @param subCommand SubCommand
     * @param sender     Player/Sender
     * @param args       Arguments
     */
    private void runSubCommand(SubCommand subCommand, CommandSender sender, String[] args) {
        subCommand.execute(sender, args);
    }

    /**
     * @param sender - Sender of the command. Usually a player
     * @param perm   - Permission node
     * @return - Returns whether the player has the permission provided in the "perm" String
     * @deprecated Never used and kind of useless.
     */
    @Deprecated
    public boolean hasPermission(CommandSender sender, String perm) {
        Server server = Bukkit.getServer();
        Player p = server.getPlayer(sender.getName());
        if (p == null) {
            return server.getConsoleSender().hasPermission(perm);
        } else {
            return p.hasPermission(perm);
        }
    }

    public void ifHasPermission(CommandSender sender, String perm, Consumer<CommandSender> consumer) {
        if (sender.hasPermission(perm))
            consumer.accept(sender);
    }

    public void ifNotHasPermission(CommandSender sender, String perm, Consumer<CommandSender> consumer) {
        if (!sender.hasPermission(perm))
            consumer.accept(sender);
    }

    public void ifPlayer(CommandSender sender, Consumer<Player> consumer) {
        if (sender instanceof Player)
            consumer.accept((Player) sender);
    }

    public void ifConsole(CommandSender sender, Consumer<ConsoleCommandSender> consumer) {
        if (sender instanceof ConsoleCommandSender)
            consumer.accept((ConsoleCommandSender) sender);
    }

    /**
     * Checks if a player is online
     *
     * @param name Player Name
     * @return Optional Player
     */
    public Optional<Player> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

    /**
     * Add one or more SubCommands to
     * a command
     *
     * @param subCommands SubCommand(s)
     */
    public void addSubCommands(SubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    public void setTabComplete(BiFunction<CommandSender, String[], List<String>> function) {
        this.completer = (sender, command, alias, args) -> {
            if (alias.equalsIgnoreCase(getName()) || getAliases().contains(alias.toLowerCase())) {
                return function.apply(sender, args);
            }
            return null;
        };
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (completer == null || this.completer.onTabComplete(sender, this, alias, args) == null) {
            String lastWord = args[args.length - 1];
            Player senderPlayer = sender instanceof Player ? (Player) sender : null;
            ArrayList<String> matchedPlayers = new ArrayList<>();
            sender.getServer().getOnlinePlayers().stream()
                    .filter(player -> senderPlayer == null || senderPlayer.canSee(player) && StringUtil.startsWithIgnoreCase(player.getName(), lastWord))
                    .forEach(player -> matchedPlayers.add(player.getName()));

            matchedPlayers.sort(String.CASE_INSENSITIVE_ORDER);
            return matchedPlayers;
        }
        return this.completer.onTabComplete(sender, this, alias, args);
    }
}
