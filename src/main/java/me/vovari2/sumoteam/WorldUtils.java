package me.vovari2.sumoteam;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.ArrayList;

public class WorldUtils {
    static World world;
    static Location pointCenter;
    static Location pointDefault;
    static Location[] fireworkPosition = new Location[]{new Location(world, -6748.5, 149, 1216.5), new Location(world, -6740.5, 149, 1221.5), new Location(world, -6740.5, 149, 1231.5), new Location(world, -6748.5, 149, 1236.5), new Location(world, -6756.5, 149, 1231.5), new Location(world, -6756.5, 149, 1221.5)};
    static void Initialization(World newWorld){
        world = newWorld;
        pointCenter = new Location(world, -6748.5, 122, 1226.5);
        pointDefault = new Location(world,-6748.5, 69, 1216.5);
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
}
