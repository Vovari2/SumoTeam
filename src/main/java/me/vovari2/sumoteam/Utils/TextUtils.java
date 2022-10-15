package me.vovari2.sumoteam.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TextUtils {
    public static Component getErrorText(String text){
        return Component.text("[!] ", ComponentUtils.Red, TextDecoration.BOLD).append(Component.text(text, ComponentUtils.Gray).decoration(TextDecoration.BOLD, false));}
    public static Component getEditText(String text){
        return Component.text("[", ComponentUtils.Aqua, TextDecoration.BOLD).append(Component.text("✎").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Aqua, TextDecoration.BOLD)).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    public static Component getWarningText(String text){
        return Component.text("[!] ", ComponentUtils.Yellow, TextDecoration.BOLD).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    public static Component getReadyText(String text){
        return Component.text("[", ComponentUtils.Green, TextDecoration.BOLD).append(Component.text("✔").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Green, TextDecoration.BOLD)).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    public static Component getGameText(String text){
        return Component.text("[", ComponentUtils.Gold, TextDecoration.BOLD).append(Component.text("⚔").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Gold, TextDecoration.BOLD)).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    public static Component getGameText(Component text){
        return Component.text("[", ComponentUtils.Gold, TextDecoration.BOLD).append(Component.text("⚔").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Gold, TextDecoration.BOLD)).color(ComponentUtils.White).append(text.decoration(TextDecoration.BOLD, false));}

    public static void errorCommandIncorrectly(Player player){
        player.sendMessage(getErrorText("Команда введена неверно! /sumoteam"));
    }
    public static void errorTooManyArguments(Player player){
        player.sendMessage(getErrorText("Слишком много параметров! /sumoteam"));
    }
    public static void errorTeamIncorrectly(Player player){
        player.sendMessage(getErrorText("Команда игроков введена неверно! /sumoteam"));
    }
    public static void errorPlayerIncorrectly(Player player){
        player.sendMessage(getErrorText("Имя игрока введено неверно!"));
    }
    public static void warningEventGoingOn(Player player){
        player.sendMessage(getWarningText(ChatColor.GRAY + "Ивент ещё идёт!"));
    }
}
