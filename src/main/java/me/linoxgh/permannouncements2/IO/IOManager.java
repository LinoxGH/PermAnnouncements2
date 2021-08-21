package me.linoxgh.permannouncements2.IO;

import java.io.File;
import java.io.IOException;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.ConfigStorage;
import me.linoxgh.permannouncements2.Data.Message;
import me.linoxgh.permannouncements2.PermAnnouncements2;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class IOManager {
    private final ConfigStorage configStorage;
    private final AnnouncementStorage announcementStorage;
    private final FileConfiguration config;
    private final File configFile;

    public IOManager(@NotNull PermAnnouncements2 plugin, @NotNull ConfigStorage configStorage, @NotNull AnnouncementStorage announcementStorage) {
        this.configStorage = configStorage;
        this.announcementStorage = announcementStorage;
        this.config = plugin.getConfig();
        configFile = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
    }

    public void loadConfig() {
        configStorage.setInterval(config.getInt("interval", 30));
        configStorage.setPrefixEnabled(config.getBoolean("prefix.enabled", true));
        configStorage.setPrefix(ChatColor.translateAlternateColorCodes('&', config.getString("prefix.text", "&9[&e!&9]")));
    }

    public boolean loadAnnouncements() {
        ConfigurationSection cfg = config.getConfigurationSection("announcements");
        if (cfg == null) return false;
        for (String key : cfg.getKeys(false)) {
            String message = cfg.getString(key + ".message");
            if (message == null) continue;

            String permission = cfg.getString(key + ".permission");
            int weight = cfg.getInt(key + ".weight");

            announcementStorage.addMessage(new Message(key, ChatColor.translateAlternateColorCodes('&', message), permission, weight));
        }
        return true;
    }

    public boolean saveConfig() {
        config.set("interval", configStorage.getInterval());
        config.set("prefix.enabled", configStorage.isPrefixEnabled());
        config.set("prefix.text", configStorage.getPrefix());

        try {
            config.save(configFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean saveAnnouncements() {
        config.set("announcements", null);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        ConfigurationSection cfg = config.createSection("announcements");
        for (Message message : announcementStorage.getMessages()) {
            String key = message.getName();
            if (!cfg.contains(message.getName())) cfg = cfg.createSection(key);

            cfg.set(key + ".message", message.getMessage());
            cfg.set(key + ".permission", message.getPermission());
            cfg.set(key + ".weight", message.getWeight());
        }

        try {
            config.save(configFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
