package me.vovari2.sumoteam.Utils;

import org.bukkit.Location;
import org.bukkit.Material;

public class STStructure {
    private Material[][] structure;
    private final static Material emptyBlock = Material.PINK_CONCRETE;
    public STStructure(Location pos){
        Load(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
    }
    public void Load(int X, int Y, int Z){
        structure = new Material[9][9];
        for (int x = 0; x < 9; x++)
            for (int z = 0; z < 9; z++)
                structure[x][z] = WorldUtils.world.getType(new Location(WorldUtils.world, X+x, Y, Z+z));
    }
    public void Paste(int X, int Y, int Z, STFlip[] flips){
        Material[][] pasteStructure = this.structure;
        for (STFlip flip : flips)
            pasteStructure = WorldUtils.flip(pasteStructure, flip);
        for (int x = 0; x < 9; x++)
            for (int z = 0; z < 9; z++)
                if (!pasteStructure[x][z].equals(emptyBlock))
                    WorldUtils.world.setType(new Location(WorldUtils.world, X+x, Y, Z+z), pasteStructure[x][z]);
    }
}
