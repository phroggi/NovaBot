package me.phrog.novabot;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import me.phrog.novabot.commands.CommandHandler;
import me.phrog.novabot.database.Database;
import me.phrog.novabot.database.DatabaseManager;
import me.phrog.novabot.discord.Discord;
import me.phrog.novabot.listeners.JoinEvent;
import me.phrog.novabot.listeners.QuitEvent;
import me.phrog.novabot.manager.PlayerManager;

public final class NovaBot extends JavaPlugin {

    @Getter
    public static NovaBot instance;

    @Getter
    private CommandHandler CommandHandler;

    @Getter
    private Database database;

    @Getter
    private Discord discord;

    @Getter
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        long l = System.currentTimeMillis();
        getLogger().info("[NovaBot] Initializing Plugin.");
        getLogger().info("[NovaBot] Registering Events...");

        new JoinEvent(this);
        new QuitEvent(this);
        
        getCommand("discord").setExecutor((org.bukkit.command.CommandExecutor) new CommandHandler());
        saveDefaultConfig();

        this.discord = new Discord();
        this.playerManager = new PlayerManager(this);
        this.database = new DatabaseManager(this);
        if (this.database.connect()) getLogger().info(String.format("Successfully connected to database!"));
        else this.getLogger().info("Failed to connect to database!");

        getLogger().info("[NovaBot] Discord...");
        // Run Asynchronously 
        Bukkit.getScheduler().runTaskAsynchronously(this, () ->
            {
                try {
                    this.discord.setupBot(getConfig().getString("bot.token"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        );
        // try {
        //     this.discord.setupBot(getConfig().getString("bot.token"));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        

        getLogger().info("\n[NovaBot] Initialized (" + String.valueOf(System.currentTimeMillis() - l) + "ms)\n");

    }

    @Override
    public void onDisable() {
       this.database.close();
       this.discord.getJda().shutdown();
    }

}
