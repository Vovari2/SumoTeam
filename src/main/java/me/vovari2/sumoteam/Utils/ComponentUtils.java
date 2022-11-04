package me.vovari2.sumoteam.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;

import java.util.HashMap;
import java.util.Map;

public class ComponentUtils {

    public static Map<TextDecoration, TextDecoration.State> textBoldAndNotItalic;
    public static Map<TextDecoration, TextDecoration.State> textBoldAndItalic;

    public static TextColor White = TextColor.color(255,255,255);
    public static TextColor Gold = TextColor.color(255, 170, 0);
    public static TextColor Green = TextColor.color(85, 255, 85);
    public static TextColor Gray = TextColor.color(170,170,170);
    public static TextColor Aqua = TextColor.color(85, 255, 255);
    public static TextColor Red = TextColor.color(255, 85, 85);
    public static TextColor Blue = TextColor.color(85, 85, 255);
    public static TextColor Yellow = TextColor.color(255, 255, 85);

    public static Component lineStyle;

    public static Color getColor (TextColor color){
        return Color.fromRGB(color.red(), color.green(), color.blue());
    }

    public static void Initialization() {
        textBoldAndNotItalic = new HashMap<>();
        textBoldAndNotItalic.put(TextDecoration.BOLD, TextDecoration.State.TRUE);
        textBoldAndNotItalic.put(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        textBoldAndItalic = new HashMap<>();
        textBoldAndItalic.put(TextDecoration.BOLD, TextDecoration.State.TRUE);
        textBoldAndItalic.put(TextDecoration.ITALIC, TextDecoration.State.TRUE);

        lineStyle = Component.text("");
        for (int i = 113; i < 210; i+=4)
            lineStyle = lineStyle.append(Component.text(" ", TextColor.color(252, i, 9), TextDecoration.STRIKETHROUGH));
        for (int i = 209; i > 112; i-=4)
            lineStyle = lineStyle.append(Component.text(" ", TextColor.color(252, i, 9), TextDecoration.STRIKETHROUGH));
    }
}
