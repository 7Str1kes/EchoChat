package me.strikes.echo.common.framework.command;

import me.strikes.echo.EchoChat;
import me.strikes.echo.core.command.MainCommand;

import java.util.Arrays;

/*
 * EchoChat | CommandRegistry
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
public class CommandRegistry {

    public static void registerAll(EchoChat instance) {
        CommandManager manager = new CommandManager(instance);

        Arrays.asList(
                new MainCommand(manager)
        ).forEach(manager::register);
    }
}