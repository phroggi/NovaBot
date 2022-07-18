package me.phrog.novabot.database;

import java.sql.Connection;
import java.util.UUID;

import me.phrog.novabot.NovaBot;

public abstract class Database {
    protected final NovaBot plugin;

    Connection connection;
    
    public Database(NovaBot plugin) {
        this.plugin = plugin;
    }

    public abstract boolean connect();

    public abstract void close();

    // public abstract void setupTable();

    public abstract String getDiscordId(UUID uuid);

    public abstract void linkPlayer(UUID uuid, String discordId);

    public abstract void unlinkPlayer(UUID uuid);
}