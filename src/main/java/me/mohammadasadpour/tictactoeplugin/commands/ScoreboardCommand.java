package me.mohammadasadpour.tictactoeplugin.commands;

import me.mohammadasadpour.tictactoeplugin.TicTacToePlugin;
import me.mohammadasadpour.tictactoeplugin.game.MyPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.*;


public class ScoreboardCommand implements CommandExecutor, Listener {
    int threadID;
    private MyPlayer myPlayer;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            MyPlayer.addMyPlayer(player);

            for (MyPlayer myPlayer : myOnlinePlayers)
                if (myPlayer.get().equals(player))
                    this.myPlayer = myPlayer;

            if (args.length == 1 && args[0].equals("clear")) {
                myPlayer.clearScoreboard();
                player.sendMessage("Your scoreboard has been cleared.");
                myPlayer.showScoreboard(false);
                myPlayer.get().performCommand("scoreboard");
            } else if (args.length == 0) {
                if (!myPlayer.isScoreboardShown()) {
                    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

                    Objective objective = scoreboard.registerNewObjective(
                            "TicTacToeScoreboard",
                            "dummy",
                            ChatColor.translateAlternateColorCodes('&', "&5&l<< &b&lTicTacToe &5&l>>"));

                    objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                    Score line = objective.getScore(Color_B_B +
                            "=-=-=-=-=-=-=-=");
                    Score gamesWonScore = objective.getScore(Color_A_B +
                            "GAMES WON  =>  " + Color_W_B + myPlayer.getGamesWon());
                    Score gamesLostScore = objective.getScore(Color_A_B +
                            "GAMES LOST =>  " + Color_W_B + myPlayer.getGamesLost());
                    Score gamesTiedScore = objective.getScore(Color_A_B +
                            "GAMES TIED =>  " + Color_W_B + myPlayer.getGamesTied());

                    line.setScore(3);
                    gamesWonScore.setScore(2);
                    gamesLostScore.setScore(1);
                    gamesTiedScore.setScore(0);

                    player.setScoreboard(scoreboard);
                    myPlayer.showScoreboard(true);

//                    if (myPlayer.isScoreboardShown())
//                        scoreboardTitleAnimation();
                } else {
                    //  For hiding the scoreboard

//                    Bukkit.getScheduler().cancelTask(threadID);
                    player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                    myPlayer.showScoreboard(false);
                }
            } else {
                player.sendMessage(Color_R +
                        "Please use the 'Tic-Tac-Toe scoreboard' command correctly.");
                return false;
            }
        } else {
            sender.sendMessage("The scoreboard is only accessible to the players.");
        }
        return true;
    }

    public void scoreboardTitleAnimation() {
        threadID = Bukkit.getScheduler().scheduleSyncRepeatingTask(TicTacToePlugin.getProvidingPlugin(ScoreboardCommand.class), new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == 10)
                    count = 0;

                switch (count) {
                    case 0 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacToe &1&l>>"));
                    case 1 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &c&lT&f&licTacToe &1&l>>"));
                    case 2 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lT&c&li&f&lcTacToe &1&l>>"));
                    case 3 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTi&c&lc&f&lTacToe &1&l>>"));
                    case 4 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTic&c&lT&f&lacToe &1&l>>"));
                    case 5 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicT&c&la&f&lcToe &1&l>>"));
                    case 6 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTa&c&lc&f&lToe &1&l>>"));
                    case 7 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTac&c&lT&f&loe &1&l>>"));
                    case 8 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacT&c&lo&f&le &1&l>>"));
                    case 9 -> myPlayer.get().getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(
                            ChatColor.translateAlternateColorCodes('&', "&1&l << &f&lTicTacTo&c&le &1&l>>"));
                }
                count++;
            }
        }, 0, 5);
    }

    //    @EventHandler
    //    public void onPlayerLeave(PlayerQuitEvent event) {
    //        if (event.getPlayer().equals(myPlayer.get()))
    //            Bukkit.getScheduler().cancelTask(threadID);
    //    }
}
