package me.mohammadasadpour.tictactoeplugin.listeners;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.Color_A;

public class OnPlayerPickupItemListener implements Listener {
    MyPlayer myPlayer;

    @EventHandler
    public void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

//            if ( "player is not the winner" ) {
//                event.setCancelled(true);
//            }
        }
    }


    // The timer is in the wrong class
    public void timeLimitForResponse() {
        new Thread(() -> {
            long beginningTime = System.nanoTime();
            long currentTime = System.nanoTime();

            int seconds = 20;

            myPlayer.get().sendMessage("You have 20 seconds to respond.");

            while (true) {
                if (currentTime - beginningTime > 1000000000) {
                    myPlayer.get().sendMessage(--seconds + "");

                    //  Reached time limit
                    if (seconds == 0)
                        break;

                    beginningTime = currentTime;
                }
                currentTime = System.nanoTime();
            }

            myPlayer.get().sendMessage(Color_A + "And Done!\n" +
                    "I hope you were able to pick up as much as you wanted. You deserve them all!");
        }).start();
    }
}
