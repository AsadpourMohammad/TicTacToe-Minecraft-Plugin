package me.mohammadasadpour.tictactoeplugin.listeners;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

import static me.mohammadasadpour.tictactoeplugin.game.Game.allMyLocations;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.Color_R_B;

public class OnStrangersWalkingInListener implements Listener {
    private static final ArrayList<Player> jumpingPlayers = new ArrayList<>();

    @EventHandler
    public void OnStrangersWalkingIn(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        MyPlayer.addMyPlayer(player);

        MyPlayer myPlayer1;
        for(MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.get().equals(event.getPlayer()))
                myPlayer1 = myPlayer;

        if ((allMyLocations.contains(player.getLocation()))) {
            player.setVelocity(player.getLocation().getDirection().multiply(-3).setY(1.5));
            player.sendMessage(Color_R_B + "Whoooooa back off!\n" +
                    "You can't enter a game arena of two other players!");
            jumpingPlayers.add(player);
        }
    }

    @EventHandler
    public void OFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && jumpingPlayers.contains(player)) {
                event.setCancelled(true);
                jumpingPlayers.remove(player);
            }
        }
    }
}
