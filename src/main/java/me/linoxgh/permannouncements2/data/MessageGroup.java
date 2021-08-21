package me.linoxgh.permannouncements2.data;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MessageGroup {
    private TreeMap<Integer, Message> messages;
    private final HashSet<UUID> players;

    public MessageGroup() {
        messages = new TreeMap<>();
        players = new HashSet<>();
    }

    public @NotNull HashSet<UUID> getPlayers() {
        return players;
    }

    public @Nullable String getRandomMessage() {
        if (messages.isEmpty()) return null;
        int random = ThreadLocalRandom.current().nextInt(messages.lastKey());
        return messages.higherEntry(random).getValue().getMessage();
    }

    public void addMessage(int weight, @NotNull Message message) {
        messages.put((messages.isEmpty() ? 0 : messages.lastKey()) + weight, message);
    }
    public void removeMessage(@NotNull Message message) {
        TreeMap<Integer, Message> newMap = new TreeMap<>();

        int key = 0;
        for (Message value : messages.values()) {
            if (value.equals(message)) continue;
            key += value.getWeight();
            newMap.put(key, value);
        }

        this.messages = newMap;
    }

    public void addPlayer(@NotNull UUID player) {
        players.add(player);
    }
    public void removePlayer(@NotNull UUID player) {
        players.remove(player);
    }
}
