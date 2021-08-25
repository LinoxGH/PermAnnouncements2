package me.linoxgh.permannouncements2;

import java.util.HashSet;
import java.util.Set;

import me.linoxgh.permannouncements2.commands.MainCommand;
import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.ConfigStorage;
import me.linoxgh.permannouncements2.data.Message;
import me.linoxgh.permannouncements2.io.IOManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PermAnnouncements2 extends JavaPlugin {
    private AnnouncementStorage announcements;
    private AnnouncementTask task;
    private IOManager ioManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.announcements = new AnnouncementStorage();
        ConfigStorage configs = new ConfigStorage();

        ioManager = new IOManager(this, configs, announcements);
        ioManager.loadConfig();
        getLogger().info("Successfully loaded config options.");

        if (ioManager.loadAnnouncements()) {
            getLogger().info("Successfully loaded announcements.");
        } else {
            getLogger().warning("Could not load announcements. Please check the config.yml using a YAML Parser.");
            return;
        }

        getCommand("permannouncements2").setExecutor(new MainCommand(this, announcements, configs));
        new Listeners(this, announcements);

        for (Player p : Bukkit.getOnlinePlayers()) {
            Set<String> permissionGroup = new HashSet<>();
            Set<Message> messages = new HashSet<>();

            for (Message message : announcements.getMessages()) {
                if (message.getPermission() == null) {
                    messages.add(message);
                    continue;
                }

                if (p.hasPermission(message.getPermission())) {
                    messages.add(message);
                    permissionGroup.add(message.getPermission());
                }
            }

            announcements.addMessage(permissionGroup, messages, p.getUniqueId());
        }
        task = new AnnouncementTask(this, announcements, configs);
    }

    @Override
    public void onDisable() {
        save();
    }

    public void load() {
        ioManager.loadConfig();
        getLogger().info("Successfully loaded config options.");

        if (ioManager.loadAnnouncements()) {
            getLogger().info("Successfully loaded announcements.");
        } else {
            getLogger().warning("Could not load announcements. Please check the config.yml using a YAML Parser.");
        }
    }

    public void save() {
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
        for (Player p : Bukkit.getOnlinePlayers()) {
            Set<String> permissionGroup = new HashSet<>();
            Set<Message> messages = new HashSet<>();

            for (Message message : announcements.getMessages()) {
                if (message.getPermission() == null) {
                    messages.add(message);
                    continue;
                }

                if (p.hasPermission(message.getPermission())) {
                    messages.add(message);
                    permissionGroup.add(message.getPermission());
                }
            }
            announcements.addMessage(permissionGroup, messages, p.getUniqueId());
        }
        task.refresh(this);
    }
}
