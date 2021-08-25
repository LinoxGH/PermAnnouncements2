package me.linoxgh.permannouncements2.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnnouncementStorage {
    private final Map<Set<String>, MessageGroup> announcements;
    private final List<Message> messages;

    public AnnouncementStorage() {
        announcements = new HashMap<>();
        messages = new ArrayList<>();
    }

    public @NotNull Map<Set<String>, MessageGroup> getAnnouncements() {
        return announcements;
    }
    public @NotNull List<Message> getMessages() {
        return messages;
    }
    public void addMessage(@NotNull Set<String> permissions, @NotNull Set<Message> messages, @NotNull UUID player) {
        if (permissionGroupExists(permissions)) {
            announcements.get(permissions).addPlayer(player);
            return;
        }

        MessageGroup group = new MessageGroup();
        messages.forEach(message -> group.addMessage(message.getWeight(), message));
        group.addPlayer(player);
        announcements.put(permissions, group);
    }

    public void addMessage(@NotNull Message message) {
        messages.add(message);
    }
    public void removeMessage(@NotNull Message message) {
        messages.remove(message);
        for (MessageGroup value : announcements.values()) {
            value.removeMessage(message);
        }
    }

    public @Nullable Message getMessage(@NotNull String name) {
        for (Message message : messages) {
            if (message.getName().equals(name)) return message;
        }
        return null;
    }

    public void removePlayer(@NotNull UUID player) {
        announcements.values().forEach(announcement -> announcement.removePlayer(player));
    }

    private boolean permissionGroupExists(@NotNull Set<String> permissionGroup) {
        for (Set<String> strings : announcements.keySet()) {
            int similarity = 0;
            for (String string : strings) {
                for (String permission : permissionGroup) {
                    if (string.equals(permission)) similarity++;
                }
            }

            if (similarity == strings.size()) return true;
        }
        return false;
    }
}
