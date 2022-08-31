package me.mohammadasadpour.tictactoeplugin.commands;

import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import static me.mohammadasadpour.tictactoeplugin.game.Game.game;

public class PutCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (game == null) {
                player.sendMessage("No Tic-Tac-Toe game has begun yet.");
                return true;
            } else if (game.isOver()) {
                player.sendMessage("Game has ended.");
                return true;
            } else if (!player.equals(game.getTurn().player())) {
                player.sendMessage("It's not your turn.");
                return true;
            } else if (args.length != 1) {
                player.sendMessage("Please use the 'Tic-Tac-Toe put' command correctly.");
                return false;
            } else if (!NumberUtils.isCreatable(args[0])) {
                player.sendMessage("Please enter a valid number.");
                return false;
            } else {
                if (game.set(Integer.parseInt(args[0]))) {
                    if (game.isOver()) {
                        announceEnd();
                        return true;
                    } else {
                        game.getTurn().player().sendMessage("Your turn to fill a spot.");
                        return true;
                    }
                } else {
                    player.sendMessage("Please choose a different spot.");
                    return true;
                }
            }
        } else {
            sender.sendMessage("Only a player can do the 'Tic-Tac-Toe put' action.");
            return true;
        }
    }

    private void announceEnd() {
        if (game.getWinner().equals("TIE")) {
            game.getPlayer1().player().sendMessage("The game ended with a tie.");
            game.getPlayer2().player().sendMessage("The game ended with a tie.");
        } else {
            game.getTurn().player().sendMessage("You have won the game!");
            fireworks();

            if (game.getTurn().equals(game.getPlayer1())) {
                game.getPlayer2().player().sendMessage("You lost the game to " + game.getPlayer1().player().getDisplayName() + "!");
            } else if (game.getTurn().equals(game.getPlayer2())) {
                game.getPlayer1().player().sendMessage("You lost the game to " + game.getPlayer2().player().getDisplayName() + "!");
            }
        }
    }

    private void fireworks() {
        Firework firework = game.getTurn().player().getWorld().spawn(game.getTurn().player().getLocation(), Firework.class);
        FireworkMeta data = firework.getFireworkMeta();
        data.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).withColor(Color.GREEN).with(FireworkEffect.Type.BALL_LARGE).withFlicker().build());
        data.setPower(25);
        firework.setFireworkMeta(data);
    }
}
