package dev.negativekb.kitpvpframework.core.implementation.registry;

import dev.negativekb.kitpvpframework.api.registry.ListenerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class ListenerRegisterImpl implements ListenerRegistry {

    private final JavaPlugin plugin;

    public ListenerRegisterImpl(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register(Listener... listeners) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Arrays.stream(listeners).forEach(listener -> pluginManager.registerEvents(listener, plugin));
    }
}
