package me.phrog.novabot.discord;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.jetbrains.annotations.Nullable;

import me.phrog.novabot.NovaBot;
import me.phrog.novabot.discord.commands.DiscordCommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Discord {
    
    private static final List<GatewayIntent> gatewayIntents = new ArrayList<>();

    private JDA jda = null;

    public void setupBot(String token) throws Exception {
        setupGatewayIntents();

        final JDABuilder b = JDABuilder.createDefault(token);
        b.setAutoReconnect(true);
        b.enableIntents(gatewayIntents);

        try {
            jda = b.build();
            jda.awaitReady();
        } catch (LoginException e) {
            NovaBot.getInstance().getLogger().severe("Invalid token, please check config file and enter the right token.");
            return;
        } catch (InterruptedException | IllegalStateException e) {
            NovaBot.getInstance().getLogger().severe("Something happened... Shutting down discord bot.");
            jda.shutdown();
            throw e;
        }

        jda.addEventListener(new DiscordCommandHandler());
    }

    @Nullable
    public JDA getJda() {
        return jda;
    }
    

    public static void setupGatewayIntents() {
        gatewayIntents.add(GatewayIntent.GUILD_MEMBERS);
        gatewayIntents.add(GatewayIntent.GUILD_EMOJIS_AND_STICKERS);
        gatewayIntents.add(GatewayIntent.GUILD_INVITES);
        gatewayIntents.add(GatewayIntent.GUILD_PRESENCES);
        gatewayIntents.add(GatewayIntent.GUILD_MESSAGES);
        gatewayIntents.add(GatewayIntent.GUILD_MESSAGE_REACTIONS);
        gatewayIntents.add(GatewayIntent.GUILD_MESSAGE_TYPING);
        gatewayIntents.add(GatewayIntent.DIRECT_MESSAGES);
        gatewayIntents.add(GatewayIntent.DIRECT_MESSAGE_REACTIONS);
        gatewayIntents.add(GatewayIntent.DIRECT_MESSAGE_TYPING);
    }
}
