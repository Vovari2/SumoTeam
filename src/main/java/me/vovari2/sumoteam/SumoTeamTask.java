package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombType;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Utils.STStructure;
import me.vovari2.sumoteam.Utils.StructureUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SumoTeamTask extends BukkitRunnable {

    public static int Tick = 0;
    @Override
    public void run() {
        if (SumoTeam.inLobby)
            return;
        if (Tick >= 400){
            if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && SumoTeam.countIce > 0 && SumoTeam.countIce < 89){
                long countMillis = System.currentTimeMillis();
                Random R = new Random();
                ReplaceComb(R);
                SumoTeam.plugin.getLogger().info("Поле обновлено (" + SumoTeam.countIce + "): " + (System.currentTimeMillis() - countMillis) + " ms");
            }
            Tick = 0;
        }
        Tick++;
    }
    public static void ReplaceComb(Random R){
        HoneyComb[] honeyCombs2 = StructureUtils.cloneHoneyCombs(StructureUtils.honeyCombs);
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
            if(honeyCombs2[i].isIce != StructureUtils.honeyCombs[i].isIce) {
                STStructure struct;
                if(honeyCombs2[i].isIce){
                    if (honeyCombs2[i].type.equals(CombType.TRAMPOLINE)) {
                        if (honeyCombs2[i].isGlass)
                            struct = StructureUtils.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[0];
                        else struct = StructureUtils.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[1];
                    } else struct = StructureUtils.structures.get(honeyCombs2[i].type).get(CombColor.ICE).getRandomVariation(R);
                }
                else struct =  StructureUtils.structures.get(honeyCombs2[i].type).get(honeyCombs2[i].color).getRandomVariation(R);
                struct.Paste(honeyCombs2[i].X, honeyCombs2[i].Y, honeyCombs2[i].Z, honeyCombs2[i].flips);
            }
        }
        StructureUtils.honeyCombs = honeyCombs2;
    }
    public static void RemoveIce(Random R){
        for (HoneyComb comb : StructureUtils.honeyCombs)
            if (comb.isIce){
                comb.isIce = false;
                StructureUtils.structures.get(comb.type).get(comb.color).getRandomVariation(R).Paste(comb.X, comb.Y, comb.Z, comb.flips);
            }
    }
}
