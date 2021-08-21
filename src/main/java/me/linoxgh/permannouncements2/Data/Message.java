package me.linoxgh.permannouncements2.Data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Message {
    private final String name;
    private String message;
    private String permission;
    private int weight;

    public Message(@NotNull String name, @NotNull String message, @Nullable String permission, int weight) {
        this.name = name;
        this.message = message;
        this.permission = permission;
        this.weight = weight;
    }

    public @NotNull String getName() {
        return name;
    }
    public @NotNull String getMessage() {
        return message;
    }
    public @Nullable String getPermission() {
        return permission;
    }
    public int getWeight() {
        return weight;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }
    public void setPermission(@Nullable String permission) {
        this.permission = permission;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean equals(@NotNull String message) {
        return this.message.equals(message) || message.equals(name);
    }
}
