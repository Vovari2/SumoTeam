package me.vovari2.sumoteam;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TextUtils {
    static Component getErrorText(String text){
        return Component.text("[!] ", ComponentUtils.Red, TextDecoration.BOLD).append(Component.text(text, ComponentUtils.Gray).decoration(TextDecoration.BOLD, false));}
    static Component getEditText(String text){
        return Component.text("[", ComponentUtils.Aqua, TextDecoration.BOLD).append(Component.text("✎").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Aqua, TextDecoration.BOLD)).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    static Component getWarningText(String text){
        return Component.text("[!] ", ComponentUtils.Yellow, TextDecoration.BOLD).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    static Component getReadyText(String text){
        return Component.text("[", ComponentUtils.Green, TextDecoration.BOLD).append(Component.text("✔").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Green, TextDecoration.BOLD)).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    static Component getGameText(String text){
        return Component.text("[", ComponentUtils.Gold, TextDecoration.BOLD).append(Component.text("⚔").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Gold, TextDecoration.BOLD)).append(Component.text(text, ComponentUtils.White).decoration(TextDecoration.BOLD, false));}
    static Component getGameText(Component component){
        return Component.text("[", ComponentUtils.Gold, TextDecoration.BOLD).append(Component.text("⚔").decoration(TextDecoration.BOLD, false)).append(Component.text("] ", ComponentUtils.Gold, TextDecoration.BOLD)).append(component.decoration(TextDecoration.BOLD, false));}

    static void errorCommandIncorrectly(Player player){
        player.sendMessage(getErrorText("Команда введена неверно! /sumoteam"));
    }
    static void errorTooManyArguments(Player player){
        player.sendMessage(getErrorText("Слишком много параметров! /sumoteam"));
    }
    static void errorTeamIncorrectly(Player player){
        player.sendMessage(getErrorText("Команда игроков введена неверно! /sumoteam"));
    }
    static void errorPlayerIncorrectly(Player player){
        player.sendMessage(getErrorText("Имя игрока введено неверно!"));
    }
    static void warningEventGoingOn(Player player){
        player.sendMessage(getWarningText(ChatColor.GRAY + "Ивент ещё идёт!"));
    }
}
