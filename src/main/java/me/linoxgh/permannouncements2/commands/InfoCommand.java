package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.data.ConfigStorage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class InfoCommand extends Command {
    private final ConfigStorage configs;

    InfoCommand(@NotNull ConfigStorage configs) {
        this.configs = configs;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.info"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        sender.sendMessage("§e.*.-----_-----{ §3PermAnnouncements 2 §e}-----_-----.*.");
        sender.sendMessage("§e- §6Interval §e- §9" + configs.getInterval());
        sender.sendMessage("§e- §6Prefix-enabled §e- §9" + configs.isPrefixEnabled());
        sender.sendMessage("§e- §6Prefix-text §e- §f" + configs.getPrefix());
        return true;
    }
}
