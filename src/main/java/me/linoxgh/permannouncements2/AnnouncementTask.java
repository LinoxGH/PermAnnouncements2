package me.linoxgh.permannouncements2;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.ConfigStorage;
import me.linoxgh.permannouncements2.Data.MessageGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AnnouncementTask implements Runnable {
    private final AnnouncementStorage announcements;
    private final ConfigStorage configs;
    private int task;

    AnnouncementTask(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements, @NotNull ConfigStorage configs) {
        this.announcements = announcements;
        this.configs = configs;

        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, configs.getInterval() * 20L, configs.getInterval() * 20L);
    }

    @Override
    public void run() {
        boolean prefixEnabled = configs.isPrefixEnabled();
        String prefix = configs.getPrefix();

        for (MessageGroup group : announcements.getAnnouncements().values()) {
            for (UUID player : group.getPlayers()) {
                Player p = Bukkit.getPlayer(player);
                if (p == null) {
                    group.removePlayer(player);
                    continue;
                }
                p.sendMessage((prefixEnabled ? prefix + " " : "") + group.getRandomMessage());
            }
        }
    }

    public void refresh(@NotNull PermAnnouncements2 plugin) {
        Bukkit.getScheduler().cancelTask(task);
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this, configs.getInterval() * 20L, configs.getInterval() * 20L);
    }
}
