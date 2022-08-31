package me.mohammadasadpour.tictactoeplugin.listeners;

import me.mohammadasadpour.tictactoeplugin.game.Game;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class OnPutListener implements Listener {
    @EventHandler
    public void onPut(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            Material material = event.getPlayer().getTargetBlock(null, 20).getType();
            if (material.equals(Material.WHITE_WOOL)) {
                int pos = Game.game.getBlockBoard().indexOf(event.getPlayer().getTargetBlockExact(20).getLocation()) + 1;
                event.getPlayer().performCommand("put " + pos);
            }
        }
    }
}
