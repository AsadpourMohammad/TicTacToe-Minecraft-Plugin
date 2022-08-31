package me.mohammadasadpour.tictactoeplugin.game;

import org.apache.commons.lang3.StringUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import static me.mohammadasadpour.tictactoeplugin.commands.AcceptCommand.blockBoard;

public class Game implements Serializable {
    public static Game game = null;

    private final MyPlayer player1;
    private final MyPlayer player2;

    private MyPlayer turn;
    private int turnCount = 0;

    private String winner;

    private final String gameName;

    private final String[] board;

    private boolean over = false;

    public Game(MyPlayer player1, MyPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.turn = this.player1;
        this.gameName =
                StringUtils.capitalize(player1.player().getDisplayName()) +
                StringUtils.capitalize(player2.player().getDisplayName()) +
                hashCode();

        this.board = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        game = this;
    }

    public boolean set(int n) {
        if (Arrays.asList(board).contains(String.valueOf(n))) {
            if (!isOver()) {
                this.board[n - 1] = turn.material().getMaterial().name();
                blockBoard.get(n - 1).getBlock().setType(game.getTurn().material().getMaterial());
            }

            if (hasContestantWon()) {
                over = true;
                winner = turn.toString();
            } else if (turnCount == 8) {
                over = true;
                winner = "TIE";
            } else {
                turn = (turn == player1) ? player2 : player1;
                turnCount++;
            }
            return true;
        } else
            return false;
    }

    public boolean hasContestantWon() {
        return (board[0].equals(turn.material().getMaterial().name()) && board[1].equals(turn.material().getMaterial().name()) && board[2].equals(turn.material().getMaterial().name())) ||
                (board[3].equals(turn.material().getMaterial().name()) && board[4].equals(turn.material().getMaterial().name()) && board[5].equals(turn.material().getMaterial().name())) ||
                (board[6].equals(turn.material().getMaterial().name()) && board[7].equals(turn.material().getMaterial().name()) && board[8].equals(turn.material().getMaterial().name())) ||

                (board[0].equals(turn.material().getMaterial().name()) && board[3].equals(turn.material().getMaterial().name()) && board[6].equals(turn.material().getMaterial().name())) ||
                (board[1].equals(turn.material().getMaterial().name()) && board[4].equals(turn.material().getMaterial().name()) && board[7].equals(turn.material().getMaterial().name())) ||
                (board[2].equals(turn.material().getMaterial().name()) && board[5].equals(turn.material().getMaterial().name()) && board[8].equals(turn.material().getMaterial().name())) ||

                (board[0].equals(turn.material().getMaterial().name()) && board[4].equals(turn.material().getMaterial().name()) && board[8].equals(turn.material().getMaterial().name())) ||
                (board[2].equals(turn.material().getMaterial().name()) && board[4].equals(turn.material().getMaterial().name()) && board[6].equals(turn.material().getMaterial().name()));
    }

    public boolean isOver() {
        return over;
    }

    public MyPlayer getPlayer1() {
        return player1;
    }

    public MyPlayer getPlayer2() {
        return player2;
    }

    public MyPlayer getTurn() {
        return turn;
    }

    public String getWinner() {
        return winner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }
}
