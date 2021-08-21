package me.linoxgh.permannouncements2;

import me.linoxgh.permannouncements2.Commands.MainCommand;
import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.ConfigStorage;
import me.linoxgh.permannouncements2.IO.IOManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermAnnouncements2 extends JavaPlugin {
    private final AnnouncementStorage announcements = new AnnouncementStorage();
    private final ConfigStorage configs = new ConfigStorage();
    private AnnouncementTask task;
    private IOManager ioManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        ioManager = new IOManager(this, configs, announcements);
        ioManager.loadConfig();
        getLogger().info("Successfully loaded config options.");

        if (ioManager.loadAnnouncements()) {
            getLogger().info("Successfully loaded announcements.");
        } else {
            getLogger().warning("Could not load announcements. Please check the config.yml using a YAML Parser.");
            return;
        }

        getCommand("permannouncements2").setExecutor(new MainCommand(announcements, configs));
        new Listeners(this, announcements);
        task = new AnnouncementTask(this, announcements, configs);
    }

    @Override
    public void onDisable() {
        if (ioManager.saveConfig()) {
            getLogger().info("Successfully saved config options.");
        } else {
            getLogger().warning("Failed to save config options.");
        }

        if (ioManager.saveAnnouncements()) {
            getLogger().info("Successfully saved announcements.");
        } else {
            getLogger().warning("Failed to save announcements.");
        }
    }

    public void reset() {
        task.refresh(this);
    }
}
