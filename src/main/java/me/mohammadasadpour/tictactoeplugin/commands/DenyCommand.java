package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.myPlayer1;
import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.myPlayer2;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.addThePlayer;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;

public class DenyCommand implements CommandExecutor {
    private MyPlayer myPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            addThePlayer(player);

            for(MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.getPlayer().equals(player))
                    this.myPlayer = myPlayer;

            if (args.length != 0) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Please use the 'Tic-Tac-Toe deny' command correctly!");
                return false;
            } else if (myPlayer.getGame() != null) {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You cannot deny a Tic-Tac-Toe game while playing one!");
            } else if (player.equals(ChallengeCommand.myPlayer2.getPlayer())) {
                myPlayer1.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + myPlayer2.getPlayer().getDisplayName() + " has denied your Tic-Tac-Toe challenge.");
                myPlayer2.getPlayer().sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "You have denied " + myPlayer1.getPlayer().getDisplayName() + " 's Tic-Tac-Toe challenge.");
            } else {
                myPlayer.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have not been challenged yet!");
            }
        } else {
            sender.sendMessage("Only a player can deny a Tic-Tac-Toe challenge.");
        }
        return true;
    }
}
