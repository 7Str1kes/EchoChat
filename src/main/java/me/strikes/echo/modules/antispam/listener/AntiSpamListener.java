package me.strikes.echo.modules.antispam.listener;

import me.strikes.echo.common.module.Module;
import me.strikes.echo.modules.antispam.AntiSpamManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/*
 * EchoChat | AntiSpamListener
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
public class AntiSpamListener extends Module<AntiSpamManager> {

    public AntiSpamListener(AntiSpamManager manager) {
        super(manager);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (getManager().isSpamming(player, event.getMessage())) {
            event.setCancelled(true);
            String cooldownMsg = getInstance().getConfig().getString("anti-spam.message", "&cEspera %cooldown% segundos antes de hablar de nuevo.")
                    .replace("%cooldown%", String.valueOf(getManager().getCooldownSeconds()));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', cooldownMsg));
        }
    }
}