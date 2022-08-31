package me.mohammadasadpour.tictactoeplugin;

import me.mohammadasadpour.tictactoeplugin.commands.*;
import me.mohammadasadpour.tictactoeplugin.listeners.OnGameBoardBreakListener;
import me.mohammadasadpour.tictactoeplugin.listeners.OnPutListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class TicTacToePlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getCommand("challenge").setExecutor(new ChallengeCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("deny").setExecutor(new DenyCommand());
        getCommand("put").setExecutor(new PutCommand());
        getCommand("scoreboard").setExecutor(new ScoreboardCommand());
        getServer().getPluginManager().registerEvents(new OnPutListener(), this);
        getServer().getPluginManager().registerEvents(new OnGameBoardBreakListener(), this);
        //  30 Seconds wait for answer
        //  Database
    }
}