package me.mohammadasadpour.tictactoeplugin.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class MyPlayer implements Serializable {
    //  Static Collections ---------------------------------------------------------------------------------------------

    public static ArrayList<MyPlayer> myOnlinePlayers = new ArrayList<>();

    public static void addMyPlayer(Player player) {
        for (MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.get().getUniqueId().equals(player.getUniqueId()))
                return;

        myOnlinePlayers.add(new MyPlayer(player));
    }

    //  Variables ------------------------------------------------------------------------------------------------------

    private final UUID playerUUID;
    private MyMaterial material;
    private MyPlayer challenged;
    private MyPlayer challengedBy;
    private MyPlayer continueGameWith;
    private Game game;

    private boolean scoreboard = false;
    private int gamesWon = 0;
    private int gamesLost = 0;

    private int gamesTied = 0;

    //  Serializing & Deserializing ------------------------------------------------------------------------------------

    public MyPlayer(Player player) {
        this.playerUUID = player.getUniqueId();
    }

    public Player get() {
        return Bukkit.getPlayer(playerUUID);
    }

    //  Setter & Getters -----------------------------------------------------------------------------------------------

    public MyPlayer getChallenged() {
        return challenged;
    }

    public void setChallenged(MyPlayer challenged) {
        this.challenged = challenged;
    }

    public MyPlayer getChallengedBy() {
        return challengedBy;
    }

    public void setChallengedBy(MyPlayer challengedBy) {
        this.challengedBy = challengedBy;
    }

    public MyPlayer askedForContinuingGameBy() {
        return continueGameWith;
    }

    public void setContinueGameBy(MyPlayer continueGame) {
        this.continueGameWith = continueGame;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public Material getMaterial() {
        return material.getMaterial();
    }

    public void setMaterial(MyMaterial material) {
        this.material = material;
    }

    //  Scoreboard -----------------------------------------------------------------------------------------------------

    public boolean isScoreboardShown() {
        return scoreboard;
    }

    public void showScoreboard(boolean scoreboard) {
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

    public void clearScoreboard() {
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.gamesTied = 0;
    }
}