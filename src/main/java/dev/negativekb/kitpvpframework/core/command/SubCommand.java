package dev.negativekb.kitpvpframework.core.command;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

import static dev.negativekb.kitpvpframework.core.Locale.*;

/**
 * SubCommand
 *
 * @author Negative
 * @apiNote Must be added to a {@link Command} class in order to work!
 */
public abstract class SubCommand {

    // subcommands of subcommands lol
    @Getter
    private final List<SubCommand> subCommands = new ArrayList<>();
    @Setter
    @Getter
    private String argument;
    @Getter
    @Setter
    private List<String> aliases;
    @Getter
    @Setter
    private String permission = "";
    @Getter
    @Setter
    private boolean consoleOnly = false;
    @Getter
    @Setter
    private boolean playerOnly = false;
    @Getter
    @Setter
    private boolean disabled;


    public SubCommand() {
        this(null, null);
    }

    /**
     * SubCommand Constructor
     *
     * @param argument Argument of the SubCommand
     * @apiNote SubCommand argument and aliases are equalsIgnoreCase!
     * @apiNote There are no aliases for this constructor!
     */
    public SubCommand(String argument) {
        this(argument, Collections.emptyList());
    }

    /**
     * SubCommand Constructor
     *
     * @param argument Argument of the SubCommand
     * @param aliases  Aliases of the SubCommand
     * @apiNote SubCommand argument and aliases are equalsIgnoreCase!
     */
    public SubCommand(String argument, List<String> aliases) {
        this.argument = argument;
        this.aliases = aliases;

        if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
            CommandInfo annotation = this.getClass().getAnnotation(CommandInfo.class);
            setArgument(annotation.name());

            if (annotation.aliases().length != 0) {
                String[] alias = annotation.aliases();
                List<String> a = new ArrayList<>(Arrays.asList(alias));
                setAliases(a);
            }

            if (!annotation.permission().isEmpty())
                setPermission(annotation.permission());

        }
    }

    public void execute(CommandSender sender, String[] args) {
        // If the Command is disabled, send this message

        if (isDisabled()) {
            COMMAND_DISABLED.send(sender);
            return;
        }

        if (isPlayerOnly() && !(sender instanceof Player)) {
            COMMAND_CANNOT_DO_THIS.send(sender);
            return;
        }

        if (isConsoleOnly() && sender instanceof Player) {
            COMMAND_CANNOT_DO_THIS.send(sender);
            return;
        }

        // If the permission node is not null and not empty
        // but, if the user doesn't have permission for the command
        // send this message
        if (!getPermission().isEmpty()) {
            if (!sender.hasPermission(getPermission())) {
                COMMAND_NO_PERMISSION.send(sender);
                return;
            }
        }

        // Checks if the SubCommand SubCommands are empty (subcommand seption)
        // if so, execute regular command
        List<SubCommand> subCommands = getSubCommands();
        if (args.length == 0 || subCommands.isEmpty()) {
            runCommand(sender, args);
            return;
        }

        // Gets args 0
        String arg = args[0];
        // Removes args 0
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
            runCommand(sender, args);
    }

    public abstract void runCommand(CommandSender sender, String[] args);

    /**
     * Adds SubCommand to the SubCommand's subcommands
     * SubCommand seption
     *
     * @param subCommands SubCommand(s)
     */
    public void addSubCommands(SubCommand... subCommands) {
        this.subCommands.addAll(Arrays.asList(subCommands));
    }

    /**
     * Runs a SubCommand
     *
     * @param subCommand SubCommand
     * @param sender     Sender
     * @param args       Arguments
     */
    private void runSubCommand(SubCommand subCommand, CommandSender sender, String[] args) {
        subCommand.execute(sender, args);
    }

    /**
     * Checks to see if a Player is online
     *
     * @param name Name
     * @return Optional
     */
    public Optional<Player> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }

}
