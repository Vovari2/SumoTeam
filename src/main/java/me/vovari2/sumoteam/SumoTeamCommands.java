package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Modes.STGameMode;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.structure.Structure;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SumoTeamCommands implements CommandExecutor{
    private final SumoTeam plugin;
    SumoTeamCommands() {this.plugin = SumoTeam.plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("events.sumoteam.*")){
                if (args.length == 0)
                    SumoTeam.HelpMessage(player);
                else {
                    switch (args[0]){
                        case "trampoline": {
                            if (args.length > 3)
                                player.sendMessage(ChatColor.RED + "Слишком много параметров! (/sumoteam)");
                            else if (args.length == 2){
                                player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)");
                            }
                            else {
                                if (args.length == 1) {
                                    if (SumoTeamListener.switchTrampoline){
                                        SumoTeamListener.switchTrampoline = false;
                                        player.sendMessage(ChatColor.RED + "Батуты выключены!");
                                    }
                                    else {
                                        SumoTeamListener.switchTrampoline = true;
                                        player.sendMessage(ChatColor.GREEN + "Батуты включены!");
                                    }
                                }
                                else {
                                    if (args[1].equalsIgnoreCase("forward")) {
                                        if (SumoTeam.CheckStringOnDouble(args[2])) {
                                            plugin.getConfig().set("ScaleForward", Double.parseDouble(args[2]));
                                            plugin.saveConfig();
                                            SumoTeamListener.scaleForward = SumoTeam.plugin.getConfig().getDouble("ScaleForward");
                                            player.sendMessage("Коэффициент батута " + ChatColor.GREEN + "Вперёд" + ChatColor.WHITE + " был изменён на " + ChatColor.GREEN + args[2]);
                                        }
                                    }
                                    else if (args[1].equalsIgnoreCase("up")){
                                        if (SumoTeam.CheckStringOnDouble(args[2])) {
                                            plugin.getConfig().set("ScaleUp", Double.parseDouble(args[2]));
                                            plugin.saveConfig();
                                            SumoTeamListener.scaleUp = SumoTeam.plugin.getConfig().getDouble("ScaleUp");
                                            player.sendMessage("Коэффициент батута " + ChatColor.GREEN + "Вверх" + ChatColor.WHITE + " был изменён на " + ChatColor.GREEN + args[2]);
                                        }
                                    }
                                    else player.sendMessage("Команда введена неверно! (/sumoteam)");
                                }
                            }
                        } break;
                        case "join": {
                            if (SumoTeam.inLobby) {
                                if (args.length == 2){
                                    if (SumoTeam.teams.containsKey(STName.getName(args[1].toLowerCase())))
                                        AddingTeams(SumoTeam.teams.get(STName.getName(args[1].toLowerCase())), player, player);
                                    else player.sendMessage("[SumoTeam] " + ChatColor.RED + "Такой команды игроков не существует");
                                }
                                else if (args.length == 3){
                                    if (!args[2].equals("*")){
                                        HashMap<String, Player> Players = PlayerUtils.HashMapPlayers();
                                        if (Players.containsKey(args[2])){
                                            if (SumoTeam.teams.containsKey(STName.getName(args[1].toLowerCase())))
                                                AddingTeams(SumoTeam.teams.get(STName.getName(args[1].toLowerCase())), Players.get(args[2]), player);
                                            else player.sendMessage(ChatColor.RED + "Такой команды игроков не существует");
                                        }
                                        else player.sendMessage(ChatColor.RED + "Такого игрока не существует!");
                                    }
                                    else {
                                        String team = args[1].toLowerCase();
                                        if (!SumoTeam.teams.containsKey(STName.getName(team)))
                                            player.sendMessage(ChatColor.RED + "Такой команды игроков не существует!");
                                        else {
                                            for (Player selectPlayer : Bukkit.getServer().getOnlinePlayers())
                                                if (team.equals("-"))
                                                    LeaveTeam(selectPlayer);
                                                else JoinTeam(SumoTeam.teams.get(STName.getName(team)), selectPlayer);
                                            if (team.equals("-"))
                                                player.sendMessage("Все игроки были удалены из своих команд!");
                                            else player.sendMessage("Все игроки были добавлены в команду " + SumoTeam.teams.get(STName.getName(team)).word + ChatColor.WHITE + "!" );
                                        }
                                    }
                                }
                            } else player.sendMessage(ChatColor.RED + "Ивент ещё идёт!");
                        } break;
                        case "list": {
                            if (args.length == 1){
                                player.sendMessage(ListTeam(SumoTeam.teams.get(STName.RED)));
                                player.sendMessage(ListTeam(SumoTeam.teams.get(STName.BLUE)));
                                player.sendMessage(ListTeam(SumoTeam.teams.get(STName.GREEN)));
                                player.sendMessage(ListTeam(SumoTeam.teams.get(STName.YELLOW)));
                                player.sendMessage(ListTeam(SumoTeam.teams.get(STName.DEFAULT)));
                            } else if (args.length == 2){
                                if (SumoTeam.teams.containsKey(STName.getName(args[1].toLowerCase())))
                                    player.sendMessage(ListTeam(SumoTeam.teams.get(STName.getName(args[1].toLowerCase()))));
                                else player.sendMessage(ChatColor.RED + "Такой игровой команды не существует!");
                            }
                        } break;
                        case "division": {
                            if (SumoTeam.inLobby){
                                if (args.length == 1){
                                    int countPlayersTeam = SumoTeam.teams.get(STName.DEFAULT).team.getSize() / 4;
                                    int countDivision = countPlayersTeam * 4, countNotDivision = SumoTeam.teams.get(STName.DEFAULT).team.getSize() - countDivision;
                                    DivisionTeam(SumoTeam.teams.get(STName.RED), countPlayersTeam);
                                    DivisionTeam(SumoTeam.teams.get(STName.BLUE), countPlayersTeam);
                                    DivisionTeam(SumoTeam.teams.get(STName.GREEN), countPlayersTeam);
                                    DivisionTeam(SumoTeam.teams.get(STName.YELLOW), countPlayersTeam);
                                    String message = "";
                                    if (countDivision > 0){
                                        message = " Распределено: " + ChatColor.GREEN + countDivision + " ";
                                    }
                                    if (countNotDivision > 0){
                                        message = message + ChatColor.WHITE + "/ Не распределено: " + ChatColor.RED + countNotDivision;
                                    }
                                    if (countDivision > 0 || countNotDivision > 0)
                                        player.sendMessage(message);
                                    else player.sendMessage("В команде " + ChatColor.GRAY + "По умолчанию" + ChatColor.WHITE + " нет игроков!");
                                }
                                else if (args.length == 2){
                                    switch (args[1].toLowerCase()){
                                        case "save": {
                                            SumoTeamConfigTeams.reload();
                                            SumoTeamConfigTeams.get().set("TeamRedPlayers", SumoTeam.teams.get(STName.RED).getListPlayers());
                                            SumoTeamConfigTeams.get().set("TeamBluePlayers", SumoTeam.teams.get(STName.BLUE).getListPlayers());
                                            SumoTeamConfigTeams.get().set("TeamGreenPlayers", SumoTeam.teams.get(STName.GREEN).getListPlayers());
                                            SumoTeamConfigTeams.get().set("TeamYellowPlayers", SumoTeam.teams.get(STName.YELLOW).getListPlayers());
                                            SumoTeamConfigTeams.save();
                                            player.sendMessage("Состав игроков в командах был сохранён!");
                                        } break;
                                        case "load": {
                                            SumoTeamConfigTeams.reload();
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.RED), "Red");
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.BLUE), "Blue");
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.GREEN), "Green");
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.YELLOW), "Yellow");
                                            player.sendMessage("Команды были заполнены предыдущим составом игроков!");
                                        } break;
                                        case "max": {
                                            int countPlayersTeam = SumoTeam.teams.get(STName.DEFAULT).team.getSize() / 4;
                                            DivisionTeam(SumoTeam.teams.get(STName.RED), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.BLUE), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.GREEN), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.YELLOW), countPlayersTeam);

                                            String[] arrayDefault = PlayerUtils.ConvertToArrayString(SumoTeam.teams.get(STName.DEFAULT).team.getEntries());
                                            int countDefault = arrayDefault.length;
                                            Player selectPlayer;
                                            if (countDefault > 2){
                                                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[new Random().nextInt(countDefault-1)]);
                                                JoinTeam(SumoTeam.teams.get(STName.GREEN), selectPlayer);
                                            }
                                            arrayDefault = PlayerUtils.ConvertToArrayString(SumoTeam.teams.get(STName.DEFAULT).team.getEntries());
                                            if (countDefault > 1){
                                                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[new Random().nextInt(countDefault-1)]);
                                                JoinTeam(SumoTeam.teams.get(STName.BLUE), selectPlayer);
                                            }
                                            arrayDefault = PlayerUtils.ConvertToArrayString(SumoTeam.teams.get(STName.DEFAULT).team.getEntries());
                                            if (countDefault > 0){
                                                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[0]);
                                                JoinTeam(SumoTeam.teams.get(STName.RED), selectPlayer);
                                            }

                                            player.sendMessage("Распределено: " + ChatColor.GREEN + (countDefault + countPlayersTeam * 4));
                                        } break;
                                        default: player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!");
                                    }
                                }
                                else player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!");
                            }
                            else player.sendMessage(ChatColor.RED + "Ивент ещё идёт!");
                        } break;
                        case "return": {
                            if(SumoTeam.inLobby) {
                                ReturnTeam(SumoTeam.teams.get(STName.RED));
                                ReturnTeam(SumoTeam.teams.get(STName.BLUE));
                                ReturnTeam(SumoTeam.teams.get(STName.GREEN));
                                ReturnTeam(SumoTeam.teams.get(STName.YELLOW));
                                player.sendMessage("Игроки были возвращены в команду " + ChatColor.GRAY + "По умолчанию");
                            }
                            else player.sendMessage(ChatColor.RED + "Ивент ещё идёт!");
                        } break;
                        case "help": SumoTeam.HelpMessage(player); break;
                        case "start": {
                            if (SumoTeam.inLobby) {
                                SumoTeam.inLobby = false;

                                if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && SumoTeam.countIce == 89){
                                    Random R = new Random();
                                    for (HoneyComb comb : SumoTeam.honeyCombs){
                                        comb.isIce = true;
                                        Structure struct;
                                        if (comb.type.equals(CombType.TRAMPOLINE)){
                                            if (comb.isGlass)
                                                struct = SumoTeam.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[1];
                                            else struct = SumoTeam.structures.get(CombType.TRAMPOLINE).get(CombColor.ICE).getStructures()[0];
                                        }
                                        else struct = SumoTeam.structures.get(comb.type).get(CombColor.ICE).getRandomVariation(R);
                                        struct.place(new Location(SumoTeam.world, comb.X, comb.Y, comb.Z), false, comb.rotation, comb.mirror, 0, 1, new Random());
                                    }
                                }
                                SumoTeamTask.Tick = 400;

                                StartTeam(SumoTeam.teams.get(STName.RED));
                                StartTeam(SumoTeam.teams.get(STName.BLUE));
                                StartTeam(SumoTeam.teams.get(STName.GREEN));
                                StartTeam(SumoTeam.teams.get(STName.YELLOW));

                                player.sendMessage(ChatColor.GREEN + "Ивент начинается!");

                                ScoreboardUtils.LoadScores();
                            } else player.sendMessage(ChatColor.RED + "Ивент уже идёт!");
                        } break;
                        case "stop": {
                            if (!SumoTeam.inLobby){
                                SumoTeam.playerHits = new HashMap<>();

                                SumoTeam.inLobby = true;

                                SumoTeamTask.RemoveIce(new Random());

                                StopTeam(SumoTeam.teams.get(STName.RED));
                                StopTeam(SumoTeam.teams.get(STName.BLUE));
                                StopTeam(SumoTeam.teams.get(STName.GREEN));
                                StopTeam(SumoTeam.teams.get(STName.YELLOW));

                                WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1218), Material.AIR, Material.BARRIER);

                                player.sendMessage(ChatColor.RED + " Ивент остановлен!");
                            } else player.sendMessage(ChatColor.RED + "Ивент ещё не начался!");
                        } break;
                        case "gamemode": {
                            if (args.length > 2){
                                player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!");
                            }
                            else if (args.length == 1){
                                player.sendMessage("Режим игры: " + SumoTeam.gameMode.getChatColor() + SumoTeam.gameMode.name());
                            }
                            else {
                                if (SumoTeam.inLobby){
                                    switch(args[1].toLowerCase()){
                                        case "classic":{
                                            SumoTeam.gameMode = STGameMode.CLASSIC;
                                            player.sendMessage("Режим игры изменён на " + SumoTeam.gameMode.getChatColor() + SumoTeam.gameMode.name());
                                        } break;
                                        case "king_of_the_hill":{
                                            SumoTeam.gameMode = STGameMode.KING_OF_THE_HILL;
                                            player.sendMessage("Режим игры изменён на " + SumoTeam.gameMode.getChatColor() + SumoTeam.gameMode.name());
                                        } break;
                                        default: {
                                            player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!");
                                        }
                                    }
                                } else player.sendMessage(ChatColor.RED + "Ивент ещё идёт!");
                            }
                        } break;
                        case "fieldmode": {
                            if (args.length > 3) {
                                player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!");
                            }
                            else if (args.length == 1){
                                String message = "Режим поля: " + SumoTeam.fieldMode.getChatColor() + SumoTeam.fieldMode.name();
                                if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM))
                                    message += " " + SumoTeam.fillProcent + "%";
                                player.sendMessage(message);
                            }
                            else {
                                if (SumoTeam.inLobby){
                                    switch(args[1].toLowerCase()){
                                        case "classic":{
                                            SumoTeam.fieldMode = STFieldMode.CLASSIC;
                                            player.sendMessage("Режим поля изменён на " + SumoTeam.fieldMode.getChatColor() + SumoTeam.fieldMode.name());
                                        } break;
                                        case "ice_platform":{
                                            SumoTeam.fieldMode = STFieldMode.ICE_PLATFORM;
                                            player.sendMessage("Режим поля изменён на " + SumoTeam.fieldMode.getChatColor() + SumoTeam.fieldMode.name());
                                            if (args.length == 3){
                                                try{
                                                    SumoTeam.fillProcent = Integer.parseInt(args[2]);
                                                } catch (Exception error) { player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!"); break;}
                                                SumoTeam.countIce = (int) (89 * (SumoTeam.fillProcent / 100D));
                                                player.sendMessage("Процент заполнения поля льдом изменён на " + ChatColor.AQUA + SumoTeam.fillProcent + "%");
                                            }
                                        } break;
                                        default: {
                                            player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)!");
                                        }
                                    }
                                } else player.sendMessage(ChatColor.RED + "Ивент ещё идёт!");
                            }
                        } break;
                        default: player.sendMessage(ChatColor.RED + "Команда введена неверно! (/sumoteam)"); break;
                    }
                }
            }
            else player.sendMessage(ChatColor.RED + "У вас нет прав на использование этой команды!");
        }
        return true;
    }
    public static void JoinTeam(STTeam team, Player selectPlayer){
        selectPlayer.teleport(team.room);
        team.team.addEntity(selectPlayer);
    }

    public static void LeaveTeam(Player selectPlayer) {
        selectPlayer.teleport(SumoTeam.teams.get(STName.UNSET).room);
        SumoTeam.teams.get(STName.UNSET).team.addEntity(selectPlayer);
        SumoTeam.teams.get(STName.UNSET).team.removeEntity(selectPlayer);
    }

    private void AddingTeams(STTeam team, Player selectPlayer, Player player){
        JoinTeam(team, selectPlayer);
        if (team == SumoTeam.teams.get(STName.UNSET)){
            team.team.removeEntity(selectPlayer);
            player.sendMessage("Игрок " + ChatColor.GRAY + selectPlayer.getName() + ChatColor.WHITE +  " удалён из своей команды!");
        }
        else player.sendMessage("Игрок " + ChatColor.GRAY + selectPlayer.getName() + ChatColor.WHITE +  " добавлен в команду " + team.word + ChatColor.WHITE + "!");
    }

    public static String ListTeam(STTeam team) {
        if (team.team.getEntries().size() > 0) {
            String messageList = "Игроки команды " + team.word + ChatColor.WHITE + " (" + team.chatColor + team.team.getEntries().size() + ChatColor.WHITE + "): ";
            for (String playerName : team.team.getEntries())
                messageList = messageList.concat(ChatColor.GRAY + playerName + ", ");
            return messageList.substring(0, messageList.length()-2);
        } else return "В команде " + team.word + ChatColor.WHITE + " нет игроков";
    }

    private void DivisionTeam(STTeam team, int countPlayersTeam) {
        for (int i = 0; i < countPlayersTeam; i++) {
            STTeam teamDefault = SumoTeam.teams.get(STName.DEFAULT);
            String[] arrayDefault = PlayerUtils.ConvertToArrayString(teamDefault.team.getEntries());
            Player selectPlayer;
            if (teamDefault.team.getSize() == 1)
                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[0]);
            else
                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[new Random().nextInt(teamDefault.team.getSize() - 1)]);
            JoinTeam(team, selectPlayer);
        }
    }

    private void DivisionTeamLoad(STTeam team, String nameTeam) {
        HashMap<String, Player> listPlayers = PlayerUtils.HashMapPlayers();
        for (String playerName : SumoTeamConfigTeams.get().getStringList("Team" + nameTeam + "Players"))
            if (listPlayers.containsKey(playerName)){
                Player selectPlayer = listPlayers.get(playerName);
                JoinTeam(team, selectPlayer);
            }
    }

    private void ReturnTeam(STTeam team) {
        for (String playerName : team.team.getEntries()){
            Player selectPlayer = Bukkit.getServer().getPlayer(playerName);
            JoinTeam(SumoTeam.teams.get(STName.DEFAULT), selectPlayer);
        }
    }

    private void StartTeam(STTeam team) {
        for (String playerName : team.team.getEntries()){
            Player selectPlayer = Bukkit.getServer().getPlayer(playerName);
            selectPlayer.teleport(team.field);
            selectPlayer.sendMessage(ChatColor.GREEN + "Ивент начался!");
            selectPlayer.setGameMode(GameMode.ADVENTURE);
            SumoTeamInventory.giveItems(team.name,selectPlayer);
        }
    }

    private void StopTeam(STTeam team) {
        team.fallPlayers = new ArrayList<>();
        for (String playerName : team.team.getEntries()){
            Player selectPlayer = Bukkit.getServer().getPlayer(playerName);
            LeaveTeam(selectPlayer);
            selectPlayer.getInventory().clear();
        }
    }
}
