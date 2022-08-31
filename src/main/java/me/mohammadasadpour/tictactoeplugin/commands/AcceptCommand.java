package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.Game;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.*;

public class AcceptCommand implements CommandExecutor {
    private MyPlayer myPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

            Player acceptingPlayer = myPlayer.get();

            if (args.length != 0) {
                acceptingPlayer.sendMessage(Color_R +
                        "Please use the 'Tic-Tac-Toe Accept' command correctly!");
                return false;
            } else if (myPlayer.getGame() != null) {
                acceptingPlayer.sendMessage(Color_R +
                        "You cannot accept a Tic-Tac-Toe Game while playing one!");
            } else if (myPlayer.getChallenged() != null) {
                acceptingPlayer.sendMessage(Color_R +
                        "You have already challenged someone yourself.");
            } else if (myPlayer.askedForContinuingGameBy() != null) {
                MyPlayer requestingPlayer = myPlayer.askedForContinuingGameBy();

                MyPlayer.addMyPlayer(requestingPlayer.get());

                for(MyPlayer myPlayer : myOnlinePlayers)
                    if (myPlayer.get().equals(requestingPlayer.get()))
                        requestingPlayer = myPlayer;

                requestingPlayer.get().sendMessage(Color_DR +
                        acceptingPlayer.getDisplayName() + " has accepted your continue game request.\n" +
                        "They will now be teleported to you.");
                acceptingPlayer.sendMessage(Color_DR +
                        "You have accepted " + requestingPlayer.get().getDisplayName() + " 's continue game request.\n" +
                        "You will now be teleported to them.");

                myPlayer.setGame(requestingPlayer.getGame());

                requestingPlayer.getGame().beginGame(requestingPlayer);
            } else if (myPlayer.getChallengedBy() == null) {
                acceptingPlayer.sendMessage(Color_R +
                        "You have not been challenged yet!");
            } else {
                Player challengingPlayer = myPlayer.getChallengedBy().get();

                challengingPlayer.sendMessage(Color_G +
                        acceptingPlayer.getDisplayName() + " has accepted your Tic-Tac-Toe Challenge.\n" +
                        "They will now be teleported to you.");
                acceptingPlayer.sendMessage(Color_G +
                        "You have accepted " + challengingPlayer.getDisplayName() + "'s Tic-Tac-Toe challenge.\n" +
                        "You will now be teleported to them.");

                new Game(myPlayer.getChallengedBy(), myPlayer);
            }
        } else {
            sender.sendMessage("Only a player can accept a Tic-Tac-Toe challenge.");
        }
        return true;
    }
}