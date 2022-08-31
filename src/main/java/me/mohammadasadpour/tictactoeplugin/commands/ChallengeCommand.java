package me.mohammadasadpour.tictactoeplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Locale;

public class ChallengeCommand implements CommandExecutor {
    public static Player player1;
    public static Player player2;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            player1 = (Player) sender;
            if (args.length != 1) {
                player1.sendMessage("Please use the 'Tic-Tac-Toe challenge' command correctly.");
                return false;
            } else if (player1.getDisplayName().toLowerCase(Locale.ROOT).equals(args[0].toLowerCase(Locale.ROOT))) {
                player1.sendMessage("You cannot challenge yourself to a game of Tic-Tac-Toe.");
                return false;
            } else {
                player2  = Bukkit.getServer().getPlayerExact(args[0]);

                if (player2 == null) {
                    player1.sendMessage("No such player was found in the server. Suck it.");
                } else {
                    player1.sendMessage("You challenged " + player2.getDisplayName() + " to a game of Tic-Tac-Toe.\nWaiting for their response...");
                    player2.sendMessage(player1.getDisplayName() + " challenged you to a game of Tic-Tac-Toe.\nDo you accept '/accept' or deny '/deny'?");
                }

                return true;
            }
        } else {
            sender.sendMessage("Only a player can challenge someone to a game of Tic-Tac-Toe.");
            return false;
        }
    }
}