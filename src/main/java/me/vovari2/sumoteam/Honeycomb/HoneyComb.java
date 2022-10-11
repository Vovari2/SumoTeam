package me.vovari2.sumoteam.Honeycomb;

import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;

public class HoneyComb {
    public boolean isIce = false;
    public boolean isGlass = false;
    public CombColor color;
    public CombType type = CombType.FULL;

    public int X;
    public int Y;
    public int Z;
    public void Location(int X, int Y, int Z) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public StructureRotation rotation = StructureRotation.NONE;
    public Mirror mirror = Mirror.NONE;

    public HoneyComb(CombColor color, int X, int Y, int Z){
        if (color.equals(CombColor.GLASS))
            isGlass = true;
        this.color = color;
        this.Location(X,Y,Z);
    }

    public HoneyComb(CombColor color, CombType type, int X, int Y, int Z){
        if (color.equals(CombColor.GLASS))
            isGlass = true;
        this.color = color;
        this.Location(X,Y,Z);
        this.type = type;
    }

}
