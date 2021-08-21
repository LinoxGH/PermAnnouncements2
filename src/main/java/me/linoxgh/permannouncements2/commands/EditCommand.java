package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.PermAnnouncements2;
import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EditCommand extends Command {
    private final PermAnnouncements2 plugin;
    private final AnnouncementStorage announcements;

    EditCommand(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements) {
        this.plugin = plugin;
        this.announcements = announcements;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.edit"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        if (args.length < 4) return false;

        Message announcement = announcements.getMessage(args[1]);
        if (announcement == null) {
            sender.sendMessage("§4Could not find the announcement.");
            return true;
        }

        switch (args[2]) {
            case "announcement":
                StringBuilder builder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    builder.append(ChatColor.translateAlternateColorCodes('&', args[i]));
                    if (i != args.length - 1) builder.append(" ");
                }
                announcement.setMessage(builder.toString());
                plugin.reset();
                sender.sendMessage("§aSuccessfully changed the announcement.");
                return true;

            case "weight":
                try {
                    int weight = Integer.parseInt(args[3]);
                    announcement.setWeight(weight);
                    sender.sendMessage("§aSuccessfully changed the weight.");
                    plugin.reset();
                    return true;
                } catch (NumberFormatException ignored) {
                    sender.sendMessage("§4Please enter a valid weight.");
                    return true;
                }

            case "permission":
                announcement.setPermission(args[3].equalsIgnoreCase("null") ? null : args[3]);
                plugin.reset();
                sender.sendMessage("§aSuccessfully changed permission.");
                return true;

            default:
                return false;
        }
    }
}
