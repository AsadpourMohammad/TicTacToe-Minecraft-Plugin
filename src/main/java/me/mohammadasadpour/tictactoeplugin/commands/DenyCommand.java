package me.mohammadasadpour.tictactoeplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.player1;

public class DenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player2) {
            if (player2.equals(ChallengeCommand.player2)) {
                player1.sendMessage(player2.getDisplayName() + " has denied your Tic-Tac-Toe challenge.");
                player2.sendMessage("You have denied " + player1.getDisplayName() + " 's Tic-Tac-Toe challenge.");
                return true;
            } else {
                player2.sendMessage("You have not been challenged yet.");
                return true;
            }
        } else {
            sender.sendMessage("Only a player can deny a challenge.");
            return true;
        }
    }
}
