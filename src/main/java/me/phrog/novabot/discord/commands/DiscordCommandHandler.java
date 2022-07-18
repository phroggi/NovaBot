package me.phrog.novabot.discord.commands;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import me.phrog.novabot.NovaBot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class DiscordCommandHandler extends ListenerAdapter {

    public enum COMMAND {
        LINK("link", new CommandDataImpl("link", "Allows user to link to their Minecraft Account")
            .addOptions(new OptionData(OptionType.STRING, "code", "The code generated", true))
        );

        @Getter
        public final String name;
        @Getter
        private final CommandData data;
    
        COMMAND(String name, CommandData data) {
            this.name = name;
            this.data = data;
        }
    }

    public DiscordCommandHandler() {
        NovaBot.getInstance().getDiscord().getJda().updateCommands().addCommands(Arrays.stream(COMMAND.values()).map(COMMAND::getData).toList()).queue();
        NovaBot.getInstance().getDiscord().getJda().addEventListener(this);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        switch(event.getName()) {
            case "link" -> new LinkCommand(event);
            case "ll" -> new UnlinkCommand(event);
        }
    }
}
