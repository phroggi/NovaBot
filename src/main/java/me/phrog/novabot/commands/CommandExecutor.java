package me.phrog.novabot.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import lombok.Setter;

public abstract class CommandExecutor {
    @Getter @Setter
    private @NotNull String command;
    @Getter @Setter
    private @NotNull String desc;
    @Getter @Setter
    private @NotNull String permission;

    public abstract boolean execute(CommandSender paramCommandSender, String[] paramArrayOfStrings);

    public abstract List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfStrings);
}