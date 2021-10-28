package dev.negativekb.kitpvpframework.api.registry;

import dev.negativekb.kitpvpframework.core.command.Command;

public interface CommandRegistry {

    void register(Command... commands);

}
