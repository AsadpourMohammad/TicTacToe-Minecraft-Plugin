package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.addThePlayer;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;


public class ScoreboardCommand implements CommandExecutor {
    private MyPlayer myPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                addThePlayer(player);

                for(MyPlayer myPlayer : myOnlinePlayers)
                    if (myPlayer.getPlayer().equals(player))
                        this.myPlayer = myPlayer;

                ScoreboardManager manager = Bukkit.getScoreboardManager();
                assert manager != null;
                Scoreboard scoreboard = manager.getNewScoreboard();

                Objective objective = scoreboard.registerNewObjective("TicTacToe","dummy","TicTacToe");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                Score gamesWonScore = objective.getScore("Games Won = " + myPlayer.getGamesWon());
                Score gamesLostScore = objective.getScore("Games Lost = " + myPlayer.getGamesLost());
                Score gamesTiedScore = objective.getScore("Games Tied = " + myPlayer.getGamesTied());

                gamesWonScore.setScore(3);
                gamesLostScore.setScore(2);
                gamesTiedScore.setScore(1);

                player.setScoreboard(scoreboard);
            } else if (args.length == 1 && args[0].equals("clear")) {
                myPlayer.clearGamesWon();
                myPlayer.clearGamesLost();
                myPlayer.clearGamesTied();
                myPlayer.getPlayer().performCommand("scoreboard");
            } else {
                player.sendMessage("Please use the 'Tic-Tac-Toe scoreboard' command correctly.");
                return false;
            }
        } else {
            sender.sendMessage("Only a player can summon the scoreboard.");
        }
        return true;
    }
}
