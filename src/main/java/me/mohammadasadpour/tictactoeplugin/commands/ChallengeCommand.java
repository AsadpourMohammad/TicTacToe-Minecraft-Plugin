package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import me.mohammadasadpour.tictactoeplugin.utils.SurroundingArea;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Locale;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.*;

public class ChallengeCommand implements CommandExecutor {
    public MyPlayer myPlayer1;
    public MyPlayer myPlayer2;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().getDisplayName().equals(player.getDisplayName()))
                    myPlayer1 = myPlayer;

            Player player1 = myPlayer1.get();

            if (args.length != 1) {
                player1.sendMessage(Color_R +
                        "Please use the 'Tic-Tac-Toe Challenge' command correctly!");
                return false;
            } else if (myPlayer1.getGame() != null) {
                player1.sendMessage(Color_R +
                        "You cannot start a Tic-Tac-Toe Game while playing one!");
            } else if (myPlayer1.getChallenged() != null || myPlayer1.getChallengedBy() != null) {
                player1.sendMessage(Color_R +
                        "You are already involved in a challenge.");
            } else if (player1.getDisplayName().toLowerCase(Locale.ROOT).equals(args[0].toLowerCase(Locale.ROOT))) {
                player1.sendMessage(Color_R +
                        "You cannot challenge yourself to a game of Tic-Tac-Toe!");
            } else if (!SurroundingArea.isTheSurroundingAreaEmpty(player)) {
                player1.sendMessage(Color_R +
                        "Please choose some place else and try again!");
            } else {
                Player player2 = Bukkit.getServer().getPlayerExact(args[0]);

                if (player2 == null) {
                    myPlayer1.get().sendMessage(Color_R +
                            "No such player was found in the server.");
                } else {
                    MyPlayer.addMyPlayer(player2);

                    for (MyPlayer myPlayer : myOnlinePlayers)
                        if (myPlayer.get().getDisplayName().equals(player2.getDisplayName()))
                            myPlayer2 = myPlayer;

                    if (myPlayer2.getChallenged() != null || myPlayer2.getChallengedBy() != null)
                        player1.sendMessage(Color_R +
                                player2.getDisplayName() + " is already involved in a challenge.");
                    else {
                        myPlayer1.setChallenged(myPlayer2);
                        myPlayer2.setChallengedBy(myPlayer1);

                        player1.sendMessage(Color_G +
                                "You challenged " + player2.getDisplayName() + " to a game of Tic-Tac-Toe.\n" +
                                                                                "Waiting for their response...");
                        player2.sendMessage(Color_G +
                                player1.getDisplayName() + " challenged you to a game of Tic-Tac-Toe.\n" +
                                                                                "Do you /accept or /deny?");
                        timeLimitForResponse();
                    }
                }
            }
        } else {
            sender.sendMessage("Only a player can challenge someone to a game of Tic-Tac-Toe.");
        }
        return true;
    }

    public void timeLimitForResponse() {
        new Thread(() -> {
            long beginningTime = System.nanoTime();
            long currentTime = System.nanoTime();

            int seconds = 20;

            myPlayer2.get().sendMessage("You have 20 seconds to respond.");

            while (true) {
                if (currentTime - beginningTime > 1000000000) {
                    //  Accepted                       Denied
                    if (myPlayer2.getGame() != null || myPlayer2.getChallengedBy() == null)
                        return;
                    else
                        myPlayer2.get().sendMessage(--seconds + "");

                    //  Reached time limit
                    if (seconds == 0)
                        break;

                    beginningTime = currentTime;
                }
                currentTime = System.nanoTime();
            }

            myPlayer1.setChallenged(null);
            myPlayer1.get().sendMessage(Color_R  +
                    myPlayer2.get().getDisplayName() + " didn't respond in time.");

            myPlayer2.setChallengedBy(null);
            myPlayer2.get().sendMessage(Color_R  +
                    "You failed to respond in time to " + myPlayer1.get().getDisplayName() + " 's challenge.");
        }).start();
    }
}