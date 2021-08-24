package me.linoxgh.permannouncements2.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends Command {
    private final HashMap<UUID, List<String[]>> cachedHelp = new HashMap<>();
    private final HashMap<UUID, Long> cacheTimestamp = new HashMap<>();

    HelpCommand() { }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (sender instanceof Player) {
            UUID id = ((Player) sender).getUniqueId();
            if (cacheTimestamp.containsKey(id)) {
                if ((System.currentTimeMillis() - cacheTimestamp.get(id)) > 900_000) {
                    cachedHelp.remove(id);
                    cacheTimestamp.remove(id);
                }
            }
            cacheTimestamp.put(id, System.currentTimeMillis());
            if (cachedHelp.containsKey(id)) {
                return sendMessage(sender, cachedHelp.get(id), args);
            }
        }

        if (args.length > 1) return false;

        List<String> messages = new ArrayList<>();
        if (sender.hasPermission("permannouncements2.main")) {
            messages.add("§6/pa2 help §3[page]");
            messages.add("§e- §aShows the help pages.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.list")) {
            messages.add("§6/pa2 list");
            messages.add("§e- §aLists down all of the announcements.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.info")) {
            messages.add("§6/pa2 info");
            messages.add("§e- §aShows all config values.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.config")) {
            messages.add("§6/pa2 interval set §9<interval>");
            messages.add("§e- §aChanges the interval value.");
            messages.add("§e- §cInterval must be in seconds!");

            messages.add("§6/pa2 prefix set §9<prefix>");
            messages.add("§e- §aChanges the prefix used in announcements.");
            messages.add("§6/pa2 prefix enable true|false");
            messages.add("§e- §aEnables or disables the prefix.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.add")) {
            messages.add("§6/pa2 add §9<name> <weight> <announcement>");
            messages.add("§e- §aAdds a new announcement.");
            messages.add("§e- §aThe permission is optional and can be added using edit command.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.remove")) {
            messages.add("§6/pa2 remove §9<name>");
            messages.add("§e- §aRemoves the announcement.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.edit")) {
            messages.add("§6/pa2 edit §9<name> §6announcement|weight|permission §9<value>");
            messages.add("§e- §aChanges the announcement text, the weight or the permission.");
            messages.add("§e- §aAs permission is optional you can use §6null §ato remove permission.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.perm")) {
            messages.add("§6/pa2 perm §9<name> §6give|take §9<player>");
            messages.add("§e- §aGives or takes the announcement permission from the player.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.refresh")) {
            messages.add("§6/pa2 refresh");
            messages.add("§e- §aRefreshes the Announcement task.");
            messages.add("§e- §cDebugging tool.");
            messages.add(" ");
        }
        if (sender.hasPermission("permannouncements2.reload")) {
            messages.add("§6/pa2 reload");
            messages.add("§e- §aReloads the plugin config and announcements.");
            messages.add(" ");
        }

        List<String[]> pages = divide(messages);
        if (sender instanceof Player) {
            cachedHelp.put(((Player) sender).getUniqueId(), pages);
        }
        return sendMessage(sender, pages, args);
    }

    private @NotNull List<String[]> divide(@NotNull List<String> messages) {
        List<String[]> result = new ArrayList<>();

        int total = 0;
        int pageIndex = 0;
        List<List<String>> pages = new ArrayList<>();
        for (String message : messages) {
            if (total > 3) {
                pageIndex++;
                total = 0;
            }

            if (pages.size() < pageIndex + 1) pages.add(new ArrayList<>());
            pages.get(pageIndex).add(message);
            if (message.equals(" ")) total++;
        }

        for (int i = 0; i < pages.size(); i++) {
            List<String> page = pages.get(i);
            page.add(0, "§e.*.-----{ §3PermAnnouncements 2 §e}-----.*.");
            page.add("§e.*.-----_-----{ §3Help - " + (i + 1) + " §e}-----_-----.*.");
            result.add(page.toArray(new String[0]));
        }
        return result;
    }

    private boolean sendMessage(@NotNull CommandSender sender, @NotNull List<String[]> messages, @NotNull String[] args) {
        if (args.length == 0 || args.length == 1) {
            sender.sendMessage(messages.get(0));
            return true;
        } else {
            try {
                int page = Integer.parseInt(args[1]);
                if (page > messages.size()) {
                    return false;
                }
                if (page <= 0) {
                    sender.sendMessage(messages.get(0));
                    return true;
                }
                sender.sendMessage(messages.get(page - 1));
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
    }
}
