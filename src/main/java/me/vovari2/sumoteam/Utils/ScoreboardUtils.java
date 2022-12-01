package me.vovari2.sumoteam.Utils;

import me.vovari2.sumoteam.Modes.STGameMode;
import me.vovari2.sumoteam.SumoTeam;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreboardUtils {
    public static Scoreboard scoreboard;
    public static Scoreboard empty;
    public static Objective objective;

    public static void Initialization() {
        empty = Bukkit.getScoreboardManager().getMainScoreboard();
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        if (ScoreboardUtils.scoreboard.getObjective("SumoTeam") != null)
            ScoreboardUtils.scoreboard.getObjective("SumoTeam").unregister();
        objective = scoreboard.registerNewObjective("SumoTeam", "dummy", Component.text("SUMOTEAM", ComponentUtils.Gold, TextDecoration.BOLD));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public static String[] scores;
    public static String[] CreateScores(){
        String[] array = new String[9];
        array[8] = ChatColor.GRAY + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + ChatColor.DARK_GRAY + " Bees";
        array[7] = "  ";
        array[6] = ChatColor.RED + "К " + ChatColor.WHITE + "Красные: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.RED));
        array[5] = ChatColor.BLUE + "C " + ChatColor.WHITE + "Синие: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.BLUE));
        array[4] = ChatColor.GREEN + "З " + ChatColor.WHITE + "Зелёные: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.GREEN));
        array[3] = ChatColor.YELLOW + "Ж " + ChatColor.WHITE + "Жёлтые: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.YELLOW));
        array[2] = " ";
        array[1] = "";
        array[0] = ChatColor.GOLD + "www.playstrix.net";
        return array;
    }
    public static String[] UpdateScores(){
        String[] array = new String[9];
        array[6] = ChatColor.RED + "К " + ChatColor.WHITE + "Красные: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.RED));
        array[5] = ChatColor.BLUE + "C " + ChatColor.WHITE + "Синие: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.BLUE));
        array[4] = ChatColor.GREEN + "З " + ChatColor.WHITE + "Зелёные: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.GREEN));
        array[3] = ChatColor.YELLOW + "Ж " + ChatColor.WHITE + "Жёлтые: " + ChatColor.WHITE + OutputNumericTeam(SumoTeam.teams.get(STName.YELLOW));
        return array;
    }
    public static void LoadScores(){
        for (int i = 0; i < scores.length; i++)
            objective.getScore(scores[i]).setScore(i);
    }
    public static void ResetScores(){
        for (String entry : scoreboard.getEntries())
            objective.getScore(entry).resetScore();
    }
    public static void ReloadScores(){
        String[] newArray = UpdateScores();
        for (int i = 3; i < 7; i++)
            if (!scores[i].equals(newArray[i])){
                objective.getScore(scores[i]).resetScore();
                objective.getScore(newArray[i]).setScore(i);
                scores[i] = newArray[i];
            }
    }

    private static String OutputNumericTeam(STTeam team) {
        if (SumoTeam.gameMode.equals(STGameMode.CLASSIC)) {
            if (team.lives == 0)
                if (team.team.getSize() > 0)
                    return ChatColor.RED + String.valueOf(team.team.getSize());
                else return ChatColor.RED + "" + ChatColor.BOLD + "✗";
            return ChatColor.GREEN + String.valueOf(team.lives);
        }
        else return ChatColor.GOLD + Integer.toString(team.points);
    }
}
