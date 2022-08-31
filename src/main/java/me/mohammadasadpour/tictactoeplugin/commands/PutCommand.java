package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.Game;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

import static me.mohammadasadpour.tictactoeplugin.commands.SaveCommand.writeGame;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.*;

public class PutCommand implements CommandExecutor {
    private MyPlayer myPlayer;
    private Game game;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for(MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

            game = myPlayer.getGame();

            if (args.length != 1) {
                player.sendMessage(Color_R + "Please use the 'Tic-Tac-Toe Put' command correctly!");
                return false;
            } else if (game == null) {
                player.sendMessage(Color_R + "No Tic-Tac-Toe game has begun yet!");
            }else if (!myPlayer.equals(game.getTurn())) {
                player.sendMessage(Color_R + "It's not your turn!");
            } else if (!NumberUtils.isCreatable(args[0])) {
                player.sendMessage(Color_R + "Please enter a valid number!");
            } else {
                if (game.put(Integer.parseInt(args[0]))) {
                    if (game.isOver())
                        announceEnd();
                    else {
                        String turnColor = game.getTurn().equals(game.getMyPlayer1()) ? Color_R : Color_B;

                        game.getTurn().get().sendMessage(turnColor + "Your turn to fill a spot.");
                    }
                } else {
                    player.sendMessage(Color_R + "Please choose a different spot.");
                }
            }
        } else {
            sender.sendMessage("Only a player can call the 'Tic-Tac-Toe Put' command.");
        }
        return true;
    }

    private void announceEnd() {
        Player player1 = game.getMyPlayer1().get();
        Player player2 = game.getMyPlayer2().get();

        if (game.getTurn() == null) {
            player1.sendTitle("GAME TIED!","huh...",2,70,2);
            player2.sendTitle("GAME TIED!","huh...",2,70,2);

            player1.sendMessage(Color_A + "The game ended with a tie.");
            player2.sendMessage(Color_A + "The game ended with a tie.");

            game.getMyPlayer1().anotherGameTied();
            game.getMyPlayer2().anotherGameTied();
        } else {
            Player winner = game.getTurn().get();
            Player loser = winner.equals(player1) ? player2 : player1;

            winner.sendMessage(Color_A + "You Have Won The Game!");
            winner.sendTitle("YOU WON!","YAY!",2,70,2);

            loser.sendTitle("YOU LOST!","SORRY...",2,70,2);
            loser.sendMessage(Color_DR + "You Lost The Game To " + winner.getDisplayName() + "!");

            if (game.getMyPlayer1().get().equals(winner)) {
                game.getMyPlayer1().anotherGameWon();
                game.getMyPlayer2().anotherGameLost();
            } else {
                game.getMyPlayer1().anotherGameLost();
                game.getMyPlayer2().anotherGameWon();
            }

            for (int i = 0; i < 13; i++)
                fireworks();
        }

        game.getMyPlayer1().showScoreboard(false);
        game.getMyPlayer1().get().performCommand("scoreboard");

        game.getMyPlayer2().showScoreboard(false);
        game.getMyPlayer2().get().performCommand("scoreboard");

        writeGame(game);
        game.endGame();
    }

    private void fireworks() {
        Random random = new Random();

        Firework firework = game.getTurn().get().getWorld().spawn(game.getTurn().get().getLocation(), Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        Builder builder = FireworkEffect.builder();
        Type[] type = Type.values();
        Color color = (game.getTurn() == game.getMyPlayer1()) ? Color.RED : Color.BLUE;

        meta.addEffect(builder
                .flicker(true)
                .withColor(color)
                .with(type[random.nextInt(type.length)])
                .with(type[random.nextInt(type.length)])
                .trail(true)
                .withFade(color)
                .build());
        meta.setPower(2);
        firework.setFireworkMeta(meta);
    }
}
