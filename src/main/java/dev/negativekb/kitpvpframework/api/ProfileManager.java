package dev.negativekb.kitpvpframework.api;

import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public interface ProfileManager {

    void addProfile(UUID uuid);

    void removeProfile(Profile profile);

    Optional<Profile> getProfile(UUID uuid);

    Optional<Profile> getProfile(Player player);

    Optional<Profile> getProfile(OfflinePlayer player);

    ArrayList<Profile> getProfiles();

    void onDisable();
}
