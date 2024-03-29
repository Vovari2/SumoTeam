package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Modes.STGameMode;
import me.vovari2.sumoteam.Utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public final class SumoTeam extends JavaPlugin {

    public static SumoTeam plugin;
    public static SumoTeamTicks taskTicks;
    public static SumoTeamSeconds taskSeconds;

    public static boolean inLobby = true;
    public static STGameMode gameMode = STGameMode.CLASSIC;
    public static int maxLives = 5;
    public static int maxPoint = 100;
    public static STFieldMode fieldMode = STFieldMode.CLASSIC;
    public static int fillProcent = 100;
    public static int countIce = 89;

    public static HashMap<STName, STTeam> teams;

    public static STName winTeam;
    public static boolean gameOver;

    @Override
    public void onEnable() {
        long miliseconds = System.currentTimeMillis();
        getLogger().info(ChatColor.BLUE + "Loading...");
        plugin = this;

        // Чтение данных с конфига
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        SumoTeamConfigTeams.create();
        SumoTeamConfigTeams.get().options().copyDefaults(true);
        SumoTeamConfigTeams.save();

        SumoTeamListener.scaleForward = getConfig().getDouble("ScaleForward");
        SumoTeamListener.scaleUp = getConfig().getDouble("ScaleUp");

        ComponentUtils.Initialization();
        WorldUtils.Initialization(getServer().getWorld(getConfig().getString("World")));
        ScoreboardUtils.Initialization();
        STTeam.Initialization(maxLives);
        StructureUtils.Initialization();
        PlayerUtils.Initialization();
        VectorUtils.Initialization();

        // SumoTeam
        getServer().getPluginManager().registerEvents(new SumoTeamListener(), this);
        getCommand("sumoteam").setExecutor(new SumoTeamCommands());
        getCommand("sumoteam").setTabCompleter(new SumoTeamTabCompleter());

        taskTicks = new SumoTeamTicks();
        taskTicks.runTaskTimer(this, 0L, 1L);


        taskSeconds = new SumoTeamSeconds();
        taskSeconds.runTaskTimer(this, 0L, 20L);

        getLogger().info(ChatColor.GREEN + "Enabled for " + (System.currentTimeMillis() - miliseconds) + " ms");
    }

    @Override
    public void onDisable() {
        if (taskTicks != null)
            taskTicks.cancel();

        SumoTeamCommands.StopTeam(SumoTeam.teams.get(STName.RED));
        SumoTeamCommands.StopTeam(SumoTeam.teams.get(STName.BLUE));
        SumoTeamCommands.StopTeam(SumoTeam.teams.get(STName.GREEN));
        SumoTeamCommands.StopTeam(SumoTeam.teams.get(STName.YELLOW));

        for (STTeam team : teams.values())
            team.team.unregister();

        if (ScoreboardUtils.scoreboard.getObjective("SumoTeam") != null)
            ScoreboardUtils.scoreboard.getObjective("SumoTeam").unregister();

        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1218), Material.AIR, Material.BARRIER);
        StructureUtils.RemoveIce(new Random());
    }

    public static void HelpMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(Component.text(" ")
                .append(Component.text("=== ", ComponentUtils.Yellow))
                .append(Component.text("Помощь для ", ComponentUtils.Aqua)
                .append(Component.text("/sumoteam", ComponentUtils.Green))
                .append(Component.text(" ===", ComponentUtils.Yellow))));
        player.sendMessage(Component.text("  /st help", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Получение информации по командам ивента", ComponentUtils.White))));
        player.sendMessage(Component.text("  /st trampoline ", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Включение/выключение батутов в мире", ComponentUtils.White)))
                .append(Component.text("[forward/up]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Изменяемое направление батута:").append(Component.text("\n  - forward (Вперёд)\n  - up (Вверх)", ComponentUtils.Gray)))))
                .append(Component.text(" "))
                .append(Component.text("[Коэффициент]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Коэффициент усиления батута ").append(Component.text("(по умолчанию 1.0)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st join ", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Добавление игрока в указанную команду", ComponentUtils.White)))
                .append(Component.text("[Команда*]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Команда игроков ").append(Component.text("(обязательный параметр)", ComponentUtils.Gray)))))
                .append(Component.text(" "))
                .append(Component.text("[Игрок]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Имя одного игрока или *").append(Component.text("\n(* выбирает всех игроков на ивенте)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st list ", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Просмотр списка игроков в командах", ComponentUtils.White)))
                .append(Component.text("[Команда]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Команда игроков или *").append(Component.text("\n(* показывает список игроков на ивенте)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st division ", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Распределение игроков поровну на 4 команды", ComponentUtils.White)))
                .append(Component.text("[max/duo/load/save]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Дополнительные параметры:").append(Component.text("\n  - max (Распределение всех игроков)\n  - duo (Распределение игроков на 2 команды)\n  - load (Загрузить сохранённый состав команд игроков)\n  - save (Сохранить состав команд игроков)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st return", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Возвращение игроков в команду по умолчанию", ComponentUtils.White))));
        player.sendMessage(Component.text("  /st gamemode ", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Режим проведения ивента", ComponentUtils.White)))
                .append(Component.text("[CLASSIC/KING_OF_THE_HILL]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Режимы игры:").append(Component.text("\n  - CLASSIC (Каждая команда должна скинуть всех своих противников)\n  - ", ComponentUtils.Gray)).append(Component.text("KING_OF_THE_HILL", ComponentUtils.Gold).append(Component.text(" (Очки добавляются только на центре. Нужно набрать больше, чем у других команд)", ComponentUtils.Gray))))))
                .append(Component.text(" [Параметр]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Дополнительный параметр для настройки режима:").append(Component.text("\n  - Для режима CLASSIC", ComponentUtils.Gray)).append(Component.text("", ComponentUtils.Gold).append(Component.text(" указывается количество жизней у каждой команды", ComponentUtils.Gray)))
                        .append(Component.text("\n  - Для режима ", ComponentUtils.Gray)).append(Component.text("KING_OF_THE_HILL", ComponentUtils.Gold).append(Component.text(" указывается количество очков, которое нужно набрать для победы", ComponentUtils.Gray)))))));
        player.sendMessage(Component.text("  /st fieldmode ", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Режим изменения поля", ComponentUtils.White)))
                .append(Component.text("[CLASSIC/ICE_PLATFORM]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Режимы поля:").append(Component.text("\n  - CLASSIC (Поле не изменяется)\n  - ", ComponentUtils.Gray)).append(Component.text("ICE_PLATFORM", ComponentUtils.Aqua).append(Component.text(" (Каждые несколько секунд, поле заменяет некоторые соты на лёд)", ComponentUtils.Gray))))))
                .append(Component.text(" [Параметр]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Дополнительный параметр для настройки режима:").append(Component.text("\n  - Для режима ", ComponentUtils.Gray)).append(Component.text("ICE_PLATFORM", ComponentUtils.Aqua).append(Component.text(" указывается процент заполения поля льдом", ComponentUtils.Gray)))))));
        player.sendMessage(Component.text("  /st start", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Запуск ивента (перед этим, нужно распределить игроков на игровые команды)", ComponentUtils.White))));
        player.sendMessage(Component.text("  /st stop", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Остановка ивента (убирает скорборд и возвращает игроков)", ComponentUtils.White))));
        player.sendMessage(Component.text(" ")
                .append(Component.text("===========================", ComponentUtils.Yellow)));
    }

    public static void InfoMessage(Player player){
        player.sendMessage("");
        player.sendMessage(ComponentUtils.lineStyle);
        if (gameMode.equals(STGameMode.CLASSIC)){
            player.sendMessage(Component.text("           Режим игры: ")
                    .append(Component.text(gameMode.toString(), ComponentUtils.Gray)
                    .append(Component.text("\n   Для победы необходимо скинуть\n    всех игроков из других команд.\n   Каждая команда имеет ", ComponentUtils.Gray).append(Component.text(maxLives, ComponentUtils.Green)).append(Component.text(" жизней, \n после падения тратиться одна жизнь. \n       Когда жизни закончаться, \n      игроки будут возвращаться\n           в комнату ожидания.", ComponentUtils.Gray)))));
        }
        else {
            player.sendMessage(Component.text("     Режим игры: ")
                    .append(Component.text(gameMode.toString(), ComponentUtils.Gold))
                    .append(Component.text("\n         Для победы необходимо\n            набрать ", ComponentUtils.Gray))
                    .append(Component.text(maxPoint, ComponentUtils.Gold))
                    .append(Component.text(" очков.\n Очки можно получить на центре карты.\n  После падения, игроки появляются\n       на спавне своей команды.\n ", ComponentUtils.Gray)));
        }
        if (fieldMode.equals(STFieldMode.CLASSIC)){
            player.sendMessage(Component.text("\n           Режим поля: ")
                    .append(Component.text(fieldMode.toString(), ComponentUtils.Gray)
                            .append(Component.text("\n       Поле никак не изменяется.", ComponentUtils.Gray))));
        }
        else {
            if (countIce == 0){
                player.sendMessage(Component.text("\n       Режим поля: ")
                        .append(Component.text(fieldMode.toString(), ComponentUtils.Aqua)
                                .append(Component.text("\n     Поле заполнено льдом на ", ComponentUtils.Gray))
                                .append(Component.text("0%", ComponentUtils.Aqua)).append(Component.text("\n     поэтому поле не изменяется.", ComponentUtils.Gray))));
            }
            else if (countIce == 89){
                player.sendMessage(Component.text("\n       Режим поля: ")
                        .append(Component.text(fieldMode.toString(), ComponentUtils.Aqua)
                                .append(Component.text("\n    Поле заполнено льдом на ", ComponentUtils.Gray))
                                .append(Component.text("100%", ComponentUtils.Aqua))
                                .append(Component.text(".\n  Все ледяные платформы ломаются,\n       если на них долго стоять.", ComponentUtils.Gray))));
            }
            else {
                player.sendMessage(Component.text("\n        Режим поля: ")
                        .append(Component.text(fieldMode.toString(), ComponentUtils.Aqua)
                                .append(Component.text("\n    Поле заполнено льдом на ", ComponentUtils.Gray))
                                .append(Component.text(fillProcent + "%", ComponentUtils.Aqua))
                                .append(Component.text(".\n  Все ледяные платформы ломаются,\n       если на них долго стоять.\n      Каждые 20 минут платформы\n      случайно перезаполняются.", ComponentUtils.Gray))));
            }
        }
        player.sendMessage(ComponentUtils.lineStyle);
    }


    public static boolean CheckStringOnDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        } catch(Exception e) { return false; }
    }

    public static ArrayList<String> ListNamePlayers(){
        ArrayList<String> ListPlayers = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            ListPlayers.add(player.getName());
        }
        return ListPlayers;
    }
    public static HashMap<String, Player> HashMapPlayers(){
        HashMap<String, Player> HashMapPlayers = new HashMap<>();
        for(Player player : Bukkit.getOnlinePlayers()){
            HashMapPlayers.put(player.getName(), player);
        }
        return HashMapPlayers;
    }
    public static String[] ConvertToArrayString(Set<String> list){
        String[] array = new String[list.size()]; int i = 0;
        for (String playerName : list) {
            array[i] = playerName;
            i++;
        }
        return array;
    }
}
