package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Honeycomb.BreakType;
import me.vovari2.sumoteam.Honeycomb.CombColor;
import me.vovari2.sumoteam.Honeycomb.CombType;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Modes.STGameMode;
import me.vovari2.sumoteam.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

public class SumoTeamTicks extends BukkitRunnable {

    public static int Ticks = 0;
    private static int ScorColor = 170;
    @Override
    public void run() {
        if (SumoTeam.inLobby || SumoTeam.gameOver)
            return;
        if (Ticks >= 96 && Ticks < 128)
            ScorColor-=2;
        else if (Ticks >= 128 && Ticks < 160)
            ScorColor+=2;
        ScoreboardUtils.objective.displayName(Component.text("SUMOTEAM", TextColor.color(252, ScorColor ,9 ), TextDecoration.BOLD));
        if (SumoTeam.gameMode.equals(STGameMode.KING_OF_THE_HILL)) {
            for (int i = 0; i < 3; i++){
                WorldUtils.world.spawnParticle(Particle.END_ROD, VectorUtils.RandomPointSphere(WorldUtils.pointCenterZone), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                WorldUtils.world.spawnParticle(Particle.REDSTONE, VectorUtils.RandomPointSphere(WorldUtils.pointRedZone), 1, 0.0D, 0.0D, 0.0D, 0.0D, new Particle.DustOptions(Color.fromRGB(204, 46, 38), 2.0F));
                WorldUtils.world.spawnParticle(Particle.REDSTONE, VectorUtils.RandomPointSphere(WorldUtils.pointBlueZone), 1, 0.0D, 0.0D, 0.0D, 0.0D, new Particle.DustOptions(Color.fromRGB(60, 68, 170), 2.0F));
                WorldUtils.world.spawnParticle(Particle.REDSTONE, VectorUtils.RandomPointSphere(WorldUtils.pointGreenZone), 1, 0.0D, 0.0D, 0.0D, 0.0D, new Particle.DustOptions(Color.fromRGB(94, 124, 22), 2.0F));
                WorldUtils.world.spawnParticle(Particle.REDSTONE, VectorUtils.RandomPointSphere(WorldUtils.pointYellowZone), 1, 0.0D, 0.0D, 0.0D, 0.0D, new Particle.DustOptions(Color.fromRGB(254, 237, 61), 2.0F));
            }
            for (STName teamName : STName.listGameTeam){
                STTeam team = SumoTeam.teams.get(teamName);
                team.countCenter = 0;
                for (String playerName: team.team.getEntries())
                    if (WorldUtils.isOnCenter(VectorUtils.centerPoint, PlayerUtils.players.get(playerName).player.getLocation(), 4.5F))
                        team.countCenter++;
                team.pointsF += 0.1 * team.countCenter;
                if ((int) team.pointsF > team.points)   {
                    team.points = (int) team.pointsF;
                    ScoreboardUtils.ReloadScores();
                    if (team.points >= SumoTeam.maxPoint)
                        team.WinTeam();
                }
            }
        }
        if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && Ticks % 2 == 0){
            for (int i = 0; i < StructureUtils.honeyCombs.length; i++){
                HoneyComb comb = StructureUtils.honeyCombs[i];
                if (comb.isIce && comb.typeBreak.equals(BreakType.NONE)){
                    for (STPlayer stPlayer : PlayerUtils.players.values()){
                        if (stPlayer.inField){
                            if (comb.getCenterLocation().distance(stPlayer.player.getLocation()) < 4.5){
                                comb.timePlus = true;
                                comb.havePlayer = true;
                                break;
                            }
                            else comb.havePlayer = false;
                        }
                    }
                    if (comb.time > 9 && comb.time < 41 && comb.time % 10 == 0 && comb.havePlayer){
                        if (comb.time == 10 && comb.type.equals(CombType.HOLE))
                            comb.time = 20;
                        WorldUtils.world.playSound(comb.getCenterLocation(),Sound.BLOCK_GLASS_BREAK, (float) (comb.time / 10 * 0.2),0.6F);
                        StructureUtils.structures.get(CombType.BREAK).get(CombColor.ICE)[comb.time / 10 - 1].Paste(comb.X, comb.Y, comb.Z);
                        if (comb.type.equals(CombType.HOLE))
                            WorldUtils.fill(WorldUtils.subtract(comb.getCenterLocation(), 1, 0, 1), WorldUtils.add(comb.getCenterLocation(), 1, 0, 1), Material.AIR);
                    }
                    else if (comb.time == 50 && comb.havePlayer){
                        WorldUtils.world.playSound(comb.getCenterLocation(),Sound.BLOCK_GLASS_BREAK, 1,0.6F);
                        for (Material material : new Material[]{Material.PACKED_ICE, Material.BLUE_ICE, Material.LIGHT_BLUE_STAINED_GLASS}){
                            WorldUtils.replace(comb.getCenterLocation().add(4, 0, 1), comb.getCenterLocation().subtract(4, 0, 1), Material.AIR, material);
                            WorldUtils.replace(comb.getCenterLocation().add(3, 0, 3), comb.getCenterLocation().subtract(3, 0, 3), Material.AIR, material);
                            WorldUtils.replace(comb.getCenterLocation().add(2, 0, 4), comb.getCenterLocation().subtract(2, 0, 4), Material.AIR, material);
                        }
                    }
                    else if (comb.time >= 70){
                        StructureUtils.getIceStructure(comb).Paste(comb.X, comb.Y, comb.Z);
                        comb.time = 0;
                        comb.timePlus = false;
                    }
                    if (comb.timePlus){
                        comb.time++;
                        if (!comb.havePlayer && comb.time < 50){
                            comb.time--;
                            if (Ticks % 4 == 0)
                                comb.time--;
                            if (comb.time % 10 == 0 && comb.time > 0){
                                StructureUtils.getIceStructure(comb).Paste(comb.X, comb.Y, comb.Z);
                                StructureUtils.structures.get(CombType.BREAK).get(CombColor.ICE)[comb.time / 10 - 1].Paste(comb.X, comb.Y, comb.Z);
                                if (comb.type.equals(CombType.HOLE))
                                    WorldUtils.fill(WorldUtils.subtract(comb.getCenterLocation(), 1, 0, 1), WorldUtils.add(comb.getCenterLocation(), 1, 0, 1), Material.AIR);
                            }
                            if (comb.time == 0){
                                comb.timePlus = false;
                                StructureUtils.getIceStructure(comb).Paste(comb.X, comb.Y, comb.Z);
                            }
                        }
                    }
                }
            }
        }
        Ticks++;
        if (Ticks >= 256)
            Ticks = 0;
    }
}
