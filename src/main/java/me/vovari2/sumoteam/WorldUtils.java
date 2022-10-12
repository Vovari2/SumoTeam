package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombType;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.Honeycomb.StructureHoneyComb;
import org.bukkit.*;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.structure.StructureManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldUtils {
    static World world;
    static Location pointCenter;
    static Location pointDefault;
    static Location[] fireworkPosition = new Location[]{new Location(world, -6748.5, 149, 1216.5), new Location(world, -6740.5, 149, 1221.5), new Location(world, -6740.5, 149, 1231.5), new Location(world, -6748.5, 149, 1236.5), new Location(world, -6756.5, 149, 1231.5), new Location(world, -6756.5, 149, 1221.5)};

    public static StructureManager structManager;
    public static HoneyComb[] honeyCombs;
    public static HashMap<CombType, HashMap<CombColor, StructureHoneyComb>> structures;
    public static HashMap<Material, CombColor> materialColor;

    static void Initialization(World newWorld){
        world = newWorld;
        pointCenter = new Location(world, -6748.5, 122, 1226.5);
        pointDefault = new Location(world,-6748.5, 69, 1216.5);

        structManager = Bukkit.getStructureManager();
        structures = StructureHoneyComb.LoadStructures();

        // Дополнительная переменная для конвертирования из материала в цвет соты
        materialColor = new HashMap<>();
        materialColor.put(Material.RED_CONCRETE, CombColor.RED);
        materialColor.put(Material.RED_WOOL, CombColor.RED);
        materialColor.put(Material.BLUE_CONCRETE, CombColor.BLUE);
        materialColor.put(Material.BLUE_WOOL, CombColor.BLUE);
        materialColor.put(Material.LAPIS_BLOCK, CombColor.BLUE);
        materialColor.put(Material.GREEN_CONCRETE, CombColor.GREEN);
        materialColor.put(Material.GREEN_WOOL, CombColor.GREEN);
        materialColor.put(Material.GREEN_TERRACOTTA, CombColor.GREEN);
        materialColor.put(Material.YELLOW_CONCRETE, CombColor.YELLOW);
        materialColor.put(Material.YELLOW_WOOL, CombColor.YELLOW);
        materialColor.put(Material.GOLD_BLOCK, CombColor.YELLOW);
        materialColor.put(Material.WHITE_STAINED_GLASS, CombColor.GLASS);
        materialColor.put(Material.GRAY_STAINED_GLASS, CombColor.GLASS);
        materialColor.put(Material.LIGHT_GRAY_STAINED_GLASS, CombColor.GLASS);

        // Создание и заполнение массива полей параметрами сот
        honeyCombs = new HoneyComb[89];
        int i = 0;
        for (int y = 136; y <= 148; y+=4){
            for (int x = -6805; x <= -6693; x+=8){
                for (int z = 1156; z <= 1296; z+=5){
                    if (materialColor.containsKey(world.getType(new Location(world, x+4, y, z))) && isFillRight(x,y,z, materialColor.get(world.getType(new Location(world, x+4, y, z))).getMaterials())){
                        Material centerMaterial = world.getType(new Location(world, x, y, z));
                        if (materialColor.containsKey(centerMaterial)){
                            if (world.getType(new Location(world,x + 2,y,z)).equals(Material.EMERALD_BLOCK) || world.getType(new Location(world,x - 2,y,z)).equals(Material.EMERALD_BLOCK)){
                                honeyCombs[i] = new HoneyComb(materialColor.get(centerMaterial), CombType.TRAMPOLINE, x-4, y, z-4);
                                switch(materialColor.get(centerMaterial)){
                                    case BLUE: {
                                        honeyCombs[i].rotation = StructureRotation.CLOCKWISE_180;
                                        honeyCombs[i].Location(x+4, y, z+4);
                                    } break;
                                    case GREEN: {
                                        honeyCombs[i].mirror = Mirror.LEFT_RIGHT;
                                        honeyCombs[i].Location(x-4, y, z+4);
                                    } break;
                                    case YELLOW: {
                                        honeyCombs[i].rotation = StructureRotation.CLOCKWISE_180;
                                        honeyCombs[i].mirror = Mirror.LEFT_RIGHT;
                                        honeyCombs[i].Location(x+4,y,z-4);
                                    } break;
                                }
                            }
                            else honeyCombs[i] = new HoneyComb(materialColor.get(centerMaterial),x-4, y, z-4);
                            i++;
                        }
                        else{
                            Material sideMaterial = world.getType(new Location(world,x + 2,y,z));
                            if (materialColor.containsKey(sideMaterial)){
                                honeyCombs[i] = new HoneyComb(materialColor.get(sideMaterial), CombType.HOLE, x-4, y, z-4);
                                i++;
                            }
                        }
                    }
                }
            }
        }

        // Пробное заполнение полей для проверки
        Random R = new Random();
        for (HoneyComb comb : honeyCombs) {
            structures.get(comb.type).get(comb.color).getRandomVariation(R).place(new Location(world, comb.X, comb.Y, comb.Z), false, comb.rotation, comb.mirror, 0, 1, R);
        }
    }

    static boolean inMap(Location playerLocation){
        if (!playerLocation.getWorld().equals(WorldUtils.world))
            return false;
        return playerLocation.distance(pointCenter) <= 80;
    }

    static void replace(Location pos1, Location pos2, Material newMaterial, Material replaceMaterial){
        for (int x = Math.min(pos1.getBlockX(), pos2.getBlockX()); x <= Math.max(pos1.getBlockX(), pos2.getBlockX()); x++)
            for (int y = Math.min(pos1.getBlockY(), pos2.getBlockY()); y <= Math.max(pos1.getBlockY(), pos2.getBlockY()); y++)
                for (int z = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); z <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); z++)
                    if (world.getBlockAt(x, y, z).getType() == replaceMaterial)
                        world.getBlockAt(x, y, z).setType(newMaterial);
    }

    static boolean isFillRight(int x, int y, int z, ArrayList<Material> materials){
        return materials.contains(world.getType(new Location(world, x + 3, y, z))) && materials.contains(world.getType(new Location(world, x - 3, y, z))) && materials.contains(world.getType(new Location(world, x, y, z + 3))) && materials.contains(world.getType(new Location(world, x, y, z - 3)));
    }

    static HoneyComb[] cloneHoneyCombs(HoneyComb[] oldHoneyCombs){
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
