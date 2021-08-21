package me.linoxgh.permannouncements2;

import java.util.HashSet;
import java.util.Set;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.Message;
import me.linoxgh.permannouncements2.Data.MessageGroup;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class Listeners implements Listener {
    private final AnnouncementStorage announcements;

    public Listeners(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.announcements = announcements;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Set<String> permissionGroup = new HashSet<>();
        Set<Message> messages = new HashSet<>();
        Player p = e.getPlayer();

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

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        announcements.removePlayer(e.getPlayer().getUniqueId());
    }
}
