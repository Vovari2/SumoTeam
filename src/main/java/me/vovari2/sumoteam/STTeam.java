package me.vovari2.sumoteam;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class STTeam {
    STName name;
    Team team;
    Location room;
    Location field;
    String word;
    ChatColor chatColor;
    int colorR;
    int colorG;
    int colorB;
    ArrayList<Player> fallPlayers;
    TextColor getTextColor() { return TextColor.color(colorR, colorG, colorB); }
    STTeam(STName teamName, String wordGS, ChatColor CColor, int R, int G, int B, Team ITeam) {
        name = teamName;
        word = CColor + wordGS;
        chatColor = CColor;
        colorR = R;
        colorG = G;
        colorB = B;
        team = ITeam;
        team.color(NamedTextColor.nearestTo(getTextColor()));
        team.setAllowFriendlyFire(false);
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.FOR_OWN_TEAM);
        fallPlayers = new ArrayList<>();
    }
    ArrayList<String> getListPlayers(){
        return new ArrayList<>(team.getEntries());
    }
    Color getColor(){
        return Color.fromRGB(colorR, colorG, colorB);
    }
    void WinTeam(){
        List<String> namePlayers = PlayerUtils.ListNamePlayers();
        for (Player player : fallPlayers)
            if(namePlayers.contains(player.getName()))
                team.addEntity(player);

        for (String playerName : team.getEntries())
            SumoTeam.plugin.getServer().getPlayer(playerName).getInventory().clear();

        for (Player player : Bukkit.getOnlinePlayers()){
            if (WorldUtils.inMap(player.getLocation())){
                player.sendMessage(ChatColor.GRAY + "\n▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂");
                player.sendMessage("\n\n        Команда " + word + ChatColor.WHITE + " победила!");
                String message = SumoTeamCommands.ListTeam(this);
                player.sendMessage("    " + message.substring(11, message.length()-1));
                player.sendMessage(ChatColor.GRAY + "\n▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂▂\n\n");
            }
        }

        for (Location pointFirework : WorldUtils.fireworkPosition){
            Firework firework = (Firework) WorldUtils.world.spawnEntity(pointFirework, EntityType.FIREWORK);
            FireworkMeta itemMeta = firework.getFireworkMeta();
            itemMeta.addEffect(name.getEffect());
            firework.setFireworkMeta(itemMeta);
        }

        SumoTeam.winTeam = name;

        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6757, 157, 1218), Material.BARRIER, Material.AIR);
        WorldUtils.replace(new Location(WorldUtils.world, -6741, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1218), Material.BARRIER, Material.AIR);
        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1218), new Location (WorldUtils.world, -6741, 157, 1218), Material.BARRIER, Material.AIR);
        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1234), Material.BARRIER, Material.AIR);
    }

    static void Initialization(){
        SumoTeam.teams = new HashMap<>();
        SumoTeam.teams.put(STName.RED, new STTeam(STName.RED, "Красных", ChatColor.RED,255, 85, 85, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamRed")));
        SumoTeam.teams.get(STName.RED).room = new Location(WorldUtils.world, -6756.5, 72, 1231.5,-90,0);
        SumoTeam.teams.get(STName.RED).field = new Location(WorldUtils.world, -6788.5, 141, 1271.5, -135, 0);

        SumoTeam.teams.put(STName.BLUE, new STTeam(STName.BLUE,"Синих", ChatColor.BLUE,85, 85, 255, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamBlue")));
        SumoTeam.teams.get(STName.BLUE).room = new Location(WorldUtils.world, -6740.5, 72, 1221.5,90,0);
        SumoTeam.teams.get(STName.BLUE).field = new Location(WorldUtils.world, -6708.5, 141, 1181.5, 45, 0);

        SumoTeam.teams.put(STName.GREEN, new STTeam(STName.GREEN,"Зелёных", ChatColor.GREEN,85, 255, 85, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamGreen")));
        SumoTeam.teams.get(STName.GREEN).room = new Location(WorldUtils.world, -6756.5, 72, 1221.5, -90, 0);
        SumoTeam.teams.get(STName.GREEN).field = new Location(WorldUtils.world, -6788.5, 141, 1181.5, -45, 0);

        SumoTeam.teams.put(STName.YELLOW, new STTeam(STName.YELLOW,"Жёлтых", ChatColor.YELLOW,255, 255, 85, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamYellow")));
        SumoTeam.teams.get(STName.YELLOW).room = new Location(WorldUtils.world, -6740.5, 72, 1231.5,90,0);
        SumoTeam.teams.get(STName.YELLOW).field = new Location(WorldUtils.world, -6708.5, 141, 1271.5, 135, 0);

        SumoTeam.teams.put(STName.DEFAULT, new STTeam(STName.DEFAULT,"По умолчанию", ChatColor.GRAY,170, 170, 170, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamDefault")));
        SumoTeam.teams.get(STName.DEFAULT).room = new Location(WorldUtils.world, -6748.5, 69, 1216.5, 0,0);
        SumoTeam.teams.get(STName.DEFAULT).field = new Location(WorldUtils.world, -6748.5 ,149 ,1226.5);

        SumoTeam.teams.put(STName.UNSET, new STTeam(STName.UNSET,"Неопределенных", ChatColor.GRAY,170, 170, 170, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamUnset")));
        SumoTeam.teams.get(STName.UNSET).room = new Location(WorldUtils.world, -6748.5, 72, 1226.5,-180,0);
    }
}