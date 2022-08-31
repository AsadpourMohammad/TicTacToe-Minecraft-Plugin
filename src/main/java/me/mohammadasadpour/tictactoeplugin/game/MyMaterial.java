package me.mohammadasadpour.tictactoeplugin.game;

import org.bukkit.Material;

import static org.bukkit.Material.*;

public enum MyMaterial {
    RED(RED_WOOL),
    BLUE(BLUE_WOOL);

    private final Material material;

    MyMaterial(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public String toString() {
        return material.toString();
    }
}
