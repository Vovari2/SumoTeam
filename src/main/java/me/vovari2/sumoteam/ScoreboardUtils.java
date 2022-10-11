package me.vovari2.sumoteam;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {
    static Scoreboard scoreboard;
    static Scoreboard empty;
    static Objective teamScores;

    static void Initialization() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        teamScores = scoreboard.registerNewObjective("SumoTeam", "dummy", Component.text("SUMOTEAM", ComponentUtils.Gold, TextDecoration.BOLD));
        teamScores.setDisplaySlot(DisplaySlot.SIDEBAR);
    }


    static void LoadScores(){
        teamScores.getScore("  ").setScore(8);
        teamScores.getScore(ChatColor.BOLD + "   Игроки:").setScore(7);
        teamScores.getScore(ChatColor.RED + "  Красные: " + ChatColor.WHITE + OutputCountPlayerTeam(SumoTeam.teams.get(STName.RED).team.getSize())).setScore(6);
        teamScores.getScore(ChatColor.BLUE + "  Синие: " + ChatColor.WHITE + OutputCountPlayerTeam(SumoTeam.teams.get(STName.BLUE).team.getSize())).setScore(5);
        teamScores.getScore(ChatColor.GREEN + "  Зелёные: " + ChatColor.WHITE + OutputCountPlayerTeam(SumoTeam.teams.get(STName.GREEN).team.getSize())).setScore(4);
        teamScores.getScore(ChatColor.YELLOW + "  Жёлтые: " + ChatColor.WHITE + OutputCountPlayerTeam(SumoTeam.teams.get(STName.YELLOW).team.getSize())).setScore(3);
        teamScores.getScore(" ").setScore(2);
        teamScores.getScore("").setScore(1);
        teamScores.getScore(ChatColor.GOLD + " playstrix.net").setScore(0);
    }
    static void ResetScores(){
        for (String entry : scoreboard.getEntries())
            teamScores.getScore(entry).resetScore();
    }

    private static String OutputCountPlayerTeam(int size) {
        if (size == 0)
            return ChatColor.BOLD + "✗";
        return String.valueOf(size);
    }
}
