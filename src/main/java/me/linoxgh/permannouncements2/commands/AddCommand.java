package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.PermAnnouncements2;
import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AddCommand extends Command {
    private final PermAnnouncements2 plugin;
    private final AnnouncementStorage announcements;

    AddCommand(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements) {
        this.plugin = plugin;
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
            builder.append(args[i]);
            if (i != args.length - 1) builder.append(" ");
        }

        Message message = new Message(args[1], ChatColor.translateAlternateColorCodes('&', builder.toString()), null, weight);
        announcements.addMessage(message);
        plugin.reset();
        sender.sendMessage("§aSuccessfully added an announcement.");
        return true;
    }
}
