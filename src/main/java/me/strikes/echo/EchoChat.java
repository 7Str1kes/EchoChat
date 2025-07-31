package me.strikes.echo;

import lombok.Getter;
import lombok.Setter;
import me.strikes.echo.common.framework.listener.ListenerManager;
import me.strikes.echo.common.module.Manager;
import me.strikes.echo.core.logging.LogHandler;
import me.strikes.echo.core.shutdown.ShutdownManager;
import me.strikes.echo.core.startup.StartupManager;
import me.strikes.echo.modules.antispam.AntiSpamManager;
import me.strikes.echo.modules.filter.FilterManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

@Getter
@Setter
public final class EchoChat extends JavaPlugin {
    @Getter
    private static EchoChat instance;

    // Core components
    private final LogHandler logHandler = new LogHandler();
    private final Manager manager = new Manager(this);

    // Core managers
    private StartupManager startupManager;
    private ShutdownManager shutdownManager;

    // Feature managers
    private ListenerManager listenerManager;
    private AntiSpamManager antiSpamManager;
    private FilterManager filterManager;

    // Plugin state
    private boolean fullyLoaded = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        try {
            logHandler.sendEnable();

            // Initialize startup manager and let it handle everything
            startupManager = new StartupManager(this);
            if (!startupManager.initialize()) {
                getLogger().severe("Failed to initialize plugin! Disabling...");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

            fullyLoaded = true;
            getLogger().info("GoldenHub has been successfully enabled!");

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Critical error during plugin initialization!", e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        fullyLoaded = false;

        try {
            logHandler.sendDisable();

            // Initialize shutdown manager and let it handle everything
            if (shutdownManager == null) {
                shutdownManager = new ShutdownManager(this);
            }
            shutdownManager.shutdown();

            getLogger().info("GoldenHub has been successfully disabled!");

        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Error during plugin shutdown!", e);
        } finally {
            instance = null;
        }
    }
}
