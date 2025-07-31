package me.strikes.echo.common.module;

import lombok.Getter;
import me.strikes.echo.EchoChat;
import me.strikes.echo.common.utils.ConfigFile;

import java.util.ArrayList;
import java.util.List;

/*
 * EchoChat | Manager
 *
 * @author 7Str1kes
 * @date 31/07/2025
 *
 * Copyright (c) 2025 7Str1kes. All rights reserved.
 */
@Getter
public class Manager {

    private final EchoChat instance;
    @Getter private static ConfigFile configFile, languageFile;
    @Getter private static final List<ConfigFile> files = new ArrayList<>();

    public Manager(EchoChat instance) {
        this.instance = instance;
        this.loadConfigFiles();
    }

    private void loadConfigFiles() {
        initializeConfigFiles();
        addFilesToList();
    }

    private void initializeConfigFiles() {
        configFile = new ConfigFile(instance, "config.yml");
        languageFile = new ConfigFile(instance, "language.yml");
    }

    private void addFilesToList() {
        files.add(configFile);
        files.add(languageFile);
    }

    public static void reloadConfigs() {
        files.forEach(ConfigFile::reload);
    }

    public void saveConfigs() {
        files.forEach(ConfigFile::save);
    }
}