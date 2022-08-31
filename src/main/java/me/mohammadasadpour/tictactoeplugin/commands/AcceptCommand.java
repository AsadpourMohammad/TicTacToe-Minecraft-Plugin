package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.Game;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import me.mohammadasadpour.tictactoeplugin.game.MyMaterial;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.player1;
import static me.mohammadasadpour.tictactoeplugin.commands.ChallengeCommand.player2;
import static me.mohammadasadpour.tictactoeplugin.game.Game.game;

public class AcceptCommand implements CommandExecutor {
    public static ArrayList<Location> blockBoard = null;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player2) {
            if (player2.equals(ChallengeCommand.player2)) {
                player1.sendMessage(player2.getDisplayName() + " has accepted your Tic-Tac-Toe challenge.\n" +
                        "They will now be teleported to you.");
                player2.sendMessage("You have accepted " + player1.getDisplayName() + "'s Tic-Tac-Toe challenge.\n" +
                        "You will now be teleported to them.");

                createBoard();
                game = new Game(new MyPlayer(player1, MyMaterial.RED), new MyPlayer(player2, MyMaterial.BLUE));
                return true;
            } else {
                player2.sendMessage("You have not been challenged yet.");
                return true;
            }
        } else {
            sender.sendMessage("Only a player can accept a challenge.");
            return true;
        }
    }

    public void createBoard() {
        ArrayList<Location> groundBlock = new ArrayList<>();
        for (int x = -10; x < 10; x++)
            for (int z = -10; z < 10; z++)
                groundBlock.add(new Location(player1.getWorld(),
                        player1.getLocation().getBlockX() + x,
                        player1.getLocation().getBlockY() + 20,
                        player1.getLocation().getBlockZ() + z));

        for (Location location : groundBlock)
            location.getBlock().setType(org.bukkit.Material.PURPLE_GLAZED_TERRACOTTA);

        blockBoard = new ArrayList<>();
        for (int y = 23; y > 20; y--)
            for (int x = -1; x < 2; x++)
                blockBoard.add(new Location(player1.getWorld(),
                        player1.getLocation().getBlockX() + x,
                        player1.getLocation().getBlockY() + y,
                        player1.getLocation().getBlockZ() - 5));

        for (Location location : blockBoard)
            location.getBlock().setType(org.bukkit.Material.WHITE_WOOL);

        player1.teleport(player1.getLocation().add(0,21,0));
        player1.sendMessage(player2.getDisplayName() + " has been teleported to your location.");

        player2.teleport(player1.getLocation().add(-3,0,0));
        player2.sendMessage("You have been teleported to " + player1.getDisplayName() + "'s location.");
    }
}