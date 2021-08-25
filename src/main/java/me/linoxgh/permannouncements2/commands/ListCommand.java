package me.linoxgh.permannouncements2.commands;

import java.util.ArrayList;
import java.util.List;

import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.Message;
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

        if (args.length > 1) return false;
        return sendMessage(sender, announcements.getMessages(), args);
    }

    private @NotNull List<Message> getPage(@NotNull List<Message> messages, int pageIndex) {
        List<Message> page = new ArrayList<>();
        for (int index = pageIndex * 20; index < pageIndex * 20 + 20; index++) {
            if (index >= messages.size()) break;
            page.add(messages.get(index));
        }
        return page;
    }

    private boolean sendMessage(@NotNull CommandSender sender, @NotNull List<Message> messages, @NotNull String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getPage(messages, 0), 1);
            return true;
        } else {
            try {
                int page = Integer.parseInt(args[1]);
                if (page > messages.size()) {
                    return false;
                }
                if (page <= 0) {
                    sendMessage(sender, getPage(messages, 0), 1);
                    return true;
                }
                sendMessage(sender, getPage(messages, page - 1), page);
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
    }

    private void sendMessage(@NotNull CommandSender sender, @NotNull List<Message> messages, int page) {
        sender.sendMessage("§e.*.-----{ §3PermAnnouncements 2 §e}-----.*.");
        sender.sendMessage("§9Name §e- §9Weight §e- §9Permission §e- §9Announcement");
        for (Message message : messages) {
            sender.sendMessage(Component.text(message.getName() + " §e- §f" + message.getWeight() + " §e- §f" + ((message.getPermission() == null) ? "N/A" : message.getPermission()) + " §e- §f")
                    .append(Component.text("§9[Announcement]").hoverEvent(Component.text(message.getMessage())))
            );
        }
        sender.sendMessage("§e.*.-----_-----{ §3List - " + page + " §e}-----_-----.*.");
    }
}
