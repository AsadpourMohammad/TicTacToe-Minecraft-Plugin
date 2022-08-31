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

        //  Fix the animation
        //  Check and see what happens when a player goes offline gets challenged,
        //      and also check what happens if that player comes back, specially to the scoreboard,
        //      If it stays the same or not
        //  Break the game if players leave
    }
}