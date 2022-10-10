package me.vovari2.sumoteam;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SumoTeamConfigTeams {
    private static File file;
    private static FileConfiguration teamsFile;

    public static void create(){
        file = new File(SumoTeam.plugin.getDataFolder(), "teams.yml");
        if (!file.exists())
            try{ file.createNewFile(); } catch (IOException error){ SumoTeam.plugin.getLogger().info(ChatColor.RED + "Не удалось сохранить файл \"teams.yml\":\n" + error.getMessage()); }

        teamsFile = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get(){
        return teamsFile;
    }

    public static void save(){
        try{ teamsFile.save(file); } catch (IOException error) { SumoTeam.plugin.getLogger().info(ChatColor.RED + "Не удалось сохранить файл \"teams.yml\":\n" + error.getMessage()); }
    }
    public static void reload(){
        teamsFile = YamlConfiguration.loadConfiguration(file);
    }
}
