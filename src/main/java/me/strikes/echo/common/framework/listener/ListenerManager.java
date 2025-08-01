package me.strikes.echo.common.framework.listener;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.module.Manager;
import me.strikes.echo.core.listener.MainListener;
import me.strikes.echo.modules.antispam.listener.AntiSpamListener;
import me.strikes.echo.modules.filter.listener.FilterListener;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

/*
 * EchoChat | ListenerManager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class ListenerManager extends Manager {

    public ListenerManager(EchoChat instance) {
        super(instance);

        List<Listener> listeners = Arrays.asList(
                new MainListener(this),
                new FilterListener(getInstance().getFilterManager()),
                new AntiSpamListener(getInstance().getAntiSpamManager())
        );

        registerListener(listeners.toArray(new Listener[0]));
    }

    private void registerListener(Listener... listeners) {
        for (Listener listener : listeners) {
            getInstance().getServer().getPluginManager().registerEvents(listener, getInstance());
        }
    }
}