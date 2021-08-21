package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.PermAnnouncements2;
import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.Message;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveCommand extends Command {
    private final PermAnnouncements2 plugin;
    private final AnnouncementStorage announcements;

    RemoveCommand(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements) {
        this.plugin = plugin;
        this.announcements = announcements;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.remove"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        Message announcement = announcements.getMessage(args[1]);
        if (announcement == null) {
            sender.sendMessage("§4Could not find the announcement.");
            return true;
        }

        announcements.removeMessage(announcement);
        plugin.reset();
        sender.sendMessage("§aSuccessfully removed the announcement.");
        return true;
    }
}
