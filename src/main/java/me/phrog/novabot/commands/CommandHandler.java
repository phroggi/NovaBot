package me.phrog.novabot.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.phrog.novabot.NovaBot;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private Map<String, me.phrog.novabot.commands.CommandExecutor> commands = new HashMap<>();

    private static final List<String> subCommands = Arrays.asList("link");
    
    public CommandHandler() {
        this.commands.put("link", new LinkCommand());
    }

    public boolean onCommand(@NotNull CommandSender paramCommandSender, @NotNull Command paramCommand,
    @NotNull String paramString, @NotNull String[] paramArrayOfStrings) {
        if(paramCommand.getName().equalsIgnoreCase("discord")) {
            if(paramArrayOfStrings.length == 0) {
                paramCommandSender.sendMessage(NovaBot.getInstance().getConfig().getString("messages.link.discord_link"));
                return true;
            }

            if (paramArrayOfStrings[0] != null) {
                String string = paramArrayOfStrings[0].toLowerCase();
                if (this.commands.containsKey(string)) {
                    me.phrog.novabot.commands.CommandExecutor commandExecutor = (me.phrog.novabot.commands.CommandExecutor) this.commands.get(string);
                
                    commandExecutor.execute(paramCommandSender, paramArrayOfStrings);
                    return true;
                }
            }
            paramCommandSender.sendMessage(NovaBot.getInstance().getConfig().getString("messages.link.unknown_command"));
        }
        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender paramCommandSender, @NotNull Command paramCommand,
    @NotNull String paramString, @NotNull String[] paramArrayOfStrings) {
        if (paramArrayOfStrings.length == 1) {
            Collections.sort(subCommands);
            return subCommands;
        }
        return null;
    }
    
}
