package dev.negativekb.kitpvpframework.core.implementation.registry;

import dev.negativekb.kitpvpframework.api.registry.CommandRegistry;
import dev.negativekb.kitpvpframework.core.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class CommandRegisterImpl implements CommandRegistry {

    @SuppressWarnings("unchecked")
    @Override
    public void register(Command... commands) {
        Arrays.stream(commands).forEach(iCommand -> {
            try {
                Server server = Bukkit.getServer();
                Field field = server.getClass().getDeclaredField("commandMap");
                field.setAccessible(true);
                CommandMap commandMap = (CommandMap) field.get(server);

                String name = iCommand.getName();

                org.bukkit.command.Command command = commandMap.getCommand(name);
                if (command != null) {
                    Map<String, Command> map;
                    Field commandField = commandMap.getClass().getDeclaredField("knownCommands");
                    commandField.setAccessible(true);
                    map = (Map<String, Command>) commandField.get(commandMap);
                    command.unregister(commandMap);
                    map.remove(name);
                    iCommand.getAliases().forEach(map::remove);
                }

                commandMap.register(name, iCommand);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
