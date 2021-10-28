package dev.negativekb.kitpvpframework.commands;

import dev.negativekb.kitpvpframework.core.command.Command;
import dev.negativekb.kitpvpframework.core.command.CommandInfo;
import dev.negativekb.kitpvpframework.kits.Kit;
import dev.negativekb.kitpvpframework.kits.Kits;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

import static dev.negativekb.kitpvpframework.core.Locale.*;

@CommandInfo(name = "viewkit", aliases = {"kitview", "kv", "vk", "kitinfo"}, playerOnly = true)
public class CommandViewKit extends Command {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            VIEW_KIT_SYNTAX_ERROR.send(player);
            return;
        }

        String name = args[0];
        Optional<Kits> byName = Kits.getByName(name);
        if (!byName.isPresent()) {
            VIEW_KIT_INVALID_KIT.replace("%name%", name).send(player);
            return;
        }

        Optional<Kit> theKit = byName.get().getKit();
        theKit.ifPresent(kit -> {
            // TODO: Open Kit Viewer Menu
        });
    }
}
