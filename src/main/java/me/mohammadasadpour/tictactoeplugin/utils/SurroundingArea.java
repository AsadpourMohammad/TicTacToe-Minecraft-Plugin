package me.mohammadasadpour.tictactoeplugin.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SurroundingArea {
    public static boolean isTheSurroundingAreaEmpty(Player player) {
        ArrayList<Location> surroundingArea = new ArrayList <>();
        for (int x = -3; x < 4; x++)
            for (int y = 2; y < 6; y++)
                for (int z = -5; z < 2; z++)
                    surroundingArea.add(new Location(player.getWorld(),
                            player.getLocation().getBlockX() + x,
                            player.getLocation().getBlockY() + y,
                            player.getLocation().getBlockZ() + z));

        for (Location location : surroundingArea)
            if (!location.getBlock().isEmpty())
                return false;

        return true;
    }
}
