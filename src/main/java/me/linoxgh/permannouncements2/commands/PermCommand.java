package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.Message;
import me.linoxgh.permannouncements2.PermAnnouncements2;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PermCommand extends Command {
    private final PermAnnouncements2 plugin;
    private final AnnouncementStorage announcements;

    PermCommand(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements) {
        this.plugin = plugin;
        this.announcements = announcements;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.perm"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        if (args.length != 4) return false;

        Message announcement = announcements.getMessage(args[1]);
        if (announcement == null) {
            sender.sendMessage("§4Could not find the announcement.");
            return true;
        }

        String permission = announcement.getPermission();
        if (permission == null) {
            sender.sendMessage("§4This announcement does not have a permission.");
            return true;
        }

        Player p = Bukkit.getPlayer(args[3]);
        if (p == null) {
            sender.sendMessage("§4Could not find the player.");
            return true;
        }

        if (args[2].equals("give")) {
            p.addAttachment(plugin, permission, true);
            sender.sendMessage("§aSuccessfully gave the permission.");
            return true;

        } else if (args[2].equals("take")) {
            p.addAttachment(plugin, permission, false);
            sender.sendMessage("§aSuccessfully took the permission.");
            return true;
        }
        return false;
    }
}
