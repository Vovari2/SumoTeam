package me.vovari2.sumoteam.Utils;

import me.vovari2.sumoteam.Honeycomb.BreakType;
import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.SumoTeam;
import me.vovari2.sumoteam.SumoTeamCommands;
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
import java.util.Random;

public class STTeam {
    public STName name;
    public Team team;
    public float pointsF;
    public int points;
    public int countCenter;
    public Location room;
    public Location field;
    public String word;
    public ChatColor chatColor;
    public int colorR;
    public int colorG;
    public int colorB;
    public int lives;
    public ArrayList<Player> fallPlayers;
    public STTeam(STName teamName, String wordGS, ChatColor CColor, int R, int G, int B, Team ITeam) {
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
    public STTeam(STName teamName, String wordGS, ChatColor CColor, int R, int G, int B, Team ITeam, int maxLives) {
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
        lives = maxLives;
    }
    public Color getColor(){
        return Color.fromRGB(colorR, colorG, colorB);
    }
    public TextColor getTextColor(){
        return TextColor.color(colorR, colorG, colorB);
    }
    public ArrayList<String> getListPlayers(){
        return new ArrayList<>(team.getEntries());
    }
    public void WinTeam(){
        for (Player player : fallPlayers)
            if(PlayerUtils.players.containsKey(player.getName()))
                team.addEntity(player);

        for (String playerName : team.getEntries()){
            SumoTeam.plugin.getServer().getPlayer(playerName).getInventory().clear();
            SumoTeam.plugin.getServer().getPlayer(playerName).teleport(SumoTeam.teams.get(STName.DEFAULT).field);
        }

        for (STPlayer stPlayer : PlayerUtils.players.values()){
            Player player = stPlayer.player;
            player.sendMessage(ComponentUtils.lineStyle);
            player.sendMessage("\n      Команда " + word + ChatColor.WHITE + " победила!");
            player.sendMessage("      " + SumoTeamCommands.ListTeam(this) + "\n ");
            player.sendMessage(ComponentUtils.lineStyle);
        }

        for (Location pointFirework : WorldUtils.fireworkPosition){
            Firework firework = (Firework) WorldUtils.world.spawnEntity(pointFirework, EntityType.FIREWORK);
            FireworkMeta itemMeta = firework.getFireworkMeta();
            itemMeta.addEffect(name.getEffect());
            firework.setFireworkMeta(itemMeta);
        }

        SumoTeam.winTeam = name;
        SumoTeam.gameOver = true;

        for (STName teamName : STName.listGameTeam)
            if (!name.equals(teamName))
                SumoTeamCommands.StopTeam(SumoTeam.teams.get(teamName));

        if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM)){
            for (int i = 82; i < 89; i++)
                StructureUtils.honeyCombs[i].typeBreak = BreakType.CENTER;
            StructureUtils.ReplaceComb(new Random());
        }

        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6757, 157, 1218), Material.BARRIER, Material.AIR);
        WorldUtils.replace(new Location(WorldUtils.world, -6741, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1218), Material.BARRIER, Material.AIR);
        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1218), new Location (WorldUtils.world, -6741, 157, 1218), Material.BARRIER, Material.AIR);
        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1234), Material.BARRIER, Material.AIR);
    }

    public static void Initialization(int maxLives){
        SumoTeam.teams = new HashMap<>();
        SumoTeam.teams.put(STName.RED, new STTeam(STName.RED, "Красных", ChatColor.RED,255, 85, 85, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamRed"), maxLives));
        SumoTeam.teams.get(STName.RED).room = new Location(WorldUtils.world, -6756.5, 72, 1231.5,-90,0);
        SumoTeam.teams.get(STName.RED).field = new Location(WorldUtils.world, -6788.5, 141, 1271.5, -135, 0);

        SumoTeam.teams.put(STName.BLUE, new STTeam(STName.BLUE,"Синих", ChatColor.BLUE,85, 85, 255, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamBlue"), maxLives));
        SumoTeam.teams.get(STName.BLUE).room = new Location(WorldUtils.world, -6740.5, 72, 1221.5,90,0);
        SumoTeam.teams.get(STName.BLUE).field = new Location(WorldUtils.world, -6708.5, 141, 1181.5, 45, 0);

        SumoTeam.teams.put(STName.GREEN, new STTeam(STName.GREEN,"Зелёных", ChatColor.GREEN,85, 255, 85, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamGreen"), maxLives));
        SumoTeam.teams.get(STName.GREEN).room = new Location(WorldUtils.world, -6756.5, 72, 1221.5, -90, 0);
        SumoTeam.teams.get(STName.GREEN).field = new Location(WorldUtils.world, -6788.5, 141, 1181.5, -45, 0);

        SumoTeam.teams.put(STName.YELLOW, new STTeam(STName.YELLOW,"Жёлтых", ChatColor.YELLOW,255, 255, 85, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamYellow"), maxLives));
        SumoTeam.teams.get(STName.YELLOW).room = new Location(WorldUtils.world, -6740.5, 72, 1231.5,90,0);
        SumoTeam.teams.get(STName.YELLOW).field = new Location(WorldUtils.world, -6708.5, 141, 1271.5, 135, 0);

        SumoTeam.teams.put(STName.DEFAULT, new STTeam(STName.DEFAULT,"По умолчанию", ChatColor.GRAY,170, 170, 170, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamDefault")));
        SumoTeam.teams.get(STName.DEFAULT).room = new Location(WorldUtils.world, -6748.5, 69, 1216.5, 0,0);
        SumoTeam.teams.get(STName.DEFAULT).field = new Location(WorldUtils.world, -6748.5 ,149 ,1226.5);

        SumoTeam.teams.put(STName.UNSET, new STTeam(STName.UNSET,"Неопределенных", ChatColor.GRAY,170, 170, 170, ScoreboardUtils.scoreboard.registerNewTeam("SumoTeamUnset")));
        SumoTeam.teams.get(STName.UNSET).room = new Location(WorldUtils.world, -6748.5, 72, 1226.5,-180,0);
    }

    public static STTeam getPlayerTeam(String playerName){
        for (STTeam team : SumoTeam.teams.values())
            if (team.team.getEntries().contains(playerName))
                return team;
        return SumoTeam.teams.get(STName.UNSET);
    }
}