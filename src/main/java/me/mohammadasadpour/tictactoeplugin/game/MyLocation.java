package me.mohammadasadpour.tictactoeplugin.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class MyLocation implements Serializable {
    private final int x;
    private final int y;
    private final int z;
    private final UUID worldUUID;

    public MyLocation(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.worldUUID = location.getWorld().getUID();
    }

    public Location getLoc() {
        return new Location(Bukkit.getServer().getWorld(worldUUID), x, y, z);
    }

    public static ArrayList<Location> getLocList(ArrayList<MyLocation> myLocations) {
        ArrayList<Location> locations = new ArrayList<>();
        for (MyLocation myLocation : myLocations)
            locations.add(myLocation.getLoc());

        return locations;
    }
}
