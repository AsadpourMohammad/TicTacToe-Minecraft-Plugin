package me.mohammadasadpour.tictactoeplugin;

import me.mohammadasadpour.tictactoeplugin.commands.*;
import me.mohammadasadpour.tictactoeplugin.listeners.OnGameBoardBreakListener;
import me.mohammadasadpour.tictactoeplugin.listeners.OnPutListener;
import me.mohammadasadpour.tictactoeplugin.listeners.OnStrangersWalkingInListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TicTacToePlugin extends JavaPlugin {
    public static String path = System.getProperty("user.dir") + "\\TicTacToeFiles";

    @Override
    public void onEnable() {
        new File(path).mkdir();

        getCommand("challenge").setExecutor(new ChallengeCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("deny").setExecutor(new DenyCommand());
        getCommand("put").setExecutor(new PutCommand());
        getCommand("save").setExecutor(new SaveCommand());
        getCommand("load").setExecutor(new LoadCommand());
        getCommand("scoreboard").setExecutor(new ScoreboardCommand());
        getServer().getPluginManager().registerEvents(new OnPutListener(), this);
        getServer().getPluginManager().registerEvents(new OnGameBoardBreakListener(), this);
        getServer().getPluginManager().registerEvents(new OnStrangersWalkingInListener(), this);
//        getServer().getPluginManager().registerEvents(new ScoreboardCommand(), this);

        //  Database

        //  Load doesn't work with accept and deny

        //  Players cannot access the commands if they go offline and return
        //  Fix the scoreboard animation
        //  Armor stand becomes visible for one of the players
        //  Break the game if players leave
        //  Strangers entering the arena creates exception
        //  Fireworks one at a time with timer
        //  Stop giving away material at tie?
        //      Or rather, make it in a way that only the winner can pick them up,
        //      And at tie both of them are allowed. (Possible?)
    }
}