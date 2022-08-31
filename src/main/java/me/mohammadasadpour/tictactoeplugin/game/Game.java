package me.mohammadasadpour.tictactoeplugin.game;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    public static HashMap<String, Game> games = new HashMap<>();
    public static ArrayList<Location> allLocations = new ArrayList<>();

    private final MyPlayer myPlayer1;
    private final MyPlayer myPlayer2;

    private MyPlayer turn;
    private int turnCount = 0;

    private String winner;

    private final String gameName;

    private final String[] board;
    private ArrayList<Location> blockBoard = null;
    private ArrayList<Location> groundBlock = null;

    private boolean over = false;

    public Game(MyPlayer myPlayer1, MyPlayer myPlayer2) {
        this.myPlayer1 = myPlayer1;
        this.myPlayer1.setMaterial(MyMaterial.RED);
        this.myPlayer1.setGame(this);
        this.myPlayer2 = myPlayer2;
        this.myPlayer2.setMaterial(MyMaterial.BLUE);
        this.myPlayer2.setGame(this);
        this.turn = this.myPlayer1;
        this.gameName =
                StringUtils.capitalize(myPlayer1.getPlayer().getDisplayName()) +
                StringUtils.capitalize(myPlayer2.getPlayer().getDisplayName()) +
                hashCode();

        this.board = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        this.createBoard();

        games.put(gameName, this);
    }

    public void createBoard() {
        groundBlock = new ArrayList<>();
        for (int x = -3; x < 4; x++)
            for (int z = -5; z < 2; z++)
                groundBlock.add(new Location(myPlayer1.getPlayer().getWorld(),
                        myPlayer1.getPlayer().getLocation().getBlockX() + x,
                        myPlayer1.getPlayer().getLocation().getBlockY() + 2,
                        myPlayer1.getPlayer().getLocation().getBlockZ() + z));

        for (Location location : groundBlock)
            location.getBlock().setType(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);

        blockBoard = new ArrayList<>();
        for (int y = 5; y > 2; y--)
            for (int x = -1; x < 2; x++)
                blockBoard.add(new Location(myPlayer1.getPlayer().getWorld(),
                        myPlayer1.getPlayer().getLocation().getBlockX() + x,
                        myPlayer1.getPlayer().getLocation().getBlockY() + y,
                        myPlayer1.getPlayer().getLocation().getBlockZ() - 5));

        for (Location location : blockBoard)
            location.getBlock().setType(org.bukkit.Material.WHITE_WOOL);

        allLocations.addAll(groundBlock);
        allLocations.addAll(blockBoard);

        blockBoardHologram();

        myPlayer1.getPlayer().teleport(myPlayer1.getPlayer().getLocation().add(1,3,-1));
        myPlayer1.getPlayer().sendMessage(myPlayer2.getPlayer().getDisplayName() + " has been teleported to your location.");

        myPlayer2.getPlayer().teleport(myPlayer1.getPlayer().getLocation().add(-2,0,0));
        myPlayer2.getPlayer().sendMessage("You have been teleported to " + myPlayer1.getPlayer().getDisplayName() + "'s location.");
    }

    public void destroyBoard() {
        for (Location location : blockBoard)
            location.getBlock().breakNaturally();

        for (Location location : groundBlock)
            location.getBlock().breakNaturally();

        allLocations.removeAll(groundBlock);
        allLocations.removeAll(blockBoard);

        myPlayer1.getPlayer().teleport(myPlayer1.getPlayer().getLocation().add(0,-2,0));
        myPlayer2.getPlayer().teleport(myPlayer2.getPlayer().getLocation().add(0,-2,0));
    }

    public void setPlayerGamesNull() {
        myPlayer1.setGame(null);
        myPlayer2.setGame(null);
    }

    public boolean put(int n) {
        if (Arrays.asList(board).contains(String.valueOf(n))) {
            if (!isOver()) {
                this.board[n - 1] = turn.getMaterial().name();
                blockBoard.get(n - 1).getBlock().setType(turn.getMaterial());
            }

            if (hasContestantWon()) {
                over = true;
                winner = turn.getPlayer().getDisplayName();
                winnerHologram();
                destroyBoard();
            } else if (turnCount == 8) {
                over = true;
                winner = "TIE";
                winnerHologram();
                destroyBoard();
            } else {
                turn = (turn == myPlayer1) ? myPlayer2 : myPlayer1;
                turnCount++;
            }
            return true;
        } else
            return false;
    }

    public boolean hasContestantWon() {
        return (board[0].equals(turn.getMaterial().name()) && board[1].equals(turn.getMaterial().name()) && board[2].equals(turn.getMaterial().name())) ||
                (board[3].equals(turn.getMaterial().name()) && board[4].equals(turn.getMaterial().name()) && board[5].equals(turn.getMaterial().name())) ||
                (board[6].equals(turn.getMaterial().name()) && board[7].equals(turn.getMaterial().name()) && board[8].equals(turn.getMaterial().name())) ||

                (board[0].equals(turn.getMaterial().name()) && board[3].equals(turn.getMaterial().name()) && board[6].equals(turn.getMaterial().name())) ||
                (board[1].equals(turn.getMaterial().name()) && board[4].equals(turn.getMaterial().name()) && board[7].equals(turn.getMaterial().name())) ||
                (board[2].equals(turn.getMaterial().name()) && board[5].equals(turn.getMaterial().name()) && board[8].equals(turn.getMaterial().name())) ||

                (board[0].equals(turn.getMaterial().name()) && board[4].equals(turn.getMaterial().name()) && board[8].equals(turn.getMaterial().name())) ||
                (board[2].equals(turn.getMaterial().name()) && board[4].equals(turn.getMaterial().name()) && board[6].equals(turn.getMaterial().name()));
    }

    public boolean isOver() {
        return over;
    }

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
        return blockBoard;
    }

    public ArrayList<Location> getGroundBlock() {
        return groundBlock;
    }

    public MyPlayer getTurn() {
        return turn;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myPlayer1, myPlayer2);
    }

    public void blockBoardHologram() {
        ArmorStand ticTacToeHologram = (ArmorStand) myPlayer1.getPlayer().getWorld().spawnEntity(blockBoard.get(2).getBlock().getLocation().add(-0.5,0.3,0), EntityType.ARMOR_STAND);
        ticTacToeHologram.setGravity(false);
        ticTacToeHologram.setVisible(false);
        ticTacToeHologram.setCustomNameVisible(true);
        ticTacToeHologram.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + "TicTacToe");

        ArmorStand playersHologram = (ArmorStand) myPlayer1.getPlayer().getWorld().spawnEntity(blockBoard.get(2).getBlock().getLocation().add(-0.5,0,0), EntityType.ARMOR_STAND);
        playersHologram.setGravity(false);
        playersHologram.setVisible(false);
        playersHologram.setCustomNameVisible(true);
        playersHologram.setCustomName(ChatColor.AQUA + myPlayer1.getPlayer().getDisplayName() + " VS " + myPlayer2.getPlayer().getDisplayName());
    }

    public void winnerHologram() {
        ArmorStand winnerHologram = (ArmorStand) myPlayer1.getPlayer().getWorld().spawnEntity(blockBoard.get(2).getBlock().getLocation().add(-0.5,-0.3,0), EntityType.ARMOR_STAND);
        winnerHologram.setVisible(false);
        winnerHologram.setCustomNameVisible(true);
        if (winner.equals("TIE"))
            winnerHologram.setCustomName(ChatColor.BOLD + "GAME TIED!");
        else
            winnerHologram.setCustomName(ChatColor.BOLD + "WINNER = " + winner.toUpperCase(Locale.ROOT));
        winnerHologram.setGravity(false);
    }
}
