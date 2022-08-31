package me.mohammadasadpour.tictactoeplugin.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;

import static me.mohammadasadpour.tictactoeplugin.game.Game.allMyLocations;
import static me.mohammadasadpour.tictactoeplugin.game.MyLocation.getLocList;

public class OnGameBoardBreakListener implements Listener {
    @EventHandler
    public void OnBlockBoardBreak(BlockBreakEvent event) {
        ArrayList<Location> allLocations = getLocList(allMyLocations);

        if (allLocations.contains(event.getBlock().getLocation()))
            event.setCancelled(true);
    }
}
