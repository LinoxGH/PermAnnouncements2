package me.linoxgh.permannouncements2.commands;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<Set<Message>> pages = divide(announcements.getMessages());
        return sendMessage(sender, pages, args);
    }

    private @NotNull List<Set<Message>> divide(@NotNull Set<Message> messages) {
        List<Set<Message>> pages = new ArrayList<>();
        Set<Message> page = new HashSet<>();
        int counter = 0;
        for (Message message : messages) {
            page.add(message);
            counter++;

            if (counter == 19) {
                pages.add(page);
                page = new HashSet<>();
                counter = 0;
            }
        }
        return pages;
    }

    private boolean sendMessage(@NotNull CommandSender sender, @NotNull List<Set<Message>> messages, @NotNull String[] args) {
        if (args.length == 0 || args.length == 1) {
            sendMessage(sender, messages.get(0), 1);
            return true;
        } else {
            try {
                int page = Integer.parseInt(args[1]);
                if (page > messages.size()) {
                    return false;
                }
                if (page <= 0) {
                    sendMessage(sender, messages.get(0), 1);
                    return true;
                }
                sendMessage(sender, messages.get(page - 1), page);
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
    }

    private void sendMessage(@NotNull CommandSender sender, @NotNull Set<Message> messages, int page) {
        sender.sendMessage("§e.*.-----_-----{ §3PermAnnouncements 2 §e}-----_-----.*.");
        sender.sendMessage("§9Name §e- §9Weight §e- §9Permission §e- §9Announcement");
        for (Message message : messages) {
            sender.sendMessage(Component.text(message.getName() + " §e- §f" + message.getWeight() + " §e- §f" + ((message.getPermission() == null) ? "N/A" : message.getPermission()) + " §e- §f")
                    .append(Component.text("§9[Announcement]").hoverEvent(Component.text(message.getMessage())))
            );
        }
        sender.sendMessage("§e.*.-----{ §3List - " + page + " §e}-----.*.");
    }
}
