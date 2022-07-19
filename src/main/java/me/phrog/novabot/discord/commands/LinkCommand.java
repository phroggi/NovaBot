package me.phrog.novabot.discord.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import me.phrog.novabot.NovaBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class LinkCommand {

    public LinkCommand(@NotNull SlashCommandInteractionEvent event) {
        Member m = event.getMember();
        // NovaBot.getInstance().getLogger().info(" this is the member" + m);

        if(m == null) m = event.getGuild().retrieveMember(event.getUser()).complete();

        if(m != null) {
            final OptionMapping code = event.getOption("code");

            try{
                if(code != null) {
                    int iCode = Integer.parseInt(code.getAsString());
    
                    if(NovaBot.getInstance().getPlayerManager().getCodes().containsKey(iCode)) {
                        final boolean linked = NovaBot.getInstance().getPlayerManager().linkPlayer(NovaBot.getInstance().getPlayerManager().getCodes().get(iCode), event.getUser().getId());
                        if(linked) {
                            event.reply("Linked.").queue();
                            NovaBot.getInstance().getPlayerManager().getCodes().remove(iCode);                            
                            OfflinePlayer player = Bukkit.getOfflinePlayer(NovaBot.getInstance().getPlayerManager().getCodes().get(iCode));
                            if(player.isOnline()) {
                                player.getPlayer().sendMessage("Link Successful!");
                            }
                            if(NovaBot.getInstance().getConfig().getLong("link.linkRoleID") == 0) return;
                            final Guild guild = NovaBot.getInstance().getDiscord().getJda().getGuildById(NovaBot.getInstance().getConfig().getLong("link.guildID"));
                            final Role roleId = guild.getRoleById(NovaBot.getInstance().getConfig().getLong("link.linkRoleID"));
                            final Member member = guild.retrieveMemberById(event.getUser().getId()).complete();
                            if (roleId != null && !member.getRoles().contains(roleId))
                                guild.addRoleToMember(member, roleId).queue();
                            return;    
                        }
                        else 
                            event.reply("not linked").queue();
                    } else
                        event.reply("Invalid code!").queue();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }   
}
