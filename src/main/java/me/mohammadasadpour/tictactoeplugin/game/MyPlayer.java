package me.mohammadasadpour.tictactoeplugin.game;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class MyPlayer implements Serializable {
    public static ArrayList<MyPlayer> myOnlinePlayers = new ArrayList<>();

    private final Player player;
    private MyMaterial material;
    private Game game;
    private boolean scoreboard = false;

    private int gamesWon = 0;
    private int gamesLost = 0;
    private int gamesTied = 0;

    public MyPlayer(Player player) {
        this.player = player;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Player getPlayer() {
        return player;
    }

    public Material getMaterial() {
        return material.getMaterial();
    }

    public void setMaterial(MyMaterial material) {
        this.material = material;
    }

    public boolean isScoreboardShown() {
        return scoreboard;
    }

    public void setScoreboard(boolean scoreboard) {
        this.scoreboard = scoreboard;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getGamesTied() {
        return gamesTied;
    }

    public void anotherGameWon() {
        this.gamesWon++;
    }

    public void anotherGameLost() {
        this.gamesLost++;
    }

    public void anotherGameTied() {
        this.gamesTied++;
    }

    public void clearGamesWon() {
        this.gamesWon = 0;
    }

    public void clearGamesLost() {
        this.gamesLost = 0;
    }

    public void clearGamesTied() {
        this.gamesTied = 0;
    }

    public static void addThePlayer(Player player) {
        for (MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.getPlayer().getDisplayName().equals(player.getDisplayName()))
                return;

        myOnlinePlayers.add(new MyPlayer(player));
    }
}