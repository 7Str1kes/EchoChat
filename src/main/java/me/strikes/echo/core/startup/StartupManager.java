package me.strikes.echo.core.startup;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.framework.command.CommandRegistry;
import me.strikes.echo.common.framework.listener.ListenerManager;
import me.strikes.echo.common.module.Manager;
import me.strikes.echo.core.shutdown.ShutdownManager;
import me.strikes.echo.modules.antispam.AntiSpamManager;
import me.strikes.echo.modules.filter.FilterManager;

import java.util.logging.Level;

/*
 * EchoChat | StartupManager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class StartupManager extends Manager {

    public StartupManager(EchoChat instance) {
        super(instance);
    }

    /**
     * Initialize all plugin components
     * @return true if successful, false otherwise
     */
    public boolean initialize() {
        try {
            if (!initializeCoreManagers()) {
                getInstance().getLogger().severe("Failed to initialize core managers!");
                return false;
            }

            if (!initializeFeatureManagers()) {
                getInstance().getLogger().severe("Failed to initialize feature managers!");
                return false;
            }

            registerCommands();

            getInstance().setShutdownManager(new ShutdownManager(getInstance()));

            return true;

        } catch (Exception e) {
            getInstance().getLogger().log(Level.SEVERE, "Error during startup initialization!", e);
            return false;
        }
    }

    /**
     * Initialize core managers
     */
    private boolean initializeCoreManagers() {
        try {
            getInstance().getLogger().info("Initializing core managers...");

//            // Menu manager
//            getInstance().setMenuManager(new MenuManager(getInstance()));
//

            getInstance().getLogger().info("Core managers initialized successfully!");
            return true;

        } catch (Exception e) {
            getInstance().getLogger().log(Level.SEVERE, "Error initializing core managers!", e);
            return false;
        }
    }

    /**
     * Initialize feature managers
     */
    private boolean initializeFeatureManagers() {
        try {
            getInstance().getLogger().info("Initializing feature managers...");

            // Anti Spam manager
            getInstance().setAntiSpamManager(new AntiSpamManager(getInstance()));

            // Filter manager
            getInstance().setFilterManager(new FilterManager(getInstance()));

            // Listener manager (initialize last as it depends on other managers)
            getInstance().setListenerManager(new ListenerManager(getInstance()));

            getInstance().getLogger().info("Feature managers initialized successfully!");
            return true;

        } catch (Exception e) {
            getInstance().getLogger().log(Level.SEVERE, "Error initializing feature managers!", e);
            return false;
        }
    }

    /**
     * Register all commands
     */
    private void registerCommands() {
        try {
            getInstance().getLogger().info("Registering commands...");

            CommandRegistry.registerAll(getInstance());

            getInstance().getLogger().info("Commands registered successfully!");
        } catch (Exception e) {
            getInstance().getLogger().log(Level.SEVERE, "Error registering commands!", e);
        }
    }
}