package me.vovari2.sumoteam;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SumoTeamTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("sumoteam.*")){
                if (args.length == 1) {
                    List<String> commands = new ArrayList<>();
                    commands.add("help");
                    commands.add("trampoline");
                    commands.add("join");
                    commands.add("list");
                    commands.add("division");
                    commands.add("return");
                    commands.add("start");
                    commands.add("stop");
                    commands.add("gamemode");
                    commands.add("fieldmode");
                    commands.removeIf(str -> !str.toLowerCase().startsWith(args[0].toLowerCase()));
                    return commands;
                }
                else if (args.length == 2) {
                    switch(args[0]){
                        case "trampoline":{
                            List<String> directions = new ArrayList<>();
                            directions.add("forward");
                            directions.add("up");
                            directions.removeIf(str -> !str.toLowerCase().startsWith(args[1].toLowerCase()));
                            return directions;
                        }
                        case "join":
                        case "list":
                            return ListTeam(args);
                        case "division":{
                            List<String> tools = new ArrayList<>();
                            tools.add("max");
                            tools.add("save");
                            tools.add("load");
                            tools.removeIf(str -> !str.toLowerCase().startsWith(args[1].toLowerCase()));
                            return tools;
                        }
                        case "gamemode":{
                            List<String> gamemodes = new ArrayList<>();
                            gamemodes.add("CLASSIC");
                            gamemodes.add("KING_OF_THE_HILL");
                            gamemodes.removeIf(str -> !str.toLowerCase().startsWith(args[1].toLowerCase()));
                            return gamemodes;
                        }
                        case "fieldmode":{
                            List<String> fieldmodes = new ArrayList<>();
                            fieldmodes.add("CLASSIC");
                            fieldmodes.add("ICE_PLATFORM");
                            fieldmodes.removeIf(str -> !str.toLowerCase().startsWith(args[1].toLowerCase()));
                            return fieldmodes;
                        }
                    }
                }
                else if (args.length == 3){
                    if (args[0].equals("join")){
                        List<String> onlinePlayers = PlayerUtils.ListNamePlayers();
                        onlinePlayers.add("*");
                        onlinePlayers.removeIf(str -> !str.toLowerCase().startsWith(args[2].toLowerCase()));
                        return onlinePlayers;
                    }
                }
            }
        }
        return new ArrayList<>();
    }
    private List<String> ListTeam(String[] args){
        List<String> listteam = new ArrayList<>();
        listteam.add("Default");
        listteam.add("Red");
        listteam.add("Blue");
        listteam.add("Green");
        listteam.add("Yellow");
        if (args[0].equals("join"))
            listteam.add("-");
        listteam.removeIf(str -> !str.toLowerCase().startsWith(args[1].toLowerCase()));
        return listteam;
    }

}

