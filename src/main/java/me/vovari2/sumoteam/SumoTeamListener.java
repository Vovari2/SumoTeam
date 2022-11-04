package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Modes.STGameMode;
import me.vovari2.sumoteam.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class SumoTeamListener implements Listener {

    public static boolean switchTrampoline = true;
    public static double scaleForward;
    public static double scaleUp;

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent event){
        if (switchTrampoline && event.getAction().equals(Action.PHYSICAL) && event.getClickedBlock().getType().equals(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE))
                event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerUtils.add(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        PlayerUtils.remove(event.getPlayer());
    }

    @EventHandler
    public void onDamagePlayer(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player damager = ((Player) event.getDamager()).getPlayer(), player = ((Player) event.getEntity()).getPlayer();
            PlayerUtils.playerHits.put(player.getName(), damager.getName());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if (switchTrampoline) {
            Location location = player.getLocation();
            if (location.clone().subtract(0.0D, 0.2D, 0.0D).getBlock().getType().equals(Material.EMERALD_BLOCK) && location.getBlock().getType().equals(Material.POLISHED_BLACKSTONE_PRESSURE_PLATE)) {
                Vector direction = location.getDirection();
                WorldUtils.world.playSound(location,Sound.BLOCK_AMETHYST_BLOCK_PLACE, 0.25F, 1);
                player.setVelocity(new Vector(direction.getX() * 0.4D * scaleForward,0.85D * scaleUp,direction.getZ() * 0.4D * scaleForward));
            }
        }

        PlayerUtils.add(player);
        if (!WorldUtils.inMap(player.getLocation()))
            PlayerUtils.remove(player);

        if (PlayerUtils.players.containsKey(player.getName())){
            String name = player.getName();

            if (player.getLocation().distance(WorldUtils.pointDefault) <= 3 && !SumoTeam.teams.get(STName.DEFAULT).team.getEntries().contains(name)){
                SumoTeam.teams.get(STName.DEFAULT).team.addEntity(player);
            }
            else if (player.getLocation().distance(WorldUtils.pointDefault) > 3 && SumoTeam.teams.get(STName.DEFAULT).team.getEntries().contains(name)){
                SumoTeam.teams.get(STName.DEFAULT).team.removeEntity(player);
            }

            // Область под картой, для удаления игрока с поля, если упал
            if (PlayerUtils.players.get(name).inField){
                if (SumoTeam.gameMode.equals(STGameMode.KING_OF_THE_HILL)){
                    if (!STTeam.getPlayerTeam(player.getName()).name.equals(STName.RED) && WorldUtils.isOnCenter(WorldUtils.pointRedZone, player.getLocation(), 6))
                        player.setVelocity(VectorUtils.getVector(player.getLocation(), WorldUtils.pointRedZone));
                    else if (!STTeam.getPlayerTeam(player.getName()).name.equals(STName.BLUE) && WorldUtils.isOnCenter(WorldUtils.pointBlueZone, player.getLocation(), 6))
                        player.setVelocity(VectorUtils.getVector(player.getLocation(), WorldUtils.pointBlueZone));
                    else if (!STTeam.getPlayerTeam(player.getName()).name.equals(STName.GREEN) && WorldUtils.isOnCenter(WorldUtils.pointGreenZone, player.getLocation(), 6))
                        player.setVelocity(VectorUtils.getVector(player.getLocation(), WorldUtils.pointGreenZone));
                    else if (!STTeam.getPlayerTeam(player.getName()).name.equals(STName.YELLOW) && WorldUtils.isOnCenter(WorldUtils.pointYellowZone, player.getLocation(), 6))
                        player.setVelocity(VectorUtils.getVector(player.getLocation(), WorldUtils.pointYellowZone));
                }

                double X = player.getLocation().getX(), Y = player.getLocation().getY(), Z = player.getLocation().getZ();
                if (Y <= 126 && Y >= 120 && X >= -6816.7 && Z <= 1305.7 && X <= -6680.3 && Z >= 1147.3){
                    // Показ сообщение другим игрокам, о том что игрок упал
                    for (STPlayer selectSTPlayer : PlayerUtils.players.values()) {
                        Player selectPlayer = selectSTPlayer.player;
                        if (!PlayerUtils.playerHits.containsKey(player.getName()))
                            selectPlayer.sendMessage(TextUtils.getGameText(Component.text(name, STTeam.getPlayerTeam(player.getName()).getTextColor()).append(Component.text(" упал", ComponentUtils.White))));
                        else if (PlayerUtils.players.containsKey(PlayerUtils.playerHits.get(name)))
                            selectPlayer.sendMessage(TextUtils.getGameText(Component.text(name, STTeam.getPlayerTeam(player.getName()).getTextColor()).append(Component.text(" был скинут ", ComponentUtils.White)).append(Component.text(PlayerUtils.playerHits.get(name), STTeam.getPlayerTeam(PlayerUtils.players.get(PlayerUtils.playerHits.get(name)).player.getName()).getTextColor()))));
                        if (selectPlayer.getName().equals(PlayerUtils.playerHits.get(name)))
                            selectPlayer.playSound(selectPlayer.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 50, 1);
                    }
                    if (SumoTeam.gameMode.equals(STGameMode.CLASSIC)) {
                        STTeam.getPlayerTeam(player.getName()).fallPlayers.add(Bukkit.getPlayer(player.getName()));

                        SumoTeamCommands.LeaveTeam(player);
                        player.getInventory().clear();

                        ScoreboardUtils.ReloadScores();

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
                    else {
                        player.teleport(STTeam.getPlayerTeam(player.getName()).field);
                        PlayerUtils.playerHits.remove(player.getName());
                    }
                }
            }
        }
    }

}
