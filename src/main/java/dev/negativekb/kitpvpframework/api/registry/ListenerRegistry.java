package dev.negativekb.kitpvpframework.api.registry;

import org.bukkit.event.Listener;

public interface ListenerRegistry {

    void register(Listener... listeners);
}
