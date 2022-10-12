package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombType;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.Modes.STFieldMode;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.structure.Structure;

import java.util.Random;

public class SumoTeamTask extends BukkitRunnable {

    public static int Tick = 0;
    @Override
    public void run() {
        if (SumoTeam.inLobby)
            return;
        if (Tick >= 400){
            long countMillis;
            countMillis = System.currentTimeMillis();
            if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && SumoTeam.countIce > 0 && SumoTeam.countIce < 89){
                Random R = new Random();
                ReplaceComb(R);
            }
            SumoTeam.plugin.getLogger().info("Поле обновлено (" + SumoTeam.countIce + "): " + (System.currentTimeMillis() - countMillis) + " ms");
            Tick = 0;
        }
        Tick++;
    }
    public static void ReplaceComb(Random R){
        HoneyComb[] honeyCombs2 = WorldUtils.cloneHoneyCombs(WorldUtils.honeyCombs);
        for (HoneyComb comb : honeyCombs2){
            if (comb.isIce)
                comb.isIce = false;
        }
        for (int i = 0; i < SumoTeam.countIce; i++) {
            HoneyComb selectComb;
            do {
                selectComb = honeyCombs2[Math.abs(R.nextInt()) % 89];
            } while (selectComb.isIce);
            selectComb.isIce = true;
        }
        int Index = 0;
        for (int i = 0; i < honeyCombs2.length; i++){
            if(honeyCombs2[i].isIce != WorldUtils.honeyCombs[i].isIce) {
                Structure struct;
                if(honeyCombs2[i].isIce){
                    Index++;
                    if (honeyCombs2[i].type.equals(CombType.TRAMPOLINE)) {
                        if (honeyCombs2[i].isGlass)
                            struct = WorldUtils.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[1];
                        else
                            struct = WorldUtils.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[0];
                    } else struct = WorldUtils.structures.get(honeyCombs2[i].type).get(CombColor.ICE).getRandomVariation(R);
                }
                else{
                    Index++;
                    struct =  WorldUtils.structures.get(honeyCombs2[i].type).get(honeyCombs2[i].color).getRandomVariation(R);
                }
                struct.place(new Location(WorldUtils.world, honeyCombs2[i].X, honeyCombs2[i].Y, honeyCombs2[i].Z), false, honeyCombs2[i].rotation, honeyCombs2[i].mirror, 0, 1, new Random());
            }
        }
        SumoTeam.plugin.getLogger().info("" + Index);
        WorldUtils.honeyCombs = WorldUtils.cloneHoneyCombs(honeyCombs2);
    }
    public static void RemoveIce(Random R){
        for (HoneyComb comb : WorldUtils.honeyCombs){
            if (!comb.isIce)
                continue;
            comb.isIce = false;
            WorldUtils.structures.get(comb.type).get(comb.color).getRandomVariation(R).place(new Location(WorldUtils.world, comb.X, comb.Y, comb.Z), false, comb.rotation, comb.mirror, 0, 1, new Random());
        }
    }
}
