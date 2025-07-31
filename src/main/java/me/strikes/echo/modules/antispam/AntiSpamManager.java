package me.strikes.echo.modules.antispam;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.module.Manager;
import me.strikes.echo.common.utils.ConfigFile;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
 * EchoChat | AntiSpamManager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class AntiSpamManager extends Manager {

    private final Map<UUID, Long> lastMessageTimes = new HashMap<>();
    private int cooldownSeconds;
    private boolean blockDuplicateMessages;
    private int maxCapsPercentage;
    private boolean enabled;

    public AntiSpamManager(EchoChat instance) {
        super(instance);
        this.reload();
    }

    public void reload() {
        ConfigFile config = Manager.getConfigFile();

        this.enabled = config.getBoolean("anti-spam.enabled", true);
        this.cooldownSeconds = config.getInt("anti-spam.cooldown", 3);
        this.blockDuplicateMessages = config.getBoolean("anti-spam.block-duplicates", true);
        this.maxCapsPercentage = config.getInt("anti-spam.max-caps-percentage", 60);
    }

    public boolean isSpamming(Player player, String message) {
        if (!enabled) return false;

        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();

        // Cooldown check
        if (lastMessageTimes.containsKey(uuid)) {
            long lastTime = lastMessageTimes.get(uuid);
            if (currentTime - lastTime < cooldownSeconds * 1000L) {
                return true;
            }
        }

        // Duplicate message check
        if (blockDuplicateMessages && lastMessageTimes.containsKey(uuid)) {
            String lastMessage = player.getMetadata("lastChatMessage").get(0).asString();
            if (message.equalsIgnoreCase(lastMessage)) {
                return true;
            }
        }

        // CAPS check (optional)
        if (maxCapsPercentage > 0) {
            int capsCount = message.replaceAll("[^A-Z]", "").length();
            int totalChars = message.replaceAll("\\s", "").length();
            if (totalChars > 0 && (capsCount * 100 / totalChars) > maxCapsPercentage) {
                return true;
            }
        }

        lastMessageTimes.put(uuid, currentTime);
        player.setMetadata("lastChatMessage", new FixedMetadataValue(getInstance(), message));
        return false;
    }
}