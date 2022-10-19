package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombStructures;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Utils.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SumoTeamTask extends BukkitRunnable {

    public static int Tick = 0;
    @Override
    public void run() {
        if (SumoTeam.inLobby)
            return;
        if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && Tick % 20 == 0){
            for (STPlayer stPlayer : PlayerUtils.players.values())
                if (stPlayer.inField)
                    for (int i = 0; i < StructureUtils.honeyCombs.length; i++){
                        if (!StructureUtils.honeyCombs[i].teamSpawn && StructureUtils.honeyCombs[i].isIce && !StructureUtils.honeyCombs[i].havePlayer){
                            HoneyComb comb = StructureUtils.honeyCombs[i];
                            if (!comb.stageBeforeBreak && !comb.stageAfterBreak && comb.getCenterLocation().distance(stPlayer.player.getLocation()) < 4.5){
                                comb.havePlayer = true;
                                comb.timePlus = true;
                                if (comb.time == 0){
                                    WorldUtils.world.spawnParticle(Particle.CRIT, comb.getCenterLocation().add(0.0D, 1.3D, 0.0D), 100, 1.7D, 0.1D,1.7D, 0.2D);
                                    WorldUtils.world.playSound(comb.getCenterLocation(), Sound.BLOCK_STONE_PLACE, 1, 1);
                                    comb.stageBeforeBreak = true;
                                }
                            }
                            else if (!comb.stageBeforeBreak && !comb.stageAfterBreak && comb.getCenterLocation().distance(stPlayer.player.getLocation()) > 4.5){
                                comb.time = 0;
                                comb.timePlus = false;
                            }
                            else if (comb.stageBeforeBreak && StructureUtils.honeyCombs[i].time == 1){
                                comb.havePlayer = true;
                                for (Material material : CombColor.ICE.getMaterials()){
                                    WorldUtils.replace(comb.getCenterLocation().add(4, 0, 1), comb.getCenterLocation().subtract(4, 0, 1), Material.AIR, material);
                                    WorldUtils.replace(comb.getCenterLocation().add(3, 0, 3), comb.getCenterLocation().subtract(3, 0, 3), Material.AIR, material);
                                    WorldUtils.replace(comb.getCenterLocation().add(2, 0, 4), comb.getCenterLocation().subtract(2, 0, 4), Material.AIR, material);
                                }
                                comb.stageAfterBreak = true;
                                comb.stageBeforeBreak = false;
                            }
                            else if (comb.stageAfterBreak && StructureUtils.honeyCombs[i].time == 5){
                                comb.havePlayer = true;
                                comb.timePlus = false;
                                comb.stageAfterBreak = false;
                                comb.time = 0;
                                CombStructures.getIceStructure(comb).Paste(comb.X, comb.Y, comb.Z, comb.flips);
                            }
                        }
                    }
            for (HoneyComb comb : StructureUtils.honeyCombs){
                if (comb.timePlus)
                    comb.time++;
                comb.havePlayer = false;
            }
        }
        else if (Tick >= 400){
            if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && SumoTeam.countIce > 0 && SumoTeam.countIce < 89){
                long countMillis = System.currentTimeMillis();
                ReplaceComb(new Random());
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
                if(honeyCombs2[i].isIce)
                    struct = CombStructures.getIceStructure(honeyCombs2[i]);
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
