package me.linoxgh.permannouncements2.Commands;

import me.linoxgh.permannouncements2.Data.AnnouncementStorage;
import me.linoxgh.permannouncements2.Data.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AddCommand extends Command {
    private final AnnouncementStorage announcements;

    AddCommand(@NotNull AnnouncementStorage announcements) {
        this.announcements = announcements;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.add"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        if (args.length < 4) return false;

        int weight;
        try {
            weight = Integer.parseInt(args[2]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage("§4Please enter a valid weight.");
            return true;
        }

        Message test = announcements.getMessage(args[1]);
        if (test != null) {
            sender.sendMessage("§4An announcement with the same name already exists.");
            return true;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            builder.append(ChatColor.translateAlternateColorCodes('&', args[i]));
            if (i != args.length - 1) builder.append(" ");
        }

        Message message = new Message(args[1], builder.toString(), null, weight);
        announcements.addMessage(message);
        sender.sendMessage("§aSuccessfully added an announcement.");
        return true;
    }
}
