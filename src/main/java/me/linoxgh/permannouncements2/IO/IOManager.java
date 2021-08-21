package me.linoxgh.permannouncements2.IO;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.ConfigStorage;
import me.linoxgh.permannouncements2.Data.Message;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class IOManager {
    private final ConfigStorage configStorage;
    private final AnnouncementStorage announcementStorage;
    private final FileConfiguration config;

    public IOManager(@NotNull ConfigStorage configStorage, @NotNull AnnouncementStorage announcementStorage, @NotNull FileConfiguration config) {
        this.configStorage = configStorage;
        this.announcementStorage = announcementStorage;
        this.config = config;
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

            announcementStorage.addMessage(new Message(key, message, permission, weight));
        }
        return true;
    }

    public void saveConfig() {
        config.set("interval", configStorage.getInterval());
        config.set("prefix.enabled", configStorage.isPrefixEnabled());
        config.set("prefix.text", configStorage.getPrefix());
    }

    public void saveAnnouncements() {

    }
}
