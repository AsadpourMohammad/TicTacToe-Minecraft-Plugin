package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.TicTacToePlugin;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import net.md_5.bungee.api.ChatColor;
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
            addThePlayer(player);

            for(MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.getPlayer().equals(player))
                    this.myPlayer = myPlayer;
            if (!myPlayer.isScoreboardShown()) {
                if (args.length == 0) {

                    ScoreboardManager manager = Bukkit.getScoreboardManager();
                    Scoreboard scoreboard = manager.getNewScoreboard();

                    Objective objective = scoreboard.registerNewObjective("TicTacToeScoreboard", "dummy",
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacToe &1&l>>"));
                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Score score = objective.getScore(ChatColor.BLUE + "=-=-=-=-=-=-=-=");
                    Score gamesWonScore = objective.getScore(ChatColor.AQUA + "GAMES WON  ->  " + ChatColor.WHITE + myPlayer.getGamesWon());
                    Score gamesLostScore = objective.getScore(ChatColor.AQUA + "GAMES LOST ->  " + ChatColor.WHITE + myPlayer.getGamesLost());
                    Score gamesTiedScore = objective.getScore(ChatColor.AQUA + "GAMES TIED ->  " + ChatColor.WHITE + myPlayer.getGamesTied());

                    score.setScore(3);
                    gamesWonScore.setScore(2);
                    gamesLostScore.setScore(1);
                    gamesTiedScore.setScore(0);

                    player.setScoreboard(scoreboard);

                    myPlayer.setScoreboard(true);

                    if (myPlayer.isScoreboardShown())
                        scoreboardTitleAnimation();
                } else {
                    player.sendMessage("Please use the 'Tic-Tac-Toe scoreboard' command correctly.");
                    return false;
                }
            } else {
                //  For hiding the scoreboard
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                myPlayer.setScoreboard(false);
            }
        } else {
            sender.sendMessage("Only a player can summon the scoreboard.");
        }
        return true;
    }

    public void scoreboardTitleAnimation() {
        try {

            Bukkit.getScheduler().scheduleSyncRepeatingTask(TicTacToePlugin.getProvidingPlugin(ScoreboardCommand.class), new Runnable() {

                int count = 0;

                @Override
                public void run() {

                    if (count == 10)
                        count = 0;

                    switch (count) {
                        case 0 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacToe &1&l>>"));
                        case 1 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &c&lT&f&licTacToe &1&l>>"));
                        case 2 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lT&c&li&f&lcTacToe &1&l>>"));
                        case 3 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTi&c&lc&f&lTacToe &1&l>>"));
                        case 4 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTic&c&lT&f&lacToe &1&l>>"));
                        case 5 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicT&c&la&f&lcToe &1&l>>"));
                        case 6 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTa&c&lc&f&lToe &1&l>>"));
                        case 7 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTac&c&lT&f&loe &1&l>>"));
                        case 8 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacT&c&lo&f&le &1&l>>"));
                        case 9 -> myPlayer.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                                ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacTo&c&le &1&l>>"));
                    }
                    count++;
                }
            }, 0, 5);

        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
            e.printStackTrace();
        }
    }
}
