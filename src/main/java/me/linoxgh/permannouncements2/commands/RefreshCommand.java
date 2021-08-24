package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.PermAnnouncements2;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RefreshCommand extends Command {
    private final PermAnnouncements2 plugin;

    RefreshCommand(@NotNull PermAnnouncements2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.hasPermission("permannouncements2.refresh")) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        plugin.reset();
        sender.sendMessage("§aSuccessfully refreshed the Announcement task.");
        return true;
    }
}
