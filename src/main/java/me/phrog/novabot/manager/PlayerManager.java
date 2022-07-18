package me.phrog.novabot.manager;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

import lombok.Getter;
import me.phrog.novabot.NovaBot;
import me.phrog.novabot.utils.Utils;

public class PlayerManager {
    private final NovaBot plugin;

    public PlayerManager(NovaBot plugin) {
        this.plugin = plugin;
    }

    @Getter
    private final HashMap<Integer, UUID> codes = new HashMap<>();


    public static boolean isPlayerLinked(@Nonnull UUID pUUID) {
        return false;
    }

    public java.lang.Integer generateLinkNumber(@Nonnull UUID pUUID) {
        if(codes.containsValue(pUUID)) {
            return null;
        } else {
            final AtomicInteger r = new AtomicInteger(-1);
            if (r.get() != -1) return r.get();
            do {
                r.set(new Random().nextInt(99999999));
            } while (codes.containsKey(r.get()));
            codes.put(r.get(), pUUID);
            Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, () -> this.codes.remove(r.get()), Utils.toSeconds(this.plugin.getConfig().getLong("link.expiry_time")));
            return r.get();
        }
    }

    public boolean linkPlayer(@Nonnull UUID pUUID, @Nonnull String discordId) {
        this.plugin.getDatabase().linkPlayer(pUUID, discordId);
        return true;
    }

    public boolean unlinkPlayer(@Nonnull UUID pUUID) {
        this.plugin.getDatabase().unlinkPlayer(pUUID);
        return true;
    }

}
