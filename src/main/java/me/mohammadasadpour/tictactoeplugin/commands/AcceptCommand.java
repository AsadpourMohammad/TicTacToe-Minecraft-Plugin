package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.Game;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.myPlayer1;
import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.myPlayer2;
import static me.mohammadasadpour.tictactoeplugin.game.Game.game;

public class AcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player
        ) {
            if (args.length != 0) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please use the 'Tic-Tac-Toe accept' command correctly!");
                return false;
            } else if (game != null && (game.getMyPlayer1().getPlayer().equals(player) || game.getMyPlayer2().getPlayer().equals(player))) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You cannot accept a Tic-Tac-Toe game while playing one!");
            } else if (player.equals(ChallengeCommand.myPlayer2.getPlayer())) {
                myPlayer1.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getDisplayName() + " has accepted your Tic-Tac-Toe challenge.\n" +
                        "They will now be teleported to you.");
                myPlayer2.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You have accepted " + myPlayer1.getPlayer().getDisplayName() + "'s Tic-Tac-Toe challenge.\n" +
                        "You will now be teleported to them.");

                game = new Game(myPlayer1, myPlayer2);
            } else {
                myPlayer2.getPlayer().sendMessage(ChatColor.RED + "You have not been challenged yet!");
            }
        } else {
            sender.sendMessage("Only a player can accept a challenge.");
        }
        return true;
    }
}