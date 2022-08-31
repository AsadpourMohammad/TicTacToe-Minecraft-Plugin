package me.mohammadasadpour.tictactoeplugin.listeners;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.addThePlayer;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;

public class OnPutListener implements Listener {
    private MyPlayer myPlayer;

    @EventHandler
    public void onPut(PlayerInteractEvent event) {
        addThePlayer(event.getPlayer());

        for(MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.getPlayer().equals(event.getPlayer()))
                this.myPlayer = myPlayer;

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            if (myPlayer.getGame() != null && myPlayer.getGame().getBlockBoard().contains(event.getClickedBlock().getLocation())) {
                Material material = event.getPlayer().getTargetBlock(null, 20).getType();
                if (material.equals(Material.WHITE_WOOL)) {
                    int pos = myPlayer.getGame().getBlockBoard().indexOf(event.getPlayer().getTargetBlockExact(20).getLocation()) + 1;
                    event.getPlayer().performCommand("put " + pos);
                }
            }
        }
    }
}
