package me.phrog.novabot.commands;

import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.phrog.novabot.NovaBot;
import me.phrog.novabot.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class LinkCommand extends me.phrog.novabot.commands.CommandExecutor {

    public LinkCommand() {
        setCommand("link");
        setDesc("Link discord to minecraft");
        setPermission("novabot.linkcommand");
    }

    @Override
    public boolean execute(CommandSender paramCommandSender, String[] paramArrayOfStrings) {
        if(!(paramCommandSender instanceof Player player)) return false;
    
        if(paramArrayOfStrings[0].equalsIgnoreCase("link")) {
            final java.lang.Integer r = NovaBot.getInstance().getPlayerManager().generateLinkNumber(player.getUniqueId());

            if (r == null) {
                player.sendMessage(Utils.colorize(NovaBot.getInstance().getConfig().getString("messages.link.generated_already")));
                return false;
            } else {
                player.spigot().sendMessage(new ComponentBuilder("code: %code%".replace("%code%", r + ""))
                //.event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(r)))
                .event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, r + ""))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(Utils.colorize(NovaBot.getInstance().getConfig().getString("messages.link.copy_clipboard")))))
                .create()
                );
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString,
            String[] paramArrayOfStrings) {
            return Collections.emptyList();
    }

}
