package me.phrog.novabot.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.phrog.novabot.NovaBot;

public class QuitEvent implements Listener {
    private NovaBot plugin;

    public QuitEvent(NovaBot plugin) {
        this.plugin = plugin;

        getInstance().getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoinEvent(PlayerQuitEvent e) {

    }

    private NovaBot getInstance() {
        return plugin;
    }
}
