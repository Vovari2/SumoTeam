package me.vovari2.sumoteam.Utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public class VectorUtils {
    public static Location centerPoint;
    public static double radius;
    public static void Initialization(){
        centerPoint = new Location(WorldUtils.world, -6748.5, 149, 1226.5);
        radius = 4.5;
    }
    public static Location RandomPointSphere(Location center){
        int angleGR = Math.abs(new Random().nextInt(360));
        double angle = Math.toRadians(angleGR), X = 4.5 * Math.cos(angle);
        return WorldUtils.add(center, X, Math.abs(new Random().nextDouble(6)), X * Math.tan(angle));
    }
    public static Vector getVector(Location A, Location B){
        if (A.getY() - B.getY() == 0)
            return new Vector((A.getX() - B.getX()) * 0.25, 0.9, (A.getZ() - B.getZ()) * 0.25);
        else return new Vector((A.getX() - B.getX()) * 0.25, (A.getY() - B.getY()) * 0.9, (A.getZ() - B.getZ()) * 0.25);
    }
}
