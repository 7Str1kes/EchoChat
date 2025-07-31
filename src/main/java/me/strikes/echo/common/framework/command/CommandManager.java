package me.strikes.echo.common.framework.command;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.framework.command.executor.CommandExecutorAdapter;
import me.strikes.echo.common.framework.command.executor.DynamicCommand;
import me.strikes.echo.common.module.Manager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

/*
 * EchoChat | CommandManager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
public class CommandManager extends Manager {

    public CommandManager(EchoChat instance) {
        super(instance);
    }

    public void register(Command command) {
        try {
            DynamicCommand dynamicCommand = new DynamicCommand(
                    command.getName(),
                    "Command name: " + command.getName() + ", Permission: " + command.getPermission() + ", Aliases: " + command.aliases(),
                    command.getPermission(),
                    command.aliases().toArray(new String[0])
            );

            dynamicCommand.setExecutor(new CommandExecutorAdapter(command));
            getCommandMap().register(getInstance().getDescription().getName(), dynamicCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CommandMap getCommandMap() throws Exception {
        Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        bukkitCommandMap.setAccessible(true);
        return (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
    }
}