package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
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

import static me.mohammadasadpour.tictactoeplugin.game.Game.game;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.addThePlayer;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;

public class PutCommand implements CommandExecutor {
    private MyPlayer myPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            addThePlayer(player);

            for(MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.getPlayer().equals(player))
                    this.myPlayer = myPlayer;

            if (game == null) {
                myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "No Tic-Tac-Toe game has begun yet!");
                return true;
            } else if (game.isOver()) {
                myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Game has ended!");
                return true;
            } else if (!myPlayer.equals(game.getTurn())) {
                myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "It's not your turn!");
                return true;
            } else if (args.length != 1) {
                myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please use the 'Tic-Tac-Toe put' command correctly!");
                return false;
            } else if (!NumberUtils.isCreatable(args[0])) {
                myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please enter a valid number!");
                return false;
            } else {
                if (game.put(Integer.parseInt(args[0]))) {
                    if (game.isOver()) {
                        announceEnd();
                    } else {
                        game.getTurn().getPlayer().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Your turn to fill a spot.");
                    }
                } else {
                    myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please choose a different spot.");
                }
                return true;
            }
        } else {
            sender.sendMessage("Only a player can do the 'Tic-Tac-Toe put' action.");
            return true;
        }
    }

    private void announceEnd() {
        if (game.getWinner().equals("TIE")) {
            game.getMyPlayer1().getPlayer().sendTitle("GAME TIED!","GAME TIED!",2,70,2);
            game.getMyPlayer2().getPlayer().sendTitle("GAME TIED!","GAME TIED!",2,70,2);
            game.getMyPlayer1().getPlayer().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "The game ended with a tie.");
            game.getMyPlayer1().anotherGameTied();
            game.getMyPlayer2().getPlayer().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "The game ended with a tie.");
            game.getMyPlayer2().anotherGameTied();
        } else {
            game.getTurn().getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You have won the game!");
            game.getMyPlayer2().getPlayer().sendTitle("YOU WON!","YAY!",2,70,2);

            for (int i = 0; i < 13; i++)
                fireworks();

            if (game.getTurn().equals(game.getMyPlayer1())) {
                game.getMyPlayer2().getPlayer().sendTitle("YOU LOST!","SORRY...",2,70,2);

                game.getMyPlayer2().getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You lost the game to " + game.getMyPlayer1().getPlayer().getDisplayName() + "!");
                game.getMyPlayer1().anotherGameWon();
                game.getMyPlayer2().anotherGameLost();
            } else if (game.getTurn().equals(game.getMyPlayer2())) {
                game.getMyPlayer1().getPlayer().sendTitle("YOU LOST!","SORRY...",2,70,2);
                game.getMyPlayer1().getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "You lost the game to " + game.getMyPlayer2().getPlayer().getDisplayName() + "!");
                game.getMyPlayer1().anotherGameLost();
                game.getMyPlayer2().anotherGameWon();
            }
        }

        game.getMyPlayer1().getPlayer().performCommand("scoreboard");
        game.getMyPlayer1().getPlayer().performCommand("scoreboard");

        game.getMyPlayer2().getPlayer().performCommand("scoreboard");
        game.getMyPlayer2().getPlayer().performCommand("scoreboard");

        game = null;
    }

    private void fireworks() {
        Random random = new Random();

        Firework firework = game.getTurn().getPlayer().getWorld().spawn(game.getTurn().getPlayer().getLocation(), Firework.class);
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
