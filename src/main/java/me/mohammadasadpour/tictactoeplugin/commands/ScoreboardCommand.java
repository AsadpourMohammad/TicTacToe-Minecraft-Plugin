package me.mohammadasadpour.tictactoeplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardCommand implements CommandExecutor {
    public static int gamesWon = 0;
    public static int gamesLost = 0;
    public static int gamesTied = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            assert manager != null;
            Scoreboard scoreboard = manager.getNewScoreboard();

            Objective objective = scoreboard.registerNewObjective("TicTacToe","dummy","TicTacToe");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score gamesWonScore = objective.getScore("Games Won = " + gamesWon);
            Score gamesLostScore = objective.getScore("Games Lost = " + gamesLost);
            Score gamesTiedScore = objective.getScore("Games Tied = " + gamesTied);

            gamesWonScore.setScore(3);
            gamesLostScore.setScore(2);
            gamesTiedScore.setScore(1);

            player.setScoreboard(scoreboard);
            return true;
        } else {
            sender.sendMessage("Only a player can summon the scoreboard.");
            return true;
        }
    }
}
