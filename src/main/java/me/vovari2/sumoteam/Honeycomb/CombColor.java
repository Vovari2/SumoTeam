package me.vovari2.sumoteam.Honeycomb;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public enum CombColor {
    RED(new Material[]{Material.RED_CONCRETE, Material.RED_WOOL}),
    BLUE(new Material[]{Material.BLUE_CONCRETE, Material.BLUE_WOOL, Material.LAPIS_BLOCK}),
    GREEN(new Material[]{Material.GREEN_CONCRETE, Material.GREEN_WOOL, Material.GREEN_TERRACOTTA}),
    YELLOW(new Material[]{Material.YELLOW_CONCRETE, Material.YELLOW_WOOL, Material.GOLD_BLOCK}),
    GLASS(new Material[]{Material.WHITE_STAINED_GLASS, Material.GRAY_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS}),
    ICE(new Material[]{Material.BLUE_ICE, Material.PACKED_ICE});

    private final Material[] materials;

    CombColor(Material[] materials){
        this.materials = materials;
    }

    public ArrayList<Material> getMaterials(){
        return new ArrayList<>(Arrays.asList(materials));
    }
}
