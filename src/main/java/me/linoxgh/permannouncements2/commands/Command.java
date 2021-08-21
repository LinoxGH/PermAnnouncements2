package me.linoxgh.permannouncements2.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class Command {

    public abstract boolean execute(@NotNull CommandSender sender, @NotNull String[] args);

}
