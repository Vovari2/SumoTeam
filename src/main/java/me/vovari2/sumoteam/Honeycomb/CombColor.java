package me.vovari2.sumoteam.Honeycomb;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CombColor {
    RED(new ArrayList<>(Arrays.asList(Material.RED_CONCRETE, Material.RED_WOOL))),
    BLUE(new ArrayList<>(Arrays.asList(Material.BLUE_CONCRETE, Material.BLUE_WOOL, Material.LAPIS_BLOCK))),
    GREEN(new ArrayList<>(Arrays.asList(Material.GREEN_CONCRETE, Material.GREEN_WOOL, Material.GREEN_TERRACOTTA))),
    YELLOW(new ArrayList<>(Arrays.asList(Material.YELLOW_CONCRETE, Material.YELLOW_WOOL, Material.GOLD_BLOCK))),
    GLASS(new ArrayList<>(Arrays.asList(Material.WHITE_STAINED_GLASS, Material.GRAY_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS))),
    ICE(new ArrayList<>(Arrays.asList(Material.BLUE_ICE, Material.PACKED_ICE))),
    NONE(new ArrayList<>());

    private final ArrayList<Material> materials;

    CombColor(ArrayList<Material> materials){
        this.materials = materials;
    }
    public ArrayList<Material> getMaterials(){
        return materials;
    }

    public static ArrayList<Material> getAllMaterials(){
        ArrayList<Material> allMaterials = new ArrayList<>();
        CombColor.getColors().forEach(color -> {
            if (!color.equals(CombColor.ICE) && !color.equals(CombColor.NONE))
                allMaterials.addAll(color.getMaterials());
        });
        return allMaterials;
    }
    public static CombColor getColor(Material material){
        for(CombColor color : CombColor.getColors())
            if(color.materials.contains(material))
                return color;
        return CombColor.NONE;
    }
    public static List<CombColor> getColors(){
        return Arrays.asList(CombColor.values());
    }
    public static boolean haveMaterial(Material material){
        for(CombColor color : CombColor.getColors())
            if(!color.equals(CombColor.ICE) && !color.equals(CombColor.NONE) && color.materials.contains(material))
                return true;
        return false;
    }
}
