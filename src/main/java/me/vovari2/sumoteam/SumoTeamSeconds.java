package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.Random;

public class SumoTeamSeconds extends BukkitRunnable {

    public static int Seconds = 0;
    public static int startCounter = 11;
    @Override
    public void run() {
        for (STPlayer stPlayer : PlayerUtils.players.values())
            if (stPlayer.inJump)
                stPlayer.inJump = false;
        if ((startCounter > 0 && startCounter < 6) || startCounter == 10){
            for (STPlayer stPlayer : PlayerUtils.players.values()){
                if (startCounter == 10)
                    SumoTeam.InfoMessage(stPlayer.player);
                stPlayer.player.playSound(stPlayer.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
                stPlayer.player.showTitle(Title.title(Component.text(startCounter, ComponentUtils.Gold), Component.text(""), Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ofMillis(500))));
            }
        }
        if (startCounter > -1 && startCounter < 11){
            startCounter--;
            if(startCounter == 0){
                SumoTeam.inLobby = false;

                SumoTeamSeconds.Seconds = 19;

                SumoTeamCommands.StartTeam(SumoTeam.teams.get(STName.RED));
                SumoTeamCommands.StartTeam(SumoTeam.teams.get(STName.BLUE));
                SumoTeamCommands.StartTeam(SumoTeam.teams.get(STName.GREEN));
                SumoTeamCommands.StartTeam(SumoTeam.teams.get(STName.YELLOW));

                ScoreboardUtils.scores = ScoreboardUtils.CreateScores();
                ScoreboardUtils.LoadScores();

                for (STPlayer stPlayer : PlayerUtils.players.values())
                    stPlayer.player.playSound(stPlayer.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1F, 1F);
            }
        }
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
