package me.strikes.echo.common.module;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import org.bukkit.event.Listener;

/*
 * EchoChat | Module
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class Module<T extends Manager> implements Listener {

    private final EchoChat instance;
    private final T manager;

    public Module(T manager) {
        this.instance = manager.getInstance();
        this.manager = manager;
    }
}