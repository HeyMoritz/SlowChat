package fr.aquazus.slowchat.util;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import fr.aquazus.slowchat.Main;

public class SlowUtils
{
    public static boolean slowmode;
    public static int slowtime;
    public static ArrayList<String> slowplayers;
    
    public static void init() {
        slowmode = false;
        slowtime = 0;
        slowplayers = new ArrayList<String>();
        SlowUtils.slowplayers.clear();
    }
    
    public static void setSlow(final String name) {
        slowplayers.add(name);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
                slowplayers.remove(name);
            }
        }, 20L * slowtime);
    }
}
