package me.linoxgh.permannouncements2.Commands;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.Message;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RemoveCommand extends Command {
    private final AnnouncementStorage announcements;

    RemoveCommand(@NotNull AnnouncementStorage announcements) {
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
        sender.sendMessage("§aSuccessfully removed the announcement.");
        return true;
    }
}
