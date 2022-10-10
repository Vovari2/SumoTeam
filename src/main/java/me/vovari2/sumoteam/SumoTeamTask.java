package me.vovari2.sumoteam;

import me.vovari2.events.SumoTeam.Honeycomb.CombColor;
import me.vovari2.events.SumoTeam.Honeycomb.CombType;
import me.vovari2.events.SumoTeam.Honeycomb.HoneyComb;
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
            if (SumoTeam.fieldMode.equals(SumoTeamFieldMode.ICE_PLATFORM) && SumoTeam.countIce > 0 && SumoTeam.countIce < 89){
                Random R = new Random();
                RemoveIce(R);
                for (int i = 0; i < SumoTeam.countIce; i++) {
                    if (SumoTeam.honeyCombs[i].isIce)
                        continue;
                    HoneyComb selectComb;
                    do {
                        selectComb = SumoTeam.honeyCombs[R.nextInt(SumoTeam.honeyCombs.length - 1)];
                    } while (selectComb.isIce);
                    selectComb.isIce = true;
                    Structure struct;
                    if (selectComb.type.equals(CombType.TRAMPOLINE)) {
                        if (selectComb.isGlass)
                            struct = SumoTeam.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[1];
                        else
                            struct = SumoTeam.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[0];
                    } else struct = SumoTeam.structures.get(selectComb.type).get(CombColor.ICE).getRandomVariation(R);
                    struct.place(new Location(SumoTeam.world, selectComb.X, selectComb.Y, selectComb.Z), false, selectComb.rotation, selectComb.mirror, 0, 1, new Random());
                }
            }
            Tick = 0;
        }
        Tick++;
    }
    public static void RemoveIce(Random R){
        for (HoneyComb comb : SumoTeam.honeyCombs){
            if (!comb.isIce)
                continue;
            SumoTeam.structures.get(comb.type).get(comb.color).getRandomVariation(R).place(new Location(SumoTeam.world, comb.X, comb.Y, comb.Z), false, comb.rotation, comb.mirror, 0, 1, new Random());
            comb.isIce = false;
        }
    }
}
