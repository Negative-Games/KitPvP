package dev.negativekb.kitpvpframework.core;

import dev.negativekb.kitpvpframework.core.util.Message;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    // Commands
    COMMAND_DISABLED(Collections.singletonList("&cThis command is disabled.")),
    COMMAND_CANNOT_DO_THIS(Collections.singletonList("&cYou cannot do this.")),
    COMMAND_NO_PERMISSION(Collections.singletonList("&cYou do not have permission to use this command.")),

    KILLSTREAK_ENDED(Collections.singletonList("&6%player% &ehas been killed on a killstreak of &c&l%amount%&e.")),
    KILLSTREAK_REACHED(Collections.singletonList("&6&l%player% &ehas reached a killstreak of &c&l%amount%&e!")),

    VIEW_KIT_SYNTAX_ERROR(Collections.singletonList("&cInvalid syntax! Do /viewkit <kit>")),
    VIEW_KIT_INVALID_KIT(Collections.singletonList("&cInvalid kit name `&e%name%&c`!")),

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
