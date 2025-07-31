package me.strikes.echo.core.shutdown;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.module.Manager;

import java.util.logging.Level;

/*
 * EchoChat | ShutdownManager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class ShutdownManager extends Manager {

    public ShutdownManager(EchoChat instance) {
        super(instance);
    }

    /**
     * Shutdown all plugin components gracefully
     */
    public void shutdown() {
        try {
            getInstance().getLogger().info("Starting graceful shutdown...");

            shutdownFeatureManagers();

            shutdownCoreManagers();

            getInstance().getLogger().info("Graceful shutdown completed!");

        } catch (Exception e) {
            getInstance().getLogger().log(Level.SEVERE, "Error during shutdown!", e);
        }
    }

    /**
     * Shutdown feature managers
     */
    private void shutdownFeatureManagers() {
        safeShutdown("ListenerManager", () -> {
            if (getInstance().getListenerManager() != null) {
                // Add any cleanup logic if needed
            }
        });

        safeShutdown("ItemInteractManager", () -> {
            if (getInstance().getAntiSpamManager() != null) {
                // Add any cleanup logic if needed
            }
        });
    }

    /**
     * Shutdown core managers
     */
    private void shutdownCoreManagers() {
        safeShutdown("StaffManager", () -> {
//            if (getInstance().getStaffManager() != null) {
//                // Add any cleanup logic if needed
//            }
        });

        safeShutdown("MenuManager", () -> {
//            if (getInstance().getMenuManager() != null) {
//                // Add any cleanup logic if needed
//            }
        });
    }

    /**
     * Safely execute shutdown operations with error handling
     */
    private void safeShutdown(String componentName, Runnable shutdownTask) {
        try {
            shutdownTask.run();
        } catch (Exception e) {
            getInstance().getLogger().log(Level.WARNING, "Error shutting down " + componentName, e);
        }
    }
}