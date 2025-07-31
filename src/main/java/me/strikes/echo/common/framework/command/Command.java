package me.strikes.echo.common.framework.command;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.module.Manager;
import me.strikes.echo.common.module.Module;
import me.strikes.echo.common.utils.CC;
import me.strikes.echo.common.utils.ConfigFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/*
 * EchoChat | Command
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public abstract class Command extends Module<CommandManager> {

    protected final EchoChat instance;
    private final String name;
    private final String permission;

    public Command(CommandManager manager, String name, String permission) {
        super(manager);
        this.instance = manager.getInstance();
        this.name = name;
        this.permission = permission;
    }

    protected void sendMessage(CommandSender sender, String msg) {
        sender.sendMessage(CC.t(msg));
    }

    protected void sendMessage(Player player, String msg) {
        player.sendMessage(CC.t(msg));
    }

    protected ConfigFile getConfigFile() {
        return Manager.getConfigFile();
    }

    protected ConfigFile getLanguageFile() {
        return Manager.getLanguageFile();
    }

    public abstract List<String> aliases();

    public abstract List<String> usage();

    public abstract void execute(CommandSender sender, String[] args);

    protected void sendUsage(CommandSender sender) {
        sendMessage(sender, String.join(" ", usage()));
    }

    protected void sendUsage(Player player) {
        sendMessage(player, String.join(" ", usage()));
    }
}