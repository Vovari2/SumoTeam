package me.vovari2.sumoteam;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class SumoTeamListener implements Listener {

    public static boolean switchTrampoline = true;
    public static double scaleForward;
    public static double scaleUp;
    private boolean isScoreboard;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if (switchTrampoline) {
            Location location = player.getLocation();
            if (location.clone().subtract(0.0D, 1.0D, 0.0D).getBlock().getType().equals(Material.EMERALD_BLOCK) && location.getBlock().getType().equals(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE)) {
                Vector direction = location.getDirection();
                player.setVelocity(new Vector(direction.getX() * 0.4D * scaleForward,0.85D * scaleUp,direction.getZ() * 0.4D * scaleForward));
            }
        }

        if (!SumoTeam.inLobby){
            for (Player selectPlayer : Bukkit.getServer().getOnlinePlayers()){
                if (WorldUtils.inMap(selectPlayer.getLocation()) && isScoreboard){
                    selectPlayer.setScoreboard(ScoreboardUtils.scoreboard);
                    isScoreboard = false;
                }
                else if (!WorldUtils.inMap(selectPlayer.getLocation()) &&!isScoreboard){
                    selectPlayer.setScoreboard(ScoreboardUtils.empty);
                    isScoreboard = true;
                }
            }
        }

        if (WorldUtils.inMap(player.getLocation())){
            String name = player.getName();

            if (player.getLocation().distance(WorldUtils.pointDefault) <= 3 && !SumoTeam.teams.get(STName.DEFAULT).team.getEntries().contains(name)){
                SumoTeam.teams.get(STName.DEFAULT).team.addEntity(player);
            }
            else if (player.getLocation().distance(WorldUtils.pointDefault) > 3 && SumoTeam.teams.get(STName.DEFAULT).team.getEntries().contains(name)){
                SumoTeam.teams.get(STName.DEFAULT).team.removeEntity(player);
            }

            // Область под картой, для удаления игрока с поля, если упал
            if (SumoTeam.teams.get(STName.RED).team.getEntries().contains(name) || SumoTeam.teams.get(STName.BLUE).team.getEntries().contains(name) || SumoTeam.teams.get(STName.GREEN).team.getEntries().contains(name) || SumoTeam.teams.get(STName.YELLOW).team.getEntries().contains(name)){
                double X = player.getLocation().getX(), Y = player.getLocation().getY(), Z = player.getLocation().getZ();
                if (Y <= 126 && Y >= 120 && X >= -6816.7 && Z <= 1305.7 && X <= -6680.3 && Z >= 1147.3){
                    // Показ сообщение другим игрокам, о том что игрок упал
                    for (Player selectPlayer : Bukkit.getOnlinePlayers())
                        if (WorldUtils.inMap(selectPlayer.getLocation())){
                            if (!SumoTeam.playerHits.containsKey(player.getName()))
                                selectPlayer.sendMessage(TextUtils.getGameText(Component.text(name, ScoreboardUtils.scoreboard.getEntityTeam(player).color()).append(Component.text(" упал", ComponentUtils.White))));
                            else if (PlayerUtils.ListNamePlayers().contains(SumoTeam.playerHits.get(name)))
                                selectPlayer.sendMessage(TextUtils.getGameText(Component.text(name, ScoreboardUtils.scoreboard.getEntityTeam(player).color()).append(Component.text(" был скинут ", ComponentUtils.White)).append(Component.text(SumoTeam.playerHits.get(name), ScoreboardUtils.scoreboard.getEntityTeam(PlayerUtils.HashMapPlayers().get(SumoTeam.playerHits.get(name))).color()))));
                            if (selectPlayer.getName().equals(SumoTeam.playerHits.get(name)))
                                selectPlayer.playSound(selectPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 50, 1);
                        }
                    SumoTeam.teams.get(STName.getName(ScoreboardUtils.scoreboard.getEntityTeam(player).getName().substring(8).toLowerCase())).fallPlayers.add(Bukkit.getPlayer(player.getName()));

                    SumoTeamCommands.LeaveTeam(player);
                    player.getInventory().clear();

                    ScoreboardUtils.ResetScores();
                    ScoreboardUtils.LoadScores();

                    player.showTitle(Title.title(Component.text("Повезёт в другой раз"),Component.text("")));


                    int Red = SumoTeam.teams.get(STName.RED).team.getSize(), Blu = SumoTeam.teams.get(STName.BLUE).team.getSize(), Gre = SumoTeam.teams.get(STName.GREEN).team.getSize(), Yel = SumoTeam.teams.get(STName.YELLOW).team.getSize();
                    if (Blu==0 && Gre==0 && Yel==0 && Red > 0)
                        SumoTeam.teams.get(STName.RED).WinTeam();
                    else if (Red==0 && Gre==0 && Yel==0 && Blu > 0)
                        SumoTeam.teams.get(STName.BLUE).WinTeam();
                    else if (Red==0 && Blu==0 && Yel==0 && Gre > 0)
                        SumoTeam.teams.get(STName.GREEN).WinTeam();
                    else if (Red==0 && Blu==0 && Gre==0 && Yel > 0)
                        SumoTeam.teams.get(STName.YELLOW).WinTeam();
                }
            }
        }
    }

}
