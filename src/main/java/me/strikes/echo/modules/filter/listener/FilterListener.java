package me.strikes.echo.modules.filter.listener;

import me.strikes.echo.common.module.Module;
import me.strikes.echo.common.utils.CC;
import me.strikes.echo.modules.filter.FilterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/*
 * EchoChat | FilterListener
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
public class FilterListener extends Module<FilterManager> {

    public FilterListener(FilterManager manager) {
        super(manager);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        FilterManager manager = getManager();

        if (player.hasPermission(manager.getBypassPermission())) return;

        String originalMessage = event.getMessage();
        String filteredMessage = manager.filterMessage(originalMessage);

        if (filteredMessage == null) {
            event.setCancelled(true);
            player.sendMessage(CC.t(manager.getWarningMessage()));
        } else if (!filteredMessage.equals(originalMessage)) {
            event.setMessage(filteredMessage);
        }
    }
}