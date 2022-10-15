package me.vovari2.sumoteam.Utils;

import org.bukkit.*;

import java.util.ArrayList;

public class WorldUtils {
    public static World world;
    public static Location pointCenter;
    public static Location pointDefault;
    public static Location[] fireworkPosition = new Location[]{new Location(world, -6748.5, 149, 1216.5), new Location(world, -6740.5, 149, 1221.5), new Location(world, -6740.5, 149, 1231.5), new Location(world, -6748.5, 149, 1236.5), new Location(world, -6756.5, 149, 1231.5), new Location(world, -6756.5, 149, 1221.5)};

    public static void Initialization(World newWorld) {
        world = newWorld;
        pointCenter = new Location(world, -6748.5, 122, 1226.5);
        pointDefault = new Location(world, -6748.5, 69, 1216.5);
    }
    public static boolean inMap(Location playerLocation){
        if (!playerLocation.getWorld().equals(WorldUtils.world))
            return false;
        return playerLocation.distance(pointCenter) <= 80;
    }

    public static void replace(Location pos1, Location pos2, Material newMaterial, Material replaceMaterial){
        for (int x = Math.min(pos1.getBlockX(), pos2.getBlockX()); x <= Math.max(pos1.getBlockX(), pos2.getBlockX()); x++)
            for (int y = Math.min(pos1.getBlockY(), pos2.getBlockY()); y <= Math.max(pos1.getBlockY(), pos2.getBlockY()); y++)
                for (int z = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); z <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); z++)
                    if (world.getBlockAt(x, y, z).getType() == replaceMaterial)
                        world.getBlockAt(x, y, z).setType(newMaterial);
    }

    public static Material[][] flip(Material[][] structure, STFlip flip){
        int sizeX = structure.length, sizeZ = structure[0].length;
        Material[][] newStructure = new Material[sizeX][sizeZ];
        switch (flip){
            case X:{
                int newZ;
                for (int x = 0; x < sizeX; x++){
                    newZ = 8;
                    for (int z = 0; z < sizeZ; z++){
                        newStructure[x][z] = structure[x][newZ];
                        newZ--;
                    }
                }
                structure = newStructure;
            } break;
            case Z:{
                int newX;
                for (int z = 0; z < sizeZ; z++){
                    newX = 8;
                    for (int x = 0; x < sizeX; x++){
                        newStructure[x][z] = structure[newX][z];
                        newX--;
                    }
                }
                structure = newStructure;
            } break;
        }
        return structure;
    }
}
