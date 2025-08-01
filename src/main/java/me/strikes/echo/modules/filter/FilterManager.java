package me.strikes.echo.modules.filter;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.module.Manager;
import me.strikes.echo.common.utils.ConfigFile;

import java.util.HashSet;
import java.util.Set;

/*
 * EchoChat | FilterManager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class FilterManager extends Manager {

    private final Set<String> blockedWords = new HashSet<>();
    private boolean filterEnabled;
    private String bypassPermission;
    private String warningMessage;
    private boolean replaceWithAsterisks;
    private String replacementChar;

    public FilterManager(EchoChat instance) {
        super(instance);
        this.reload();
    }

    public void reload() {
        ConfigFile config = Manager.getConfigFile();

        this.filterEnabled = config.getBoolean("word-filter.enabled", true);
        this.bypassPermission = config.getString("word-filter.bypass-permission", "echochat.filter.bypass");
        this.warningMessage = config.getString("word-filter.message", "&cYour message contains blocked words.");
        this.replaceWithAsterisks = config.getBoolean("word-filter.replace-with-asterisks", true);
        this.replacementChar = config.getString("word-filter.replacement-char", "*");

        blockedWords.clear();
        blockedWords.addAll(config.getStringList("word-filter.blocked-words"));
    }

    public String filterMessage(String message) {
        if (!filterEnabled) return message;

        String[] words = message.split(" ");
        StringBuilder filtered = new StringBuilder();

        for (String word : words) {
            if (blockedWords.contains(word.toLowerCase())) {
                if (replaceWithAsterisks) {
                    filtered.append(replacementChar.repeat(word.length())).append(" ");
                } else {
                    return null;
                }
            } else {
                filtered.append(word).append(" ");
            }
        }

        return filtered.toString().trim();
    }
}