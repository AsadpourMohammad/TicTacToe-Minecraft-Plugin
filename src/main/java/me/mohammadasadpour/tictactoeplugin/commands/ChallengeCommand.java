package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Locale;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.addThePlayer;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;

public class ChallengeCommand implements CommandExecutor {
    public static MyPlayer myPlayer1;
    public static MyPlayer myPlayer2;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            addThePlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.getPlayer().getDisplayName().equals(player.getDisplayName()))
                    myPlayer1 = myPlayer;

            if (args.length != 1) {
                myPlayer1.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please use the 'Tic-Tac-Toe challenge' command correctly!");
                return false;
            } else if (myPlayer1.getGame() != null) {
                myPlayer1.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You cannot start a Tic-Tac-Toe game while playing one!");
                return true;
            } else if (myPlayer1.getPlayer().getDisplayName().toLowerCase(Locale.ROOT).equals(args[0].toLowerCase(Locale.ROOT))) {
                myPlayer1.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You cannot challenge yourself to a game of Tic-Tac-Toe!");
                return false;
            } else if (!isAboveGround()) {
                myPlayer1.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please chose some place else!");
                return true;
            } else {
                Player player2 = Bukkit.getServer().getPlayerExact(args[0]);

                if (player2 == null) {
                    myPlayer1.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "No such player was found in the server.");
                } else {
                    addThePlayer(player2);

                    for (MyPlayer myPlayer : myOnlinePlayers)
                        if (myPlayer.getPlayer().getDisplayName().equals(player2.getDisplayName()))
                            myPlayer2 = myPlayer;

                    myPlayer1.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You challenged " + myPlayer2.getPlayer().getDisplayName() + " to a game of Tic-Tac-Toe.\nWaiting for their response...");
                    myPlayer2.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + myPlayer1.getPlayer().getDisplayName() + " challenged you to a game of Tic-Tac-Toe.\nDo you accept '/accept' or deny '/deny'?");
                }
                return true;
            }
        } else {
            sender.sendMessage("Only a player can challenge someone to a game of Tic-Tac-Toe.");
            return false;
        }
    }

    public boolean isAboveGround() {
        ArrayList<Location> surroundingArea = new ArrayList<>();
        for (int x = -3 ; x < 4; x++)
            for (int y = 2; y < 6; y++)
                for (int z = -5; z < 2; z++)
                    surroundingArea.add(new Location(myPlayer1.getPlayer().getWorld(),
                            myPlayer1.getPlayer().getLocation().getBlockX() + x,
                            myPlayer1.getPlayer().getLocation().getBlockY() + y,
                            myPlayer1.getPlayer().getLocation().getBlockZ() + z));

        for(Location location : surroundingArea)
            if (!location.getBlock().isEmpty())
                return false;

        return true;
    }
}