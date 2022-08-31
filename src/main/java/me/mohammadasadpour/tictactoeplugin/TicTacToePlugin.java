package me.mohammadasadpour.tictactoeplugin;

import me.mohammadasadpour.tictactoeplugin.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class TicTacToePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("challenge").setExecutor(new ChallengeCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("deny").setExecutor(new DenyCommand());
        getCommand("put").setExecutor(new PutCommand());
        getCommand("scoreboard").setExecutor(new ScoreboardCommand());
        //  Right Click
        //  Breaking a block
        //  Hologram
        //  Database
        //  ChatColor
    }
}