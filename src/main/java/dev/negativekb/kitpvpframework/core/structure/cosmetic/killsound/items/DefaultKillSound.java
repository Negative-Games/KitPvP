package dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.items;

import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSound;
import dev.negativekb.kitpvpframework.core.structure.cosmetic.killsound.KillSoundType;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DefaultKillSound extends KillSound {

    private final Sound sound;

    public DefaultKillSound() {
        super(KillSoundType.DEFAULT);

        sound = Sound.LEVEL_UP;
    }

    @Override
    public void send(Player victim, Player killer) {
        killer.playSound(victim.getLocation(), sound, 1, 1);
    }

    // No icon needed for this.
    // For non-default Kill Sounds you need an icon for
    // them to show up in the menu!
    @Override
    public ItemStack getIcon() {
        return null;
    }
}
