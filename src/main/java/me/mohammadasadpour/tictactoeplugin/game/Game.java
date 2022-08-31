package me.mohammadasadpour.tictactoeplugin.game;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import static me.mohammadasadpour.tictactoeplugin.game.MyLocation.getLocList;
import static me.mohammadasadpour.tictactoeplugin.game.MyPlayer.myOnlinePlayers;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.Color_A;
import static me.mohammadasadpour.tictactoeplugin.utils.RepeatingChatColors.Color_A_B;

public class Game implements Serializable {
    //  Static Collections ---------------------------------------------------------------------------------------------

    public static HashMap<String, Game> games = new HashMap<>();
    public static ArrayList<MyLocation> allMyLocations = new ArrayList<>();

    //  Variables ------------------------------------------------------------------------------------------------------

    private MyPlayer myPlayer1;
    private MyPlayer myPlayer2;

    private MyPlayer turn;

    private final String gameName;

    private final String[] board = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final String[] blockMaterials = {
            Material.WHITE_WOOL.name(), Material.WHITE_WOOL.name(), Material.WHITE_WOOL.name(),
            Material.WHITE_WOOL.name(), Material.WHITE_WOOL.name(), Material.WHITE_WOOL.name(),
            Material.WHITE_WOOL.name(), Material.WHITE_WOOL.name(), Material.WHITE_WOOL.name()};
    private ArrayList<MyLocation> blockBoard = null;
    private ArrayList<MyLocation> arena = null;

    private boolean over = false;

    //  Begin game -----------------------------------------------------------------------------------------------------

    public Game(MyPlayer myPlayer1, MyPlayer myPlayer2) {
        this.myPlayer1 = myPlayer1;
        this.myPlayer2 = myPlayer2;

        this.turn = this.myPlayer1;
        this.beginGame(turn);

        this.gameName = myPlayer1.get().getDisplayName() + myPlayer2.get().getDisplayName() + "." + hashCode();
    }

    public void beginGame(MyPlayer theStartingPlayer) {
        MyPlayer.addMyPlayer(myPlayer1.get());

        for(MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.get().equals(myPlayer1.get()))
                this.myPlayer1 = myPlayer;

        MyPlayer.addMyPlayer(myPlayer2.get());

        for(MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.get().equals(myPlayer2.get()))
                this.myPlayer2 = myPlayer;

        MyPlayer.addMyPlayer(turn.get());

        for(MyPlayer myPlayer : myOnlinePlayers)
            if (myPlayer.get().equals(turn.get()))
                this.turn = myPlayer;

        this.myPlayer1.setMaterial(MyMaterial.RED);
        this.myPlayer1.setGame(this);

        this.myPlayer2.setMaterial(MyMaterial.BLUE);
        this.myPlayer2.setGame(this);

        Player startingPlayer = theStartingPlayer.get();
        Player teleportedPlayer = startingPlayer.equals(myPlayer1.get()) ? myPlayer2.get() : myPlayer1.get();

        //  Main Ground
        arena = new ArrayList<>();
        for (int z = -5; z <= 1; z++)
            for (int x = -3; x <= 3; x++)
                arena.add(new MyLocation(new Location(startingPlayer.getWorld(),
                        startingPlayer.getLocation().getBlockX() + x,
                        startingPlayer.getLocation().getBlockY() + 2,
                        startingPlayer.getLocation().getBlockZ() + z)));

        for (MyLocation location : arena)
            location.getLoc().getBlock().setType(Material.BEACON);

        //  Stone sides
        int[] groundStone = {0, 1, 2, 3, 4, 5, 6, 7, 13, 14, 20, 21, 27, 28, 34, 35, 41, 42, 43, 44, 45, 46, 47, 48};
        for (int temp : groundStone)
            arena.get(temp).getLoc().getBlock().setType(Material.CHISELED_POLISHED_BLACKSTONE);

        //  Roof
        ArrayList<MyLocation> roofBlock = new ArrayList<>();
        for (MyLocation location : arena) {
            Location blockLocation = new Location(startingPlayer.getWorld(),
                    location.getLoc().getBlockX(),
                    location.getLoc().getBlockY(),
                    location.getLoc().getBlockZ());
            blockLocation.setY(location.getLoc().getY() + 5);
            blockLocation.getBlock().setType(Material.GLASS);
            roofBlock.add(new MyLocation(blockLocation));
        }
        //  Roof Stone sides
        for (int temp : groundStone)
            roofBlock.get(temp).getLoc().getBlock().setType(Material.CHISELED_POLISHED_BLACKSTONE);

        arena.addAll(roofBlock);

        //  Walls
        int[] places = {0, 6, 42, 48};
        for (int temp : places) {
            Location block1 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() + 1,
                    arena.get(temp).getLoc().getBlockZ());
            block1.getBlock().setType(Material.CHISELED_POLISHED_BLACKSTONE);
            arena.add(new MyLocation(block1));

            Location block2 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() + 2,
                    arena.get(temp).getLoc().getBlockZ());
            block2.getBlock().setType(Material.BEACON);
            arena.add(new MyLocation(block2));

            Location block3 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() + 3,
                    arena.get(temp).getLoc().getBlockZ());
            block3.getBlock().setType(Material.BEACON);
            arena.add(new MyLocation(block3));

            Location block4 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() + 4,
                    arena.get(temp).getLoc().getBlockZ());
            block4.getBlock().setType(Material.CHISELED_POLISHED_BLACKSTONE);
            arena.add(new MyLocation(block4));

            Location block5 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() + 5,
                    arena.get(temp).getLoc().getBlockZ());
            block5.getBlock().setType(Material.CHISELED_POLISHED_BLACKSTONE);
            arena.add(new MyLocation(block5));
        }

        ArrayList<MyLocation> stairBlock = new ArrayList<>();
        int[] stairIndex = {44, 45, 46};
        for (int temp : stairIndex) {
            Location block1 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY(),
                    arena.get(temp).getLoc().getBlockZ() + 1);
            block1.getBlock().setType(Material.WHITE_WOOL);
            stairBlock.add(new MyLocation(block1));

            Location block2 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() - 1,
                    arena.get(temp).getLoc().getBlockZ() + 2);
            block2.getBlock().setType(Material.WHITE_WOOL);
            stairBlock.add(new MyLocation(block2));

            Location block3 = new Location(startingPlayer.getWorld(),
                    arena.get(temp).getLoc().getBlockX(),
                    arena.get(temp).getLoc().getBlockY() - 2,
                    arena.get(temp).getLoc().getBlockZ() + 3);
            block3.getBlock().setType(Material.WHITE_WOOL);
            stairBlock.add(new MyLocation(block3));
        }
        arena.addAll(stairBlock);

        //  Game Board
        blockBoard = new ArrayList<>();
        for (int y = 5; y >= 3; y--)
            for (int x = -1; x <= 1; x++)
                blockBoard.add(new MyLocation(new Location(startingPlayer.getWorld(),
                        startingPlayer.getLocation().getBlockX() + x,
                        startingPlayer.getLocation().getBlockY() + y,
                        startingPlayer.getLocation().getBlockZ() - 5)));

        for (int i = 0; i < 9; i++)
            blockBoard.get(i).getLoc().getBlock().setType(Material.valueOf(blockMaterials[i]));

        allMyLocations.addAll(arena);
        allMyLocations.addAll(blockBoard);

        blockBoardHologram();

        startingPlayer.teleport(startingPlayer.getLocation().add(1, 3, -1));
        startingPlayer.sendMessage(Color_A +
                startingPlayer.getDisplayName() + " has been teleported to your location. The game has begun");

        teleportedPlayer.teleport(startingPlayer.getLocation().add(-2, 0, 0));
        teleportedPlayer.sendMessage(Color_A + "You have been teleported to " + startingPlayer.getDisplayName() + "'s location.");

        Player turnPlayer = turn.get();
        Player waitTurnPlayer = turnPlayer.equals(startingPlayer) ? teleportedPlayer : startingPlayer;

        turnPlayer.sendMessage("It's your turn.");
        waitTurnPlayer.sendMessage("Please wait for your turn.");

        games.put(gameName, this);
    }

    public void blockBoardHologram() {

        ArmorStand ticTacToeHologram = (ArmorStand) myPlayer1.get().getWorld().spawnEntity(
                arena.get(45).getLoc().getBlock().getLocation().add(0.5, 1.9, 0), EntityType.ARMOR_STAND);
        ticTacToeHologram.setGravity(false);
        ticTacToeHologram.setVisible(false);
        ticTacToeHologram.setCustomNameVisible(true);
        ticTacToeHologram.setCustomName(ChatColor.translateAlternateColorCodes('&', "&c&l&k||&c&lTicTacToe&c&l&k||"));

        ArmorStand playersHologram = (ArmorStand) myPlayer1.get().getWorld().spawnEntity(
                arena.get(45).getLoc().getBlock().getLocation().add(0.5, 1.6, 0), EntityType.ARMOR_STAND);
        playersHologram.setGravity(false);
        playersHologram.setVisible(false);
        playersHologram.setCustomNameVisible(true);
        playersHologram.setCustomName(
                Color_A_B + myPlayer1.get().getDisplayName() + " VS " + myPlayer2.get().getDisplayName());
    }

    //  Put ------------------------------------------------------------------------------------------------------------

    public boolean put(int n) {
        if (Arrays.asList(board).contains(String.valueOf(n))) {
            if (!isOver()) {
                this.board[n - 1] = turn.getMaterial().name();
                blockBoard.get(n - 1).getLoc().getBlock().setType(turn.getMaterial());
                blockMaterials[n - 1] = turn.getMaterial().name();
            }

            if (hasContestantWon()) {
                over = true;
            } else if (isOver()) {
                turn = null;
            } else
                turn = (turn == myPlayer1) ? myPlayer2 : myPlayer1;
            return true;
        } else
            return false;
    }

    //  Check For End --------------------------------------------------------------------------------------------------

    public boolean hasContestantWon() {
        String checkValue = turn.getMaterial().name();

        return (board[0].equals(checkValue) && board[1].equals(checkValue) && board[2].equals(checkValue)) ||
                (board[3].equals(checkValue) && board[4].equals(checkValue) && board[5].equals(checkValue)) ||
                (board[6].equals(checkValue) && board[7].equals(checkValue) && board[8].equals(checkValue)) ||

                (board[0].equals(checkValue) && board[3].equals(checkValue) && board[6].equals(checkValue)) ||
                (board[1].equals(checkValue) && board[4].equals(checkValue) && board[7].equals(checkValue)) ||
                (board[2].equals(checkValue) && board[5].equals(checkValue) && board[8].equals(checkValue)) ||

                (board[0].equals(checkValue) && board[4].equals(checkValue) && board[8].equals(checkValue)) ||
                (board[2].equals(checkValue) && board[4].equals(checkValue) && board[6].equals(checkValue));
    }

    public boolean isOver() {
        if (over)
            return true;

        for (String place : board)
            if (NumberUtils.isCreatable(place))
                return false;

        return (over = true);
    }

    //  Setter & Getters -----------------------------------------------------------------------------------------------

    public String getGameName() {
        return gameName;
    }

    public MyPlayer getMyPlayer1() {
        return myPlayer1;
    }

    public MyPlayer getMyPlayer2() {
        return myPlayer2;
    }

    public ArrayList<Location> getBlockBoard() {
        return getLocList(blockBoard);
    }

    public ArrayList<Location> getArena() {
        return getLocList(arena);
    }

    public MyPlayer getTurn() {
        return turn;
    }

    //  Endgame Actions ------------------------------------------------------------------------------------------------

    public void endGame() {
        destroyBoard();

        if (isOver())
            winnerHologram();

        setPlayerGamesNull();
    }

    public void destroyBoard() {
        for (MyLocation location : blockBoard)
            location.getLoc().getBlock().breakNaturally();

        for (MyLocation location : arena)
            location.getLoc().getBlock().breakNaturally();

        allMyLocations.removeAll(arena);
        allMyLocations.removeAll(blockBoard);

        myPlayer1.get().teleport(myPlayer1.get().getLocation().add(0, -2, 0));
        myPlayer2.get().teleport(myPlayer2.get().getLocation().add(0, -2, 0));
    }

    public void winnerHologram() {
        ArmorStand winnerHologram = (ArmorStand) myPlayer1.get().getWorld().spawnEntity(
                arena.get(45).getLoc().getBlock().getLocation().add(0.5, 1.3, 0), EntityType.ARMOR_STAND);
        winnerHologram.setGravity(false);
        winnerHologram.setVisible(false);
        winnerHologram.setCustomNameVisible(true);
        winnerHologram.setCustomName(turn == null ?
                (ChatColor.BOLD + "GAME TIED!") : (ChatColor.BOLD + "WINNER = " + turn.get().getDisplayName()));
    }

    public void setPlayerGamesNull() {
        myPlayer1.setGame(null);
        myPlayer1.setChallenged(null);
        myPlayer1.setChallengedBy(null);
        myPlayer1.setContinueGameBy(null);

        myPlayer2.setGame(null);
        myPlayer2.setChallenged(null);
        myPlayer2.setChallengedBy(null);
        myPlayer2.setContinueGameBy(null);
    }

    //  Hashcode -------------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        return Objects.hash(myPlayer1, myPlayer2);
    }
}