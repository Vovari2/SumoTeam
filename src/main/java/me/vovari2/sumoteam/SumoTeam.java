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
    public static SumoTeamTask task;

    public static boolean inLobby = true;
    public static STGameMode gameMode = STGameMode.CLASSIC;
    public static STFieldMode fieldMode = STFieldMode.CLASSIC;
    public static int fillProcent = 50;
    public static int countIce = 46;

    public static HashMap<STName, STTeam> teams;
    public static STName winTeam;

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
        STTeam.Initialization();
        StructureUtils.Initialization();
        PlayerUtils.Initialization();

        // SumoTeam
        getServer().getPluginManager().registerEvents(new SumoTeamListener(), this);
        getCommand("sumoteam").setExecutor(new SumoTeamCommands());
        getCommand("sumoteam").setTabCompleter(new SumoTeamTabCompleter());

        task = new SumoTeamTask();
        task.runTaskTimer(this, 0L, 1L);

        getLogger().info(ChatColor.GREEN + "Enabled for " + (System.currentTimeMillis() - miliseconds) + " ms");
    }

    @Override
    public void onDisable() {
        for (STTeam team : teams.values())
            team.team.unregister();
        if (ScoreboardUtils.scoreboard.getObjective("SumoTeam") != null)
            ScoreboardUtils.scoreboard.getObjective("SumoTeam").unregister();

        WorldUtils.replace(new Location(WorldUtils.world, -6757, 149, 1234), new Location (WorldUtils.world, -6741, 157, 1218), Material.AIR, Material.BARRIER);
        SumoTeamTask.RemoveIce(new Random());
    }

    public static void HelpMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(Component.text(" ")
                .append(Component.text("=== ", ComponentUtils.Yellow))
                .append(Component.text("Помощь для ", ComponentUtils.Aqua)
                .append(Component.text("/sumoteam", ComponentUtils.Green))
                .append(Component.text(" ===", ComponentUtils.Yellow))));
        player.sendMessage(Component.text("  /st help", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Получение информации по командам ивента", ComponentUtils.White))));
        player.sendMessage(Component.text("  /st trampoline ", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Включение/выключение батутов в мире", ComponentUtils.White)))
                .append(Component.text("[forward/up]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Изменяемое направление батута:").append(Component.text("\n  - forward (Вперёд)\n  - up (Вверх)", ComponentUtils.Gray)))))
                .append(Component.text(" "))
                .append(Component.text("[Коэффициент]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Коэффициент усиления батута ").append(Component.text("(по умолчанию 1.0)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st join ", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Добавление игрока в указанную команду", ComponentUtils.White)))
                .append(Component.text("[Команда*]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Команда игроков ").append(Component.text("(обязательный параметр)", ComponentUtils.Gray)))))
                .append(Component.text(" "))
                .append(Component.text("[Игрок]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Имя одного игрока или *").append(Component.text("\n(* выбирает всех игроков на ивенте)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st list ", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Просмотр списка игроков в командах", ComponentUtils.White)))
                .append(Component.text("[Команда]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Команда игроков или *").append(Component.text("\n(* показывает список игроков на ивенте)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st division ", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Распределение игроков поровну", ComponentUtils.White)))
                .append(Component.text("[max/load/save]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Дополнительные параметры:").append(Component.text("\n  - max (Распределение всех игроков)\n  - load (Загрузить сохранённый состав команд игроков)\n  - save (Сохранить состав команд игроков)", ComponentUtils.Gray))))));
        player.sendMessage(Component.text("  /st return", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Возвращение игроков в команду по умолчанию", ComponentUtils.White))));
        player.sendMessage(Component.text("  /st gamemode ", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Режим проведения ивента", ComponentUtils.White)))
                .append(Component.text("[CLASSIC/KING_OF_THE_HILL]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Режимы игры:").append(Component.text("\n  - CLASSIC (Каждая команда должна скинуть всех своих противников)\n  - ", ComponentUtils.Gray)).append(Component.text("KING_OF_THE_HILL", ComponentUtils.Gold).append(Component.text(" (Очки добавляются только на центре. Нужно набрать больше, чем у других команд)", ComponentUtils.Gray)))))));
        player.sendMessage(Component.text("  /st fieldmode ", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Режим изменения поля", ComponentUtils.White)))
                .append(Component.text("[CLASSIC/ICE_PLATFORM]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Режимы поля:").append(Component.text("\n  - CLASSIC (Поле не изменяется)\n  - ", ComponentUtils.Gray)).append(Component.text("ICE_PLATFORM", ComponentUtils.Aqua).append(Component.text(" (Каждые несколько секунд, поле заменяет некоторые соты на лёд)", ComponentUtils.Gray))))))
                .append(Component.text(" [Параметр настройки]", ComponentUtils.Gray).hoverEvent(HoverEvent.showText(Component.text("Дополнительный параметр для настройки режима:").append(Component.text("\n  - Для режима ", ComponentUtils.Gray)).append(Component.text("ICE_PLATFORM", ComponentUtils.Aqua).append(Component.text(" указывается процент заполения поля льдом", ComponentUtils.Gray)))))));
        player.sendMessage(Component.text("  /st start", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Запуск ивента (перед этим, нужно распределить игроков на игровые команды)", ComponentUtils.White))));
        player.sendMessage(Component.text("  /st stop", ComponentUtils.White).hoverEvent(HoverEvent.showText(Component.text("Остановка ивента (убирает скорборд и возвращает игроков)", ComponentUtils.White))));
        player.sendMessage(Component.text(" ")
                .append(Component.text("===========================", ComponentUtils.Yellow)));
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
