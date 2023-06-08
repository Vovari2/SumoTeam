package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Honeycomb.BreakType;
import me.vovari2.sumoteam.Honeycomb.HoneyComb;
import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Modes.STGameMode;
import me.vovari2.sumoteam.Utils.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SumoTeamCommands implements CommandExecutor{
    private final SumoTeam plugin;
    SumoTeamCommands() {this.plugin = SumoTeam.plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args){
        if (sender instanceof Player player){
            if (player.hasPermission("sumoteam.*")){
                if (args.length == 0)
                    SumoTeam.HelpMessage(player);
                else {
                    switch (args[0]) {
                        case "trampoline" -> {
                            if (args.length > 3)
                                TextUtils.errorTooManyArguments(player);
                            else if (args.length == 2)
                                TextUtils.errorCommandIncorrectly(player);
                            else {
                                if (args.length == 1) {
                                    if (SumoTeamListener.switchTrampoline) {
                                        SumoTeamListener.switchTrampoline = false;
                                        player.sendMessage(TextUtils.getEditText(ChatColor.RED + "Батуты выключены!"));
                                    } else {
                                        SumoTeamListener.switchTrampoline = true;
                                        player.sendMessage(TextUtils.getEditText(ChatColor.GREEN + "Батуты включены!"));
                                    }
                                } else {
                                    if (args[1].equalsIgnoreCase("forward")) {
                                        if (SumoTeam.CheckStringOnDouble(args[2])) {
                                            plugin.getConfig().set("ScaleForward", Double.parseDouble(args[2]));
                                            plugin.saveConfig();
                                            SumoTeamListener.scaleForward = SumoTeam.plugin.getConfig().getDouble("ScaleForward");
                                            player.sendMessage(TextUtils.getEditText("Коэффициент батута " + ChatColor.GREEN + "Вперёд" + ChatColor.WHITE + " был изменён на " + ChatColor.GREEN + args[2]));
                                        } else TextUtils.errorCommandIncorrectly(player);
                                    } else if (args[1].equalsIgnoreCase("up")) {
                                        if (SumoTeam.CheckStringOnDouble(args[2])) {
                                            plugin.getConfig().set("ScaleUp", Double.parseDouble(args[2]));
                                            plugin.saveConfig();
                                            SumoTeamListener.scaleUp = SumoTeam.plugin.getConfig().getDouble("ScaleUp");
                                            player.sendMessage(TextUtils.getEditText("Коэффициент батута " + ChatColor.GREEN + "Вверх" + ChatColor.WHITE + " был изменён на " + ChatColor.GREEN + args[2]));
                                        } else TextUtils.errorCommandIncorrectly(player);
                                    } else TextUtils.errorCommandIncorrectly(player);
                                }
                            }
                        }
                        case "join" -> {
                            if (SumoTeam.inLobby) {
                                if (args.length > 3) {
                                    TextUtils.errorTooManyArguments(player);
                                } else if (args.length == 2) {
                                    if (STName.isSTName(args[1]))
                                        AddingTeams(SumoTeam.teams.get(STName.getName(args[1])), player, player);
                                    else TextUtils.errorTeamIncorrectly(player);
                                } else if (args.length == 3) {
                                    if (!args[2].equals("*")) {
                                        HashMap<String, Player> Players = SumoTeam.HashMapPlayers();
                                        if (Players.containsKey(args[2])) {
                                            if (STName.isSTName(args[1]))
                                                AddingTeams(SumoTeam.teams.get(STName.getName(args[1])), Players.get(args[2]), player);
                                            else TextUtils.errorTeamIncorrectly(player);
                                        } else TextUtils.errorPlayerIncorrectly(player);
                                    } else {
                                        String team = args[1].toLowerCase();
                                        if (!STName.isSTName(team))
                                            TextUtils.errorTeamIncorrectly(player);
                                        else {
                                            for (STPlayer selectSTPlayer : PlayerUtils.players.values()) {
                                                Player selectPlayer = selectSTPlayer.player;
                                                if (team.equals("-"))
                                                    LeaveTeam(selectPlayer);
                                                else JoinTeam(SumoTeam.teams.get(STName.getName(team)), selectPlayer);
                                            }
                                            if (team.equals("-"))
                                                player.sendMessage(TextUtils.getReadyText("Все игроки были удалены из своих команд!"));
                                            else
                                                player.sendMessage(TextUtils.getReadyText("Все игроки были добавлены в команду " + SumoTeam.teams.get(STName.getName(team)).word + ChatColor.WHITE + "!"));
                                        }
                                    }
                                } else TextUtils.errorCommandIncorrectly(player);
                            } else TextUtils.warningEventGoingOn(player);
                        }
                        case "list" -> {
                            if (args.length == 1) {
                                player.sendMessage(TextUtils.getReadyText(ListTeam(SumoTeam.teams.get(STName.RED))));
                                player.sendMessage(TextUtils.getReadyText(ListTeam(SumoTeam.teams.get(STName.BLUE))));
                                player.sendMessage(TextUtils.getReadyText(ListTeam(SumoTeam.teams.get(STName.GREEN))));
                                player.sendMessage(TextUtils.getReadyText(ListTeam(SumoTeam.teams.get(STName.YELLOW))));
                                player.sendMessage(TextUtils.getReadyText(ListTeam(SumoTeam.teams.get(STName.DEFAULT))));
                            } else if (args.length == 2) {
                                if (!args[1].equals("*")) {
                                    if (STName.isSTName(args[1]))
                                        player.sendMessage(TextUtils.getReadyText(ListTeam(SumoTeam.teams.get(STName.getName(args[1])))));
                                    else TextUtils.errorTeamIncorrectly(player);
                                } else {
                                    if (PlayerUtils.players.size() > 0) {
                                        StringBuilder messageList = new StringBuilder("Игроки на ивенте:" + " (" + ChatColor.GRAY + PlayerUtils.players.size() + ChatColor.WHITE + "): ");
                                        for (STPlayer selectPlayer : PlayerUtils.players.values()) {
                                            if (selectPlayer.inField)
                                                messageList.append(ChatColor.GREEN).append("\n       ● ");
                                            else messageList.append(ChatColor.RED).append("\n       ● ");
                                            messageList.append(ChatColor.GRAY).append(selectPlayer.player.getName());
                                        }
                                        player.sendMessage(TextUtils.getReadyText(messageList.toString()));
                                    } else player.sendMessage(TextUtils.getReadyText("На ивенте нет игроков!"));
                                }

                            } else TextUtils.errorTooManyArguments(player);
                        }
                        case "division" -> {
                            if (SumoTeam.inLobby) {
                                if (args.length == 1) {
                                    int countPlayersTeam = SumoTeam.teams.get(STName.DEFAULT).team.getSize() / 4;
                                    int countDivision = countPlayersTeam * 4, countNotDivision = SumoTeam.teams.get(STName.DEFAULT).team.getSize() - countDivision;
                                    DivisionTeam(SumoTeam.teams.get(STName.RED), countPlayersTeam);
                                    DivisionTeam(SumoTeam.teams.get(STName.BLUE), countPlayersTeam);
                                    DivisionTeam(SumoTeam.teams.get(STName.GREEN), countPlayersTeam);
                                    DivisionTeam(SumoTeam.teams.get(STName.YELLOW), countPlayersTeam);
                                    String message = "";
                                    if (countDivision > 0) {
                                        message = " Распределено: " + ChatColor.GREEN + countDivision + " ";
                                        if (countNotDivision > 0)
                                            message += "/ ";
                                    }
                                    if (countNotDivision > 0)
                                        message += ChatColor.WHITE + "Не распределено: " + ChatColor.RED + countNotDivision;
                                    if (countDivision > 0)
                                        player.sendMessage(TextUtils.getReadyText(message));
                                    else if (countNotDivision > 0) {
                                        player.sendMessage(TextUtils.getWarningText(message));
                                    } else
                                        player.sendMessage(TextUtils.getWarningText("В команде " + ChatColor.GRAY + "По умолчанию" + ChatColor.WHITE + " нет игроков!"));
                                } else if (args.length == 2) {
                                    switch (args[1].toLowerCase()) {
                                        case "save" -> {
                                            SumoTeamConfigTeams.reload();
                                            SumoTeamConfigTeams.get().set("TeamRedPlayers", SumoTeam.teams.get(STName.RED).getListPlayers());
                                            SumoTeamConfigTeams.get().set("TeamBluePlayers", SumoTeam.teams.get(STName.BLUE).getListPlayers());
                                            SumoTeamConfigTeams.get().set("TeamGreenPlayers", SumoTeam.teams.get(STName.GREEN).getListPlayers());
                                            SumoTeamConfigTeams.get().set("TeamYellowPlayers", SumoTeam.teams.get(STName.YELLOW).getListPlayers());
                                            SumoTeamConfigTeams.save();
                                            player.sendMessage(TextUtils.getReadyText("Состав игроков в командах был сохранён!"));
                                        }
                                        case "load" -> {
                                            SumoTeamConfigTeams.reload();
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.RED), "Red");
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.BLUE), "Blue");
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.GREEN), "Green");
                                            DivisionTeamLoad(SumoTeam.teams.get(STName.YELLOW), "Yellow");
                                            player.sendMessage(TextUtils.getReadyText("Команды были заполнены предыдущим составом игроков!"));
                                        }
                                        case "max" -> {
                                            int countPlayersTeam = SumoTeam.teams.get(STName.DEFAULT).team.getSize() / 4;
                                            DivisionTeam(SumoTeam.teams.get(STName.RED), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.BLUE), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.GREEN), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.YELLOW), countPlayersTeam);

                                            String[] arrayDefault = SumoTeam.ConvertToArrayString(SumoTeam.teams.get(STName.DEFAULT).team.getEntries());
                                            int countDefault = arrayDefault.length;
                                            Player selectPlayer;
                                            if (countDefault > 2) {
                                                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[Math.abs(new Random().nextInt() % (countDefault - 1))]);
                                                JoinTeam(SumoTeam.teams.get(STName.GREEN), selectPlayer);
                                            }
                                            arrayDefault = SumoTeam.ConvertToArrayString(SumoTeam.teams.get(STName.DEFAULT).team.getEntries());
                                            if (countDefault > 1) {
                                                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[Math.abs(new Random().nextInt() % (countDefault - 1))]);
                                                JoinTeam(SumoTeam.teams.get(STName.BLUE), selectPlayer);
                                            }
                                            arrayDefault = SumoTeam.ConvertToArrayString(SumoTeam.teams.get(STName.DEFAULT).team.getEntries());
                                            if (countDefault > 0) {
                                                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[0]);
                                                JoinTeam(SumoTeam.teams.get(STName.RED), selectPlayer);
                                            }

                                            player.sendMessage(TextUtils.getReadyText("Распределено: " + ChatColor.GREEN + (countDefault + countPlayersTeam * 4)));
                                        }
                                        case "duo" -> {
                                            int countPlayersTeam = SumoTeam.teams.get(STName.DEFAULT).team.getSize() / 2;
                                            int countDivision = countPlayersTeam * 2, countNotDivision = SumoTeam.teams.get(STName.DEFAULT).team.getSize() - countDivision;
                                            DivisionTeam(SumoTeam.teams.get(STName.RED), countPlayersTeam);
                                            DivisionTeam(SumoTeam.teams.get(STName.BLUE), countPlayersTeam);
                                            String message = "";
                                            if (countDivision > 0) {
                                                message = " Распределено: " + ChatColor.GREEN + countDivision + " ";
                                                if (countNotDivision > 0)
                                                    message += "/ ";
                                            }
                                            if (countNotDivision > 0)
                                                message += ChatColor.WHITE + "Не распределено: " + ChatColor.RED + countNotDivision;
                                            if (countDivision > 0)
                                                player.sendMessage(TextUtils.getReadyText(message));
                                            else if (countNotDivision > 0) {
                                                player.sendMessage(TextUtils.getWarningText(message));
                                            } else
                                                player.sendMessage(TextUtils.getWarningText("В команде " + ChatColor.GRAY + "По умолчанию" + ChatColor.WHITE + " нет игроков!"));
                                        }
                                        default -> TextUtils.errorCommandIncorrectly(player);
                                    }
                                } else TextUtils.errorCommandIncorrectly(player);
                            } else TextUtils.warningEventGoingOn(player);
                        }
                        case "return" -> {
                            if (SumoTeam.inLobby) {
                                ReturnTeam(SumoTeam.teams.get(STName.RED));
                                ReturnTeam(SumoTeam.teams.get(STName.BLUE));
                                ReturnTeam(SumoTeam.teams.get(STName.GREEN));
                                ReturnTeam(SumoTeam.teams.get(STName.YELLOW));
                                player.sendMessage(TextUtils.getReadyText("Игроки были возвращены в команду " + ChatColor.GRAY + "По умолчанию"));
                            } else TextUtils.warningEventGoingOn(player);
                        }
                        case "help" -> SumoTeam.HelpMessage(player);
                        case "start" -> {
                            if (SumoTeam.inLobby) {
                                SumoTeamSeconds.startCounter = 10;

                                if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM) && SumoTeam.countIce > 0){
                                    StructureUtils.honeyCombs[23].typeBreak = BreakType.SPAWN;
                                    StructureUtils.honeyCombs[28].typeBreak = BreakType.SPAWN;
                                    StructureUtils.honeyCombs[53].typeBreak = BreakType.SPAWN;
                                    StructureUtils.honeyCombs[58].typeBreak = BreakType.SPAWN;
                                    if (SumoTeam.countIce == 89)
                                        StructureUtils.ReplaceComb(new Random());
                                }
                                if (SumoTeam.gameMode.equals(STGameMode.KING_OF_THE_HILL))
                                    StructureUtils.honeyCombs[85].typeBreak = BreakType.CENTER;
                            } else player.sendMessage(TextUtils.getWarningText("Ивент уже идёт!"));
                        }
                        case "stop" -> {
                            if (!SumoTeam.inLobby) {
                                PlayerUtils.playerHits = new HashMap<>();

                                SumoTeamSeconds.startCounter = 11;

                                SumoTeam.inLobby = true;
                                SumoTeam.gameOver = false;
                                ScoreboardUtils.ResetScores();

                                StructureUtils.RemoveIce(new Random());

                                StopTeam(SumoTeam.teams.get(STName.RED));
                                StopTeam(SumoTeam.teams.get(STName.BLUE));
                                StopTeam(SumoTeam.teams.get(STName.GREEN));
                                StopTeam(SumoTeam.teams.get(STName.YELLOW));

                                WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location(WorldUtils.world, -6741, 157, 1218), Material.AIR, Material.BARRIER);

                                for (HoneyComb comb : StructureUtils.honeyCombs)
                                    if (!comb.typeBreak.equals(BreakType.NONE))
                                        comb.typeBreak = BreakType.NONE;

                                player.sendMessage(TextUtils.getReadyText(ChatColor.RED + "Ивент остановлен!"));
                            } else
                                player.sendMessage(TextUtils.getWarningText(ChatColor.GRAY + "Ивент ещё не начался!"));
                        }
                        case "gamemode" -> {
                            if (args.length > 3)
                                TextUtils.errorTooManyArguments(player);
                            else if (args.length == 1){
                                String message = "Режим игры: " + SumoTeam.gameMode.getChatColor() + SumoTeam.gameMode.name();
                                if (SumoTeam.gameMode.equals(STGameMode.KING_OF_THE_HILL))
                                    message += " " + SumoTeam.maxPoint + " очков";
                                else if (SumoTeam.gameMode.equals(STGameMode.CLASSIC))
                                    message += " " + SumoTeam.maxLives + " жизней";
                                player.sendMessage(TextUtils.getReadyText(message));
                            }
                            else {
                                if (SumoTeam.inLobby) {
                                    switch (args[1].toLowerCase()) {
                                        case "classic" -> {
                                            SumoTeam.gameMode = STGameMode.CLASSIC;
                                            player.sendMessage(TextUtils.getEditText("Режим игры изменён на " + SumoTeam.gameMode.getChatColor() + SumoTeam.gameMode.name()));
                                            if (args.length == 3) {
                                                try {
                                                    SumoTeam.maxLives = Integer.parseInt(args[2]);
                                                } catch (Exception error) {
                                                    TextUtils.errorCommandIncorrectly(player);
                                                    break;
                                                }
                                                player.sendMessage(TextUtils.getEditText("Количество жизней у команд изменёно на " + ChatColor.GREEN + SumoTeam.maxLives));
                                            }
                                        }
                                        case "king_of_the_hill" -> {
                                            SumoTeam.gameMode = STGameMode.KING_OF_THE_HILL;
                                            player.sendMessage(TextUtils.getEditText("Режим игры изменён на " + SumoTeam.gameMode.getChatColor() + SumoTeam.gameMode.name()));
                                            if (args.length == 3) {
                                                try {
                                                    SumoTeam.maxPoint = Integer.parseInt(args[2]);
                                                } catch (Exception error) {
                                                    TextUtils.errorCommandIncorrectly(player);
                                                    break;
                                                }
                                                player.sendMessage(TextUtils.getEditText("Количество очков изменёно на " + ChatColor.GOLD + SumoTeam.maxPoint));
                                            }
                                        }
                                        default -> TextUtils.errorCommandIncorrectly(player);
                                    }
                                } else TextUtils.warningEventGoingOn(player);
                            }
                        }
                        case "fieldmode" -> {
                            if (args.length > 3)
                                TextUtils.errorTooManyArguments(player);
                            else if (args.length == 1) {
                                String message = "Режим поля: " + SumoTeam.fieldMode.getChatColor() + SumoTeam.fieldMode.name();
                                if (SumoTeam.fieldMode.equals(STFieldMode.ICE_PLATFORM))
                                    message += " " + SumoTeam.fillProcent + "%";
                                player.sendMessage(TextUtils.getReadyText(message));
                            } else {
                                if (SumoTeam.inLobby) {
                                    switch (args[1].toLowerCase()) {
                                        case "classic" -> {
                                            SumoTeam.fieldMode = STFieldMode.CLASSIC;
                                            player.sendMessage(TextUtils.getEditText("Режим поля изменён на " + SumoTeam.fieldMode.getChatColor() + SumoTeam.fieldMode.name()));
                                        }
                                        case "ice_platform" -> {
                                            SumoTeam.fieldMode = STFieldMode.ICE_PLATFORM;
                                            player.sendMessage(TextUtils.getEditText("Режим поля изменён на " + SumoTeam.fieldMode.getChatColor() + SumoTeam.fieldMode.name()));
                                            if (args.length == 3) {
                                                try {
                                                    SumoTeam.fillProcent = Integer.parseInt(args[2]);
                                                } catch (Exception error) {
                                                    TextUtils.errorCommandIncorrectly(player);
                                                    break;
                                                }
                                                SumoTeam.countIce = (int) (89 * (SumoTeam.fillProcent / 100D));
                                                player.sendMessage(TextUtils.getEditText("Процент заполнения поля льдом изменён на " + ChatColor.AQUA + SumoTeam.fillProcent + "%"));
                                            }
                                        }
                                        default -> TextUtils.errorCommandIncorrectly(player);
                                    }
                                } else TextUtils.warningEventGoingOn(player);
                            }
                        }
                        case "reload" -> {
                            if (args.length > 1)
                                TextUtils.errorTooManyArguments(player);
                            else {
                                plugin.onDisable();
                                plugin.onEnable();
                            }
                            TextUtils.getReadyText("Плагин перезагружен!");
                        }
                        default -> TextUtils.errorCommandIncorrectly(player);
                    }
                }
            }
            else player.sendMessage(TextUtils.getErrorText(ChatColor.RED + "У вас нет прав на использование этой команды!"));
        }
        else sender.sendMessage(TextUtils.getErrorText("Вы не игрок!"));
        return true;
    }
    public static void JoinTeam(STTeam team, Player selectPlayer){
        selectPlayer.teleport(team.room);
        team.team.addEntity(selectPlayer);
    }

    public static void LeaveTeam(Player selectPlayer) {
        SumoTeam.teams.get(STName.UNSET).team.addEntity(selectPlayer);
        SumoTeam.teams.get(STName.UNSET).team.removeEntity(selectPlayer);
        PlayerUtils.players.get(selectPlayer.getName()).inField = false;
        selectPlayer.getInventory().clear();
        selectPlayer.teleport(SumoTeam.teams.get(STName.UNSET).room);
    }

    private void AddingTeams(STTeam team, Player selectPlayer, Player player){
        JoinTeam(team, selectPlayer);
        if (team == SumoTeam.teams.get(STName.UNSET)){
            team.team.removeEntity(selectPlayer);
            player.sendMessage(TextUtils.getReadyText("Игрок " + ChatColor.GRAY + selectPlayer.getName() + ChatColor.WHITE +  " удалён из своей команды!"));
        }
        else player.sendMessage(TextUtils.getReadyText("Игрок " + ChatColor.GRAY + selectPlayer.getName() + ChatColor.WHITE +  " добавлен в команду " + team.word + ChatColor.WHITE + "!"));
    }

    public static String ListTeam(STTeam team) {
        if (team.team.getEntries().size() > 0) {
            StringBuilder messageList = new StringBuilder("Игроки команды " + team.word + ChatColor.WHITE + " (" + team.chatColor + team.team.getEntries().size() + ChatColor.WHITE + "): ");
            for (String playerName : team.team.getEntries())
                messageList.append("\n       - ").append(ChatColor.GRAY).append(playerName);
            return messageList.toString();
        } else return "В команде " + team.word + ChatColor.WHITE + " нет игроков";
    }

    private void DivisionTeam(STTeam team, int countPlayersTeam) {
        for (int i = 0; i < countPlayersTeam; i++) {
            STTeam teamDefault = SumoTeam.teams.get(STName.DEFAULT);
            String[] arrayDefault = SumoTeam.ConvertToArrayString(teamDefault.team.getEntries());
            Player selectPlayer;
            if (teamDefault.team.getSize() == 1)
                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[0]);
            else
                selectPlayer = Bukkit.getServer().getPlayer(arrayDefault[Math.abs(new Random().nextInt() % (teamDefault.team.getSize() - 1))]);
            JoinTeam(team, selectPlayer);
        }
    }

    private void DivisionTeamLoad(STTeam team, String nameTeam) {
        HashMap<String, Player> listPlayers = SumoTeam.HashMapPlayers();
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

    public static void StartTeam(STTeam team) {
        team.lives = SumoTeam.maxLives;
        for (String playerName : team.team.getEntries()){
            Player selectPlayer = Bukkit.getServer().getPlayer(playerName);
            selectPlayer.teleport(team.field);
            PlayerUtils.players.get(playerName).inField = true;
            selectPlayer.sendMessage(TextUtils.getGameText(ChatColor.GREEN + "Ивент начался!"));
            selectPlayer.setGameMode(GameMode.ADVENTURE);
            SumoTeamInventory.giveItems(team.name,selectPlayer);
        }
    }

    public static void StopTeam(STTeam team) {
        team.fallPlayers = new ArrayList<>();
        team.points = 0;
        team.pointsF = 0;
        for (String playerName : team.team.getEntries())
            LeaveTeam(Bukkit.getServer().getPlayer(playerName));
    }
}
