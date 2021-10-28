package dev.negativekb.kitpvpframework.commands;

import dev.negativekb.kitpvpframework.api.KitPvPAPI;
import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.core.command.Command;
import dev.negativekb.kitpvpframework.core.command.CommandInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "kit", aliases = {"ekit", "ekits", "kits"}, playerOnly = true)
public class CommandKit extends Command {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        // TODO: Open Kit Selection Menu
    }
}
