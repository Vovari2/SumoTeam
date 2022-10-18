package me.vovari2.sumoteam.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerUtils {
    public static HashMap<String, Player> players;
    public static HashMap<String, String> playerHits;

    public static void Initialization() {
        players = new HashMap<>();
        reload();
        playerHits = new HashMap<>();
    }

    public static void reload() {
        players.clear();
        for (Player player : Bukkit.getOnlinePlayers())
            add(player);
    }

    public static void add(Player player) {
        if (WorldUtils.inMap(player.getLocation()) && !players.containsKey(player.getName())){
            players.put(player.getName(), player);
            player.setScoreboard(ScoreboardUtils.scoreboard);
        }
    }

    public static void remove(Player player) {
        if (players.containsKey(player.getName()) && !WorldUtils.inMap(player.getLocation())){
            players.remove(player.getName());
            player.setScoreboard(ScoreboardUtils.empty);
        }
    }
}
