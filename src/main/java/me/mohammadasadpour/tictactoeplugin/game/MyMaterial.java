package me.mohammadasadpour.tictactoeplugin.game;

import org.bukkit.Material;

import java.io.Serializable;

import static org.bukkit.Material.*;

public enum MyMaterial implements Serializable {
    RED(RED_WOOL),
    BLUE(BLUE_WOOL);

    private final String materialName;

    MyMaterial(Material material) {
        this.materialName = material.name();
    }

    public Material getMaterial() {
        return Material.valueOf(materialName);
    }
}
