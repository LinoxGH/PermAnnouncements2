package me.linoxgh.permannouncements2.Commands;

import me.linoxgh.permannouncements2.Data.ConfigStorage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ConfigCommand extends Command {
    private final ConfigStorage configs;

    ConfigCommand(@NotNull ConfigStorage configs) {
        this.configs = configs;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender.hasPermission("permannouncements2.config"))) {
            sender.sendMessage("§4You do not have enough permission to use this command.");
            return true;
        }

        if (args.length != 3) return false;

        switch (args[0]) {
            case "interval":
                if (args[1].equals("set")) {
                    try {
                        int interval = Integer.parseInt(args[2]);
                        configs.setInterval(interval);
                        sender.sendMessage("§aSuccessfully changed the interval.");
                        return true;
                    } catch (NumberFormatException ignored) {
                        sender.sendMessage("§4Please enter a valid interval.");
                        return true;
                    }
                }
                return false;

            case "prefix":
                if (args[1].equals("set")) {
                    configs.setPrefix(args[2]);
                    sender.sendMessage("§aSuccessfully changed the prefix.");
                    return true;

                } else if (args[1].equals("enable")) {
                    if (args[2].equalsIgnoreCase("true")) {
                        configs.setPrefixEnabled(true);
                        sender.sendMessage("§aSuccessfully enabled prefixes.");
                        return true;
                    } else if (args[2].equalsIgnoreCase("false")) {
                        configs.setPrefixEnabled(false);
                        sender.sendMessage("§aSuccessfully disabled prefixes.");
                        return true;
                    }
                    sender.sendMessage("§4Please enter a valid value (true or false).");
                    return true;
                }
                return false;

            default:
                return false;
        }
    }
}
