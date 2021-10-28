package dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.items;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessage;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killmessage.KillMessageType;
import dev.negativekb.kitpvpframework.core.util.Message;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefaultKillMessage extends KillMessage {

    private final Message killerMessage;
    private final Message victimMessage;
    public DefaultKillMessage() {
        super(KillMessageType.DEFAULT);

        killerMessage = new Message("&7You have killed &c%victim&7!%");
        victimMessage = new Message("&7You have been killed by &c%killer%");
    }

    @Override
    public void send(Player victim, Player killer) {
        victimMessage.replace("%killer%", killer.getName()).send(victim);
        killerMessage.replace("%victim%", victim.getName()).send(killer);
    }

    // No icon needed for this.
    // For non-default Kill Messages you need an icon for
    // them to show up in the menu!
    @Override
    public ItemStack getIcon() {
        return null;
    }
}
