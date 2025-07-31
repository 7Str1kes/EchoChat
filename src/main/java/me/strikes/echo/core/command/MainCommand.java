package me.strikes.echo.core.command;

import me.strikes.echo.common.framework.command.Command;
import me.strikes.echo.common.framework.command.CommandManager;
import me.strikes.echo.common.module.Manager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/*
 * EchoChat | MainCommand
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
public class MainCommand extends Command {

    public MainCommand(CommandManager manager) {
        super(manager, "echochat", "echochat.admin");
    }

    @Override
    public List<String> aliases() {
        return List.of();
    }

    @Override
    public List<String> usage() {
        return List.of();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sendMessage(sender, getLanguageFile().getString("global.not-player"));
            return;
        }

        if (!player.hasPermission(getPermission())) {
            sendMessage(sender, getLanguageFile().getString("global.no-permission"));
            return;
        }

        if (args.length == 0) {
            sendUsage(player);
            return;
        }

        String action = args[0];

        switch (action) {
            case "reload" -> {
                if (args.length < 2) {
                    sendMessage(player, "&eUsage: /echochat reload <configFile>");
                    return;
                }

                String fileName = args[1].toLowerCase().replace(".yml", "");

                switch (fileName) {
                    case "config" -> getConfigFile().reload();
                    case "language" -> getLanguageFile().reload();
                    case "all" -> Manager.reloadConfigs();
                    default -> {
                        sendMessage(player, "&cArchivo no encontrado: &f" + fileName);
                        return;
                    }
                }

                getInstance().getAntiSpamManager().reload();
                getInstance().getFilterManager().reload();

                sendMessage(player, "&aArchivo &f" + fileName + ".yml &arecargado con éxito.");
            }
        }
    }
}