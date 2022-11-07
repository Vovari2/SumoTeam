package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Utils.PlayerUtils;
import me.vovari2.sumoteam.Utils.STPlayer;
import me.vovari2.sumoteam.Utils.StructureUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class SumoTeamSeconds extends BukkitRunnable {

    public static int Seconds = 0;
    @Override
    public void run() {
        for (STPlayer stPlayer : PlayerUtils.players.values())
            if (stPlayer.inJump)
                stPlayer.inJump = false;
        if (SumoTeam.inLobby || SumoTeam.gameOver)
            return;
        if (Seconds >= 20){
            if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && SumoTeam.countIce > 0 && SumoTeam.countIce < 89){
                long countMillis = System.currentTimeMillis();
                StructureUtils.ReplaceComb(new Random());
                SumoTeam.plugin.getLogger().info("Поле обновлено (" + SumoTeam.countIce + "): " + (System.currentTimeMillis() - countMillis) + " ms");
            }
            Seconds = 0;
        }
        Seconds++;
    }
}
