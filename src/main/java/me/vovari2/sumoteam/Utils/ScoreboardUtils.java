package me.vovari2.sumoteam.Utils;

import me.vovari2.sumoteam.SumoTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardUtils {
    public static Scoreboard scoreboard;
    public static Scoreboard empty;
    public static Objective teamScores;

    public static void Initialization() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        if (ScoreboardUtils.scoreboard.getObjective("SumoTeam") != null)
            ScoreboardUtils.scoreboard.getObjective("SumoTeam").unregister();
        teamScores = scoreboard.registerNewObjective("SumoTeam", "dummy", Component.text("SUMOTEAM", ComponentUtils.Gold, TextDecoration.BOLD));
        teamScores.setDisplaySlot(DisplaySlot.SIDEBAR);
        empty = Bukkit.getScoreboardManager().getNewScoreboard();
    }


    public static void LoadScores(){
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
    public static void ResetScores(){
        for (String entry : scoreboard.getEntries())
            teamScores.getScore(entry).resetScore();
    }

    private static String OutputCountPlayerTeam(int size) {
        if (size == 0)
            return ChatColor.BOLD + "✗";
        return String.valueOf(size);
    }
}
