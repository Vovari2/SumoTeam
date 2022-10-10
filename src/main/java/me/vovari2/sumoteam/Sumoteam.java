package me.vovari2.sumoteam;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Sumoteam extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void HelpMessage(Player player) {
        player.sendMessage("");
        player.sendMessage(Component.text(" ")
                .append(Component.text("=== ", TextColor.color(255, 255, 85)))
                .append(Component.text("Помощь для ", TextColor.color(85, 255, 255)))
                .append(Component.text("/sumoteam", ComponentUtils.Green))
                .append(Component.text(" ===", TextColor.color(255, 255, 85))));
        player.sendMessage(Component.text("  /st help", ComponentUtils.Green)
                .append(Component.text(" - Получение информации по командам ивента", TextColor.color(255,255,255))));
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
}
