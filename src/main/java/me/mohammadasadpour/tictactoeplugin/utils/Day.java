package me.mohammadasadpour.tictactoeplugin.utils;

import org.bukkit.Server;
import org.bukkit.World;

import static org.bukkit.Bukkit.getServer;

public class Day {
    public static boolean isDay(World world) {
        Server server = getServer();
        long time = server.getWorld(world.getName()).getTime();

        return time > 0 && time < 12300;
    }
}
