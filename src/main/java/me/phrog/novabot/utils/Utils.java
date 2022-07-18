package me.phrog.novabot.utils;

import java.util.ArrayList;
import java.util.List;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

// import org.bukkit.Bukkit;

import net.md_5.bungee.api.ChatColor;


public class Utils {

    // private final static String HEX_PATTERN = "&#[0-9a-f]{5}";
    
    public static String colorize(String paramString) {
        return ChatColor.translateAlternateColorCodes('&', paramString);
    }

    public static String colorize(List<String> paramList) {
        List<String> colorizedList = new ArrayList<>();
        paramList.forEach(string -> colorizedList.add(colorize(string)));
        return String.join("\n", colorizedList);
    }

    // Not finished
    // public static String hexColorize(String paramString) {
    //     if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18") || Bukkit.getVersion().contains("1.19")) {
    //         final Pattern hex = Pattern.compile(HEX_PATTERN);
    //         final Matcher matcher = hex.matcher(paramString);
    //     }
    //     return paramString;
    // }

    public static Long toSeconds(Long paramLong) {
        return paramLong * 60L;
    }
}