package me.mohammadasadpour.tictactoeplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static me.mohammadasadpour.tictactoeplugin.game.Game.game;

public class OnGameBoardBreakListener implements Listener {
    @EventHandler
    public void OnBlockBoardBreak(BlockBreakEvent event) {
        if (game.getBlockBoard().contains(event.getBlock().getLocation())
                || game.getGroundBlock().contains(event.getBlock().getLocation()))
            event.setCancelled(true);
    }
}
