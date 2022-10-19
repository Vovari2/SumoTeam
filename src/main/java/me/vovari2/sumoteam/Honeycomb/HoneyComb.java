package me.vovari2.sumoteam.Honeycomb;

import me.vovari2.sumoteam.Utils.STFlip;
import me.vovari2.sumoteam.Utils.WorldUtils;
import org.bukkit.Location;


public class HoneyComb implements Cloneable{

    public boolean isIce = false;
    public boolean isGlass = false;

    public CombColor color;
    public CombType type = CombType.FULL;

    public int X;
    public int Y;
    public int Z;
    public void Location(int X, int Y, int Z) { this.X = X; this.Y = Y; this.Z = Z; }

    public STFlip[] flips = new STFlip[]{};


    public boolean stageAfterBreak = false;
    public boolean stageBeforeBreak = false;
    public boolean havePlayer = false;
    public boolean teamSpawn = false;

    public boolean timePlus = false;
    public int time = 0;


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

    public Location getCenterLocation(){
        return new Location(WorldUtils.world, X+4.5, Y, Z+4.5);
    }

    public HoneyComb clone() throws CloneNotSupportedException{
        return (HoneyComb) super.clone();
    }


}
