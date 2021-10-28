package dev.negativekb.kitpvpframework.core.implementation;

import dev.negativekb.kitpvpframework.api.ProfileManager;
import dev.negativekb.kitpvpframework.core.cache.ObjectCache;
import dev.negativekb.kitpvpframework.core.structure.profile.Profile;
import lombok.SneakyThrows;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class ProfileManagerImpl extends ObjectCache<Profile> implements ProfileManager {

    private ArrayList<Profile> profiles;

    @SneakyThrows
    public ProfileManagerImpl(JavaPlugin plugin) {
        super(plugin.getDataFolder().getPath() + "/data/profiles.json", Profile[].class);

        profiles = load();

        new ProfileSaveTask().runTaskTimerAsynchronously(plugin, 0, 20 * 60);
    }

    @Override
    public void addProfile(UUID uuid) {
        if (getProfile(uuid).isPresent())
            return;

        Profile profile = new Profile(uuid);
        profiles.add(profile);
    }

    @Override
    public void removeProfile(Profile profile) {
        profiles.remove(profile);
    }

    @Override
    public Optional<Profile> getProfile(UUID uuid) {
        return profiles.stream().filter(profile -> profile.getUniqueID().equals(uuid)).findFirst();
    }

    @Override
    public Optional<Profile> getProfile(Player player) {
        return getProfile(player.getUniqueId());
    }

    @Override
    public Optional<Profile> getProfile(OfflinePlayer player) {
        return getProfile(player.getUniqueId());
    }

    @Override
    public ArrayList<Profile> getProfiles() {
        return profiles;
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        save(profiles);
    }

    private class ProfileSaveTask extends BukkitRunnable {

        @SneakyThrows
        @Override
        public void run() {
            save(profiles);
            profiles = load();
        }
    }
}
