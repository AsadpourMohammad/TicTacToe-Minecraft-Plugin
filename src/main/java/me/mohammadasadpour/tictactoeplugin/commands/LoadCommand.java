package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.TicTacToePlugin;
import me.mohammadasadpour.tictactoeplugin.game.Game;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import me.mohammadasadpour.tictactoeplugin.utils.SurroundingArea;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.*;

public class LoadCommand implements CommandExecutor {
    private MyPlayer myPlayer;
    private MyPlayer otherPlayer;
    private Game game;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

            if (args.length != 1) {
                player.sendMessage(Color_R + "Please use the 'Tic-Tac-Toe save' command correctly.");
                return false;
            } else {
                if (myPlayer.getGame() != null || myPlayer.getChallenged() != null) {
                    player.sendMessage(Color_R + "You are involved in a Tic-Tac-Toe game currently.");
                } else if (!args[0].contains(player.getDisplayName())) {
                    player.sendMessage(Color_R + "You can't load someone else's game!");
                } else {
                    if (!SurroundingArea.isTheSurroundingAreaEmpty(player)) {
                        player.sendMessage(Color_R + "Please choose some place else and try again!");
                    } else {
                        game = readGame(args[0]);
                        if (game == null) {
                            player.sendMessage(Color_R + "No such game was found.");
                        } else {
                            otherPlayer = game.getMyPlayer1().get().equals(player) ?
                                    game.getMyPlayer2() : game.getMyPlayer1();

                            MyPlayer.addMyPlayer(otherPlayer.get());

                            for (MyPlayer myPlayer : myOnlinePlayers)
                                if (myPlayer.equals(otherPlayer))
                                    this.otherPlayer = myPlayer;

                            if (!otherPlayer.get().isOnline()) {
                                player.sendMessage(Color_R +
                                        otherPlayer.get().getDisplayName() + " is not in the server at the moment.");
                            } else {
                                if (game.isOver()) {
                                    player.sendMessage("This game has already ended.");
                                } else {
                                    myPlayer.get().sendMessage(Color_G +
                                            "You have asked " + otherPlayer.get().getDisplayName() +
                                            " to continue your old Tic-Tac-Toe game.\n" +
                                            "Wait for their response.");

                                    otherPlayer.get().sendMessage(Color_G +
                                            myPlayer.get().getDisplayName() +
                                            " has asked you to continue your old Tic-Tac-Toe game.\n" +
                                            "Do you /accept or /deny?");

                                    myPlayer.setGame(game);
                                    otherPlayer.setContinueGameBy(myPlayer);

                                    otherPlayer.setGame(game);
                                    myPlayer.getGame().beginGame(myPlayer);

                                    //                                    timeLimitForResponse();
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public static Game readGame(String gameName) {
        String newPath = TicTacToePlugin.path + String.format("\\%s.txt", gameName);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(newPath))) {
            return (Game) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void timeLimitForResponse() {
        new Thread(() -> {
            long beginningTime = System.nanoTime();
            long currentTime = System.nanoTime();

            int seconds = 20;

            otherPlayer.get().sendMessage("You have 20 seconds to respond.");

            while (true) {
                if (currentTime - beginningTime > 1000000000) {
                    //  Accepted                       Denied
                    if (otherPlayer.getGame() != null || otherPlayer.askedForContinuingGameBy() == null) {
                        otherPlayer.get().sendMessage("here");
                        return;
                    } else
                        otherPlayer.get().sendMessage(--seconds + "");

                    //  Reached time limit
                    if (seconds == 0)
                        break;

                    beginningTime = currentTime;
                }
                currentTime = System.nanoTime();
            }

            myPlayer.setGame(null);
            myPlayer.get().sendMessage(Color_R +
                    otherPlayer.get().getDisplayName() + " didn't respond in time.");

            otherPlayer.setContinueGameBy(null);
            otherPlayer.get().sendMessage(Color_R +
                    "You failed to respond in time to " + myPlayer.get().getDisplayName() + " 's request.");
        }).start();
    }
}
