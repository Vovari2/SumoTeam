package me.vovari2.sumoteam.Honeycomb;

import me.vovari2.sumoteam.Utils.STStructure;
import me.vovari2.sumoteam.Utils.StructureUtils;
import me.vovari2.sumoteam.Utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CombStructures {
    private final STStructure[] structures;

    public CombStructures(STStructure[] structures){
        this.structures = structures;
    }
    public STStructure getRandomVariation(Random R){
        if (structures.length < 2)
            return structures[0];
        return structures[Math.abs(R.nextInt()) % structures.length];
    }
    public STStructure[] getStructures(){
        return structures;
    }



    public static HashMap<CombType, HashMap<CombColor, CombStructures>> LoadStructures() {
        HashMap<CombType, HashMap<CombColor, CombStructures>> structures = new HashMap<>();
        HashMap<CombColor, CombStructures> fullStructures = new HashMap<>();

        int Y = 121;
        for (CombColor color : CombColor.values())
            if (!color.equals(CombColor.NONE)){
                fullStructures.put(color, new CombStructures(LoadArrayStructures(-6730, Y, 1132)));
                Y += 12;
            }
        structures.put(CombType.FULL, fullStructures);

        HashMap<CombColor, CombStructures> holeStructures = new HashMap<>();
        Y = 121;
        for (CombColor color : CombColor.values())
            if (!color.equals(CombColor.NONE)){
                holeStructures.put(color, new CombStructures(new STStructure[]{LoadStructure(new Location(WorldUtils.world, -6740, Y, 1132))}));
                Y+=2;
            }
        structures.put(CombType.HOLE, holeStructures);

        HashMap<CombColor, CombStructures> tramStructures = new HashMap<>();
        Y = 135;
        for (CombColor color : CombColor.values())
            if (!color.equals(CombColor.NONE)){
                if (!color.equals(CombColor.ICE))
                    tramStructures.put(color, new CombStructures(new STStructure[]{LoadStructure(new Location(WorldUtils.world, -6740, Y, 1132))}));
                else tramStructures.put(color, new CombStructures(new STStructure[]{LoadStructure(new Location(WorldUtils.world, -6740, Y, 1132)), LoadStructure(new Location(WorldUtils.world, -6740, Y+2, 1132))}));
                Y+=2;
            }
        structures.put(CombType.TRAMPOLINE, tramStructures);

        return structures;
    }
    public static STStructure[] LoadArrayStructures(int X, int Y, int Z){
        STStructure[] arrayStructures = new STStructure[5];
        for (int i = 0; i < arrayStructures.length; i++)
            arrayStructures[i] = LoadStructure(new Location(WorldUtils.world, X, Y+i*2, Z));
        return arrayStructures;
    }
    public static STStructure LoadStructure(Location pos){
        return new STStructure(pos);
    }


    public static boolean isFillRight(int x, int y, int z, ArrayList<Material> materials){
        return materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x + 3, y, z))) && materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x - 3, y, z))) && materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x, y, z + 3))) && materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x, y, z - 3)));
    }
    public static STStructure getIceStructure(HoneyComb honeyComb){
        if (honeyComb.type.equals(CombType.TRAMPOLINE)) {
            if (honeyComb.isGlass)
                return StructureUtils.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[0];
            else return StructureUtils.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[1];
        } else return StructureUtils.structures.get(honeyComb.type).get(CombColor.ICE).getRandomVariation(new Random());
    }
}
