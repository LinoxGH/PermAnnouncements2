package me.linoxgh.permannouncements2.commands;

import me.linoxgh.permannouncements2.data.AnnouncementStorage;
import me.linoxgh.permannouncements2.data.ConfigStorage;
import me.linoxgh.permannouncements2.PermAnnouncements2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    private final HelpCommand help;
    private final ListCommand list;
    private final InfoCommand info;
    private final ConfigCommand config;
    private final AddCommand add;
    private final RemoveCommand remove;
    private final EditCommand edit;
    private final PermCommand perm;
    private final RefreshCommand refresh;
    private final ReloadCommand reload;

    public MainCommand(@NotNull PermAnnouncements2 plugin, @NotNull AnnouncementStorage announcements, @NotNull ConfigStorage configs) {
        this.help = new HelpCommand();
        this.list = new ListCommand(announcements);
        this.info = new InfoCommand(configs);
        this.config = new ConfigCommand(plugin, configs);
        this.add = new AddCommand(plugin, announcements);
        this.remove = new RemoveCommand(plugin, announcements);
        this.edit = new EditCommand(plugin, announcements);
        this.perm = new PermCommand(plugin, announcements);
        this.refresh = new RefreshCommand(plugin);
        this.reload = new ReloadCommand(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            return help.execute(sender, args);
        }

        switch (args[0]) {
            case "help":
                return help.execute(sender, args);

            case "list":
                return list.execute(sender, args);

            case "info":
                return info.execute(sender, args);

            case "interval":
            case "prefix":
                return config.execute(sender, args);

            case "add":
                return add.execute(sender, args);

            case "remove":
                return remove.execute(sender, args);

            case "edit":
                return edit.execute(sender, args);

            case "perm":
                return perm.execute(sender, args);

            case "refresh":
                return refresh.execute(sender, args);

            case "reload":
                return reload.execute(sender, args);

            default:
                return false;
        }
    }
}
