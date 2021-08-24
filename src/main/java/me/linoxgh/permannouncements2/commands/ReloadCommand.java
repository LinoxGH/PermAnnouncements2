package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.PermAnnouncements2;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends Command {
    private final PermAnnouncements2 plugin;

    ReloadCommand(@NotNull PermAnnouncements2 plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!sender.hasPermission("permannouncements2.reload")) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        plugin.save();
        plugin.load();
        plugin.reset();
        sender.sendMessage("§aSuccessfully reloaded the plugin.");
        return true;
    }
}
