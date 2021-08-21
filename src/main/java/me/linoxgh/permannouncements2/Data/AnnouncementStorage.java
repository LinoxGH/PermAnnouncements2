package me.linoxgh.permannouncements2.Data;

import java.util.HashMap;
import java.util.HashSet;

import org.jetbrains.annotations.NotNull;

public class AnnouncementStorage {
    private final HashMap<HashSet<String>, MessageGroup> announcements;
    private final HashSet<Message> messages;

    public AnnouncementStorage() {
        announcements = new HashMap<>();
        messages = new HashSet<>();
    }

    public @NotNull HashSet<Message> getMessages() {
        return messages;
    }
    public void addMessage(@NotNull Message message) {
        messages.add(message);
    }
    public void removeMessage(@NotNull Message message) {
        messages.remove(message);
    }

}
