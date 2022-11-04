package me.vovari2.sumoteam.Utils;

import org.bukkit.*;

import java.util.ArrayList;

public class WorldUtils {
    public static World world;
    public static Location pointCenter;
    public static Location pointDefault;
    public static Location pointCenterZone;
    public static Location pointRedZone;
    public static Location pointBlueZone;
    public static Location pointGreenZone;
    public static Location pointYellowZone;
    public static Location[] fireworkPosition;

    public static void Initialization(World newWorld) {
        world = newWorld;
        pointCenter = new Location(world, -6748.5, 122, 1226.5);
        pointDefault = new Location(world, -6748.5, 69, 1216.5);
        pointCenterZone = new Location(WorldUtils.world, -6748.5, 149, 1226.5);
        pointRedZone = new Location(WorldUtils.world, -6788.5, 141, 1271.5);
        pointBlueZone = new Location(WorldUtils.world, -6708.5, 141, 1181.5);
        pointGreenZone = new Location(WorldUtils.world, -6788.5, 141, 1181.5);
        pointYellowZone = new Location(WorldUtils.world, -6708.5, 141, 1271.5);
        fireworkPosition = new Location[]{new Location(world, -6743.5, 149, 1229.5), new Location(world, -6743.5, 149, 1223.5), new Location(world, -6748.5, 149, 1220.5), new Location(world, -6753.5, 149, 1223.5), new Location(world, -6753.5, 149, 1229.5), new Location(world, -6748.5, 149, 1232.5)};
    }
    public static boolean inMap(Location playerLocation){
        if (!playerLocation.getWorld().equals(WorldUtils.world))
            return false;
        return playerLocation.distance(pointCenter) <= 120;
    }
    public static boolean isOnCenter(Location center, Location pos, float radius){
        return Math.pow(center.getX() - pos.getX(), 2) + Math.pow(center.getZ() - pos.getZ(), 2) <= Math.pow(radius, 2);
    }
    public static boolean isFillRight(int x, int y, int z, ArrayList<Material> materials){
        return materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x + 3, y, z))) && materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x - 3, y, z))) && materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x, y, z + 3))) && materials.contains(WorldUtils.world.getType(new Location(WorldUtils.world, x, y, z - 3)));
    }
    public static void fill(Location pos1, Location pos2, Material material){
        for (int x = Math.min(pos1.getBlockX(), pos2.getBlockX()); x <= Math.max(pos1.getBlockX(), pos2.getBlockX()); x++)
            for (int y = Math.min(pos1.getBlockY(), pos2.getBlockY()); y <= Math.max(pos1.getBlockY(), pos2.getBlockY()); y++)
                for (int z = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); z <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); z++)
                    world.getBlockAt(x, y, z).setType(material);
    }

    public static void replace(Location pos1, Location pos2, Material newMaterial, Material replaceMaterial){
        for (int x = Math.min(pos1.getBlockX(), pos2.getBlockX()); x <= Math.max(pos1.getBlockX(), pos2.getBlockX()); x++)
            for (int y = Math.min(pos1.getBlockY(), pos2.getBlockY()); y <= Math.max(pos1.getBlockY(), pos2.getBlockY()); y++)
                for (int z = Math.min(pos1.getBlockZ(), pos2.getBlockZ()); z <= Math.max(pos1.getBlockZ(), pos2.getBlockZ()); z++)
                    if (world.getBlockAt(x, y, z).getType() == replaceMaterial)
                        world.getBlockAt(x, y, z).setType(newMaterial);
    }

    public static Location add(Location loc, int X, int Y, int Z){
        return new Location(world, loc.getX() + X, loc.getY() + Y, loc.getZ() + Z);
    }
    public static Location add(Location A, double X, double Y, double Z){
        return new Location(WorldUtils.world, A.getX() + X, A.getY() + Y, A.getZ() + Z);
    }
    public static Location subtract(Location loc, int X, int Y, int Z){
        return new Location(world, loc.getX() - X, loc.getY() - Y, loc.getZ() - Z);
    }
}
