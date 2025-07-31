package me.strikes.echo.common.framework.command.executor;

import me.strikes.echo.common.framework.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/*
 * EchoChat | CommandExecutorAdapter
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
public class CommandExecutorAdapter implements CommandExecutor {

    private final Command instance;

    public CommandExecutorAdapter(Command instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, String @NotNull [] args) {
        instance.execute(sender, args);
        return true;
    }
}