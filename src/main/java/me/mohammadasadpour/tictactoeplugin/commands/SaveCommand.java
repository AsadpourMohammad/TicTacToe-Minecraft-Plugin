package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.TicTacToePlugin;
import me.mohammadasadpour.tictactoeplugin.game.Game;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;

public class SaveCommand implements CommandExecutor {
    private MyPlayer myPlayer;
    private Game game;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

            if (args.length == 0) {
                if (myPlayer.getGame() == null) {
                    player.sendMessage("You are not involved in a Tic-Tac-Toe game currently.");
                } else {
                    this.game = myPlayer.getGame();
                    writeGame(game);

                    game.getMyPlayer1().get().sendMessage("The game has been saved for later.\n" +
                            "You can continue the game later by loading the " + game.getGameName() + " file.");
                    game.getMyPlayer2().get().sendMessage("The game has been saved for later.\n" +
                            "You can continue the game later by loading the " + game.getGameName() + " file.");

                    game.endGame();
                }
            } else {
                player.sendMessage("Please use the 'Tic-Tac-Toe save' command correctly.");
            }
        }
        return true;
    }

    public static void writeGame(Game game) {
        String newPath = TicTacToePlugin.path + String.format("\\%s.txt", game.getGameName());

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(newPath))) {
            out.writeObject(game);
        } catch (IOException ignored) {}
    }
}
