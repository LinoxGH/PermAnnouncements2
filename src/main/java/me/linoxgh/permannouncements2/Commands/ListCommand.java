package me.linoxgh.permannouncements2.Commands;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.Message;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ListCommand extends Command {
    private final AnnouncementStorage announcements;

    ListCommand(@NotNull AnnouncementStorage announcements) {
        this.announcements = announcements;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.list"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        sender.sendMessage("§e.*.-----_-----{ §3PermAnnouncements 2 §e}-----_-----.*.");
        sender.sendMessage("§9Name §e- §9Weight §e- §9Permission §e- §9Announcement");
        for (Message message : announcements.getMessages()) {
            sender.sendMessage(Component.text(message.getName() + " §e- §f" + message.getWeight() + " §e- §f" + ((message.getPermission() == null) ? "N/A" : message.getPermission() + " §e- §f"))
                    .append(Component.text("§9[Announcement]").hoverEvent(Component.text(message.getMessage())))
            );
        }
        return false;
    }
}
