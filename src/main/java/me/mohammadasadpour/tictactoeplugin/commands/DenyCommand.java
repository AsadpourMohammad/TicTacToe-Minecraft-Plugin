package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.*;

public class DenyCommand implements CommandExecutor {
    private MyPlayer myPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for(MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

            Player denyingPlayer = myPlayer.get();

            if (args.length != 0) {
                denyingPlayer.sendMessage(Color_R +
                        "Please use the 'Tic-Tac-Toe Deny' command correctly!");
                return false;
            } else if (myPlayer.getGame() != null) {
                denyingPlayer.sendMessage(Color_R +
                        "You cannot deny a Tic-Tac-Toe Game while playing one!");
            } else if (myPlayer.getChallenged() != null) {
                denyingPlayer.sendMessage(Color_R +
                        "You have already challenged someone yourself.");
            } else if (myPlayer.askedForContinuingGameBy() != null) {
                Player requestingPlayer = myPlayer.askedForContinuingGameBy().get();

                requestingPlayer.sendMessage(Color_DR +
                        denyingPlayer.getDisplayName() + " has denied your continue game request.");
                denyingPlayer.sendMessage(Color_DR +
                        "You have denied " + requestingPlayer.getDisplayName() + " 's continue game request.");

                myPlayer.askedForContinuingGameBy().setGame(null);
                myPlayer.setContinueGameBy(null);
            } else if (myPlayer.getChallengedBy() == null) {
                denyingPlayer.sendMessage(Color_R +
                        "You have not been challenged yet!");
            } else {
                Player challengingPlayer = myPlayer.getChallengedBy().get();

                challengingPlayer.sendMessage(Color_DR +
                        denyingPlayer.getDisplayName() + " has denied your Tic-Tac-Toe challenge.");
                denyingPlayer.sendMessage(Color_DR +
                        "You have denied " + challengingPlayer.getDisplayName() + " 's Tic-Tac-Toe challenge.");

                myPlayer.getChallengedBy().setChallenged(null);
                myPlayer.setChallengedBy(null);
            }
        } else {
            sender.sendMessage("Only a player can deny a Tic-Tac-Toe challenge.");
        }
        return true;
    }
}
