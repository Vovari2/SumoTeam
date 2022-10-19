package me.vovari2.sumoteam.Utils;

import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombType;
import me.vovari2.sumoteam.Honeycomb.CombStructures;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.SumoTeam;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Random;

public class StructureUtils {
    public static HoneyComb[] honeyCombs;
    public static HashMap<CombType, HashMap<CombColor, CombStructures>> structures;
    public static void Initialization(){
        structures = CombStructures.LoadStructures();

        // Создание и заполнение массива полей параметрами сот
        honeyCombs = new HoneyComb[89];
        int i = 0;
        for (int y = 136; y <= 148; y+=4){
            for (int x = -6805; x <= -6693; x+=8){
                for (int z = 1156; z <= 1296; z+=5){
                    if (CombStructures.isFillRight(x,y,z, CombColor.getAllMaterials())){
                        Material centerMaterial = WorldUtils.world.getType(new Location(WorldUtils.world, x, y, z));
                        if (CombColor.haveMaterial(centerMaterial)){
                            if (WorldUtils.world.getType(new Location(WorldUtils.world,x + 2,y,z)).equals(Material.EMERALD_BLOCK) || WorldUtils.world.getType(new Location(WorldUtils.world,x - 2,y,z)).equals(Material.EMERALD_BLOCK)){
                                honeyCombs[i] = new HoneyComb(CombColor.getColor(centerMaterial), CombType.TRAMPOLINE, x-4, y, z-4);
                                switch (CombColor.getColor(centerMaterial)){
                                    case GREEN: honeyCombs[i].flips = new STFlip[]{STFlip.X}; break;
                                    case BLUE: honeyCombs[i].flips = new STFlip[]{STFlip.X, STFlip.Z}; break;
                                    case YELLOW: honeyCombs[i].flips = new STFlip[]{STFlip.Z}; break;
                                }
                            }
                            else honeyCombs[i] = new HoneyComb(CombColor.getColor(centerMaterial),x-4, y, z-4);
                        }
                        else {
                            honeyCombs[i] = new HoneyComb(CombColor.getColor(WorldUtils.world.getType(new Location(WorldUtils.world,x + 2,y,z))), CombType.HOLE, x-4, y, z-4);
                        }
                        i++;
                    }
                }
            }
        }
        honeyCombs[23].teamSpawn = true;
        honeyCombs[28].teamSpawn = true;
        honeyCombs[53].teamSpawn = true;
        honeyCombs[58].teamSpawn = true;

        // Пробное заполнение полей для проверки
        Random R = new Random();
        for (HoneyComb comb : honeyCombs) {
            structures.get(comb.type).get(comb.color).getRandomVariation(R).Paste(comb.X, comb.Y, comb.Z , comb.flips);
        }
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
}
