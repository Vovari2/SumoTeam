package me.vovari2.sumoteam.Utils;

import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombType;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.SumoTeam;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Random;

public class StructureUtils {
    public static HoneyComb[] honeyCombs;
    public static HashMap<CombType, HashMap<CombColor, STStructure[]>> structures;
    public static void Initialization(){
        structures = LoadStructures();

        // Создание и заполнение массива полей параметрами сот
        honeyCombs = new HoneyComb[89];
        int i = 0;
        for (int y = 136; y <= 148; y+=4){
            for (int x = -6805; x <= -6693; x+=8){
                for (int z = 1156; z <= 1296; z+=5){
                    if (WorldUtils.isFillRight(x,y,z, CombColor.getAllMaterials())){
                        Material centerMaterial = WorldUtils.world.getType(new Location(WorldUtils.world, x, y, z));
                        if (CombColor.haveMaterial(centerMaterial))
                            honeyCombs[i] = new HoneyComb(CombColor.getColor(centerMaterial),x-4, y, z-4);
                        else honeyCombs[i] = new HoneyComb(CombColor.getColor(WorldUtils.world.getType(new Location(WorldUtils.world,x + 2,y,z))), CombType.HOLE, x-4, y, z-4);
                        i++;
                    }
                }
            }
        }

        // Пробное заполнение полей для проверки
        for (HoneyComb comb : honeyCombs)
            getRandomVariation(structures.get(comb.type).get(comb.color), new Random()).Paste(comb.X, comb.Y, comb.Z);
    }

    public static HashMap<CombType, HashMap<CombColor, STStructure[]>> LoadStructures() {
        HashMap<CombType, HashMap<CombColor, STStructure[]>> structures = new HashMap<>();
        HashMap<CombColor, STStructure[]> fullStructures = new HashMap<>();

        int Y = 121;
        for (CombColor color : CombColor.values())
            if (!color.equals(CombColor.NONE)){
                fullStructures.put(color, LoadArrayStructures(-6730, Y, 1132,5));
                Y += 12;
            }
        structures.put(CombType.FULL, fullStructures);

        HashMap<CombColor, STStructure[]> holeStructures = new HashMap<>();
        Y = 121;
        for (CombColor color : CombColor.values())
            if (!color.equals(CombColor.NONE)){
                holeStructures.put(color, new STStructure[]{LoadStructure(new Location(WorldUtils.world, -6740, Y, 1132))});
                Y+=2;
            }
        structures.put(CombType.HOLE, holeStructures);

        HashMap<CombColor,STStructure[]> breakStructures = new HashMap<>();
        breakStructures.put(CombColor.ICE, LoadArrayStructures(-6740, 151, 1132, 4));

        structures.put(CombType.BREAK, breakStructures);

        return structures;
    }
    public static STStructure[] LoadArrayStructures(int X, int Y, int Z, int count){
        STStructure[] arrayStructures = new STStructure[count];
        for (int i = 0; i < arrayStructures.length; i++)
            arrayStructures[i] = LoadStructure(new Location(WorldUtils.world, X, Y+i*2, Z));
        return arrayStructures;
    }
    public static STStructure LoadStructure(Location pos){
        return new STStructure(pos);
    }

    public static STStructure getIceStructure(HoneyComb honeyComb){
        if (honeyComb.type.equals(CombType.FULL))
            return structures.get(honeyComb.type).get(CombColor.ICE)[honeyComb.variant];
        else return getRandomVariation(structures.get(honeyComb.type).get(CombColor.ICE), new Random());
    }
    public static STStructure getRandomVariation(STStructure[] stStructures, Random R){
        if (stStructures.length < 2)
            return stStructures[0];
        return stStructures[Math.abs(R.nextInt()) % stStructures.length];
    }

    public static HoneyComb[] cloneHoneyCombs(HoneyComb[] oldHoneyCombs){
        HoneyComb[] newHoneyCombs = new HoneyComb[89];
        for(int i = 0; i < newHoneyCombs.length; i++)
            try{
                newHoneyCombs[i] = oldHoneyCombs[i].clone();
            } catch(Exception error) {
                SumoTeam.plugin.getLogger().info(ChatColor.RED + error.getMessage());
            }
        return newHoneyCombs;
    }

    public static void ReplaceComb(Random R){
        HoneyComb[] honeyCombs2 = cloneHoneyCombs(honeyCombs);
        for (HoneyComb comb : honeyCombs2)
            if (comb.isIce)
                comb.isIce = false;
        for (int i = 0; i < SumoTeam.countIce; i++) {
            HoneyComb selectComb;
            do selectComb = honeyCombs2[Math.abs(R.nextInt()) % 89];
            while (selectComb.isIce);
            selectComb.isIce = true;
        }

        for (int i = 0; i < honeyCombs2.length; i++){
            if(honeyCombs2[i].isIce != honeyCombs[i].isIce) {
                STStructure struct;
                if(honeyCombs2[i].isIce)
                    struct = getIceStructure(honeyCombs2[i]);
                else struct = getRandomVariation(structures.get(honeyCombs2[i].type).get(honeyCombs2[i].color), R);
                struct.Paste(honeyCombs2[i].X, honeyCombs2[i].Y, honeyCombs2[i].Z);
            }
        }
        honeyCombs = honeyCombs2;
    }
    public static void RemoveIce(Random R){
        for (HoneyComb comb : honeyCombs)
            if (comb.isIce){
                comb.isIce = false;
                getRandomVariation(structures.get(comb.type).get(comb.color), R).Paste(comb.X, comb.Y, comb.Z);
            }
    }
}
