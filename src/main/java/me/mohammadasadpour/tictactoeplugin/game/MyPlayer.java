package me.mohammadasadpour.tictactoeplugin.game;

import org.bukkit.entity.Player;

import java.io.Serializable;

public record MyPlayer(Player player, MyMaterial material) implements Serializable {}
