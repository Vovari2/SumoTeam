package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Modes.STFieldMode;
import me.vovari2.sumoteam.Modes.STGameMode;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;

public final class SumoTeam extends JavaPlugin {

    static SumoTeam plugin;
    public static SumoTeamTask task;

    static boolean inLobby;
    static STGameMode gameMode = STGameMode.CLASSIC;
    static STFieldMode fieldMode = STFieldMode.CLASSIC;

    static HashMap<STName, STTeam> teams;
    public static HashMap<String, String> playerHits;
    static STName winTeam;



    @Override
    public void onEnable() {
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
        PlayerUtils.Initialization();
        ScoreboardUtils.Initialization();
        STTeam.Initialization();
        playerHits = new HashMap<>();

        // SumoTeam
        getServer().getPluginManager().registerEvents(new SumoTeamListener(), this);
        getCommand("sumoteam").setExecutor(new SumoTeamCommands());
        getCommand("sumoteam").setTabCompleter(new SumoTeamTabCompleter());

        getLogger().info(ChatColor.GREEN + "Enabled!");

        task = new SumoTeamTask();
        task.runTaskTimer(this, 0L, 1L);
    }

    @Override
    public void onDisable() {
        for (STTeam team : teams.values())
            team.team.unregister();
        if (ScoreboardUtils.scoreboard.getObjective("SumoTeam") != null)
            ScoreboardUtils.scoreboard.getObjective("SumoTeam").unregister();

        SumoTeamTask.RemoveIce(new Random());
    }

    static void HelpMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(Component.text(" ")
                .append(Component.text("=== ", ComponentUtils.Yellow))
                .append(Component.text("Помощь для ", ComponentUtils.Aqua)
                .append(Component.text("/sumoteam", ComponentUtils.Green))
                .append(Component.text(" ===", ComponentUtils.Yellow))));
        player.sendMessage(Component.text("  /st help", ComponentUtils.Green)
                .append(Component.text(" - Получение информации по командам ивента", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st trampoline ", ComponentUtils.Green)
                .append(Component.text("[forward/up]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Изменяемое направление батута:").append(Component.text("\n  - forward (Вперёд)\n  - up (Вверх)", ComponentUtils.Gray)))))
                .append(Component.text(" "))
                .append(Component.text("[Коэффициент]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Коэффициент усиления батута ").append(Component.text("(по умолчанию 1.0)", ComponentUtils.Gray)))))
                .append(Component.text(" - Включение/выключение батутов в мире", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st join ", ComponentUtils.Green)
                .append(Component.text("[Команда*]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Команда игроков ").append(Component.text("(обязательный параметр)", ComponentUtils.Gray)))))
                .append(Component.text(" "))
                .append(Component.text("[Игрок]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Имя одного игрока или *, выбирающая всех игроков"))))
                .append(Component.text(" - Добавление игрока в команду", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st list ", ComponentUtils.Green)
                .append(Component.text("[Команда]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Команда игроков"))))
                .append(Component.text(" - Просмотр списка игроков в командах", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st division ", ComponentUtils.Green)
                .append(Component.text("[max/load/save]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Дополнительные параметры:").append(Component.text("\n  - max (Распределение всех игроков)\n  - load (Загрузить сохранённый состав команд игроков)\n  - save (Сохранить состав команд игроков)", ComponentUtils.Gray)))))
                .append(Component.text(" - Распределение игроков поровну", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st return", ComponentUtils.Green).append(Component.text(" - Возвращение игроков в команду по умолчанию", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st gamemode ", ComponentUtils.Green)
                .append(Component.text("[CLASSIC/KING_OF_THE_HILL]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Режимы игры:").append(Component.text("\n  - CLASSIC (Каждая команда должна скинуть всех своих противников)\n  - ", ComponentUtils.Gray)).append(Component.text("KING_OF_THE_HILL", ComponentUtils.Gold).append(Component.text(" (Очки добавляются только на центре. Нужно набрать больше, чем у других команд)", ComponentUtils.Gray))))))
                .append(Component.text(" - Режим проведения ивента", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st fieldmode ", ComponentUtils.Green)
                .append(Component.text("[CLASSIC/ICE_PLATFORM]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Режимы поля:").append(Component.text("\n  - CLASSIC (Поле не изменяется)\n  - ", ComponentUtils.Gray)).append(Component.text("ICE_PLATFORM", ComponentUtils.Aqua).append(Component.text(" (Каждые несколько секунд, поле заменяет некоторые соты на лёд)", ComponentUtils.Gray))))))
                .append(Component.text(" [Параметр настройки]", ComponentUtils.Green).hoverEvent(HoverEvent.showText(Component.text("Дополнительный параметр для настройки режима:").append(Component.text("\n  - Для режима ", ComponentUtils.Gray)).append(Component.text("ICE_PLATFORM", ComponentUtils.Aqua).append(Component.text(" указывается процент заполения поля льдом", ComponentUtils.Gray))))))
                .append(Component.text(" - Режим изменения поля", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st start", ComponentUtils.Green).append(Component.text(" - Запуск ивента (перед этим, нужно распределить игроков на игровые команды)", ComponentUtils.White)));
        player.sendMessage(Component.text("  /st stop", ComponentUtils.Green).append(Component.text(" - Остановка ивента (убирает скорборд и возвращает игроков)", ComponentUtils.White)));
    }

    public static boolean CheckStringOnDouble(String str){
        try{
            Double.parseDouble(str);
            return true;
        } catch(Exception e) { return false; }
    }
}
