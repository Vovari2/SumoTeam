package me.vovari2.sumoteam;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Color;

import java.util.HashMap;
import java.util.Map;

class ComponentUtils {

    static Map<TextDecoration, TextDecoration.State> textBoldAndNotItalic;
    static Map<TextDecoration, TextDecoration.State> textBoldAndItalic;
    static TextColor White = TextColor.color(255,255,255);
    static TextColor Gold = TextColor.color(255, 170, 0);
    static TextColor Green = TextColor.color(85, 255, 85);
    static TextColor Gray = TextColor.color(170,170,170);
    static TextColor Aqua = TextColor.color(85, 255, 255);
    static TextColor Red = TextColor.color(255, 85, 85);
    static TextColor Blue = TextColor.color(85, 85, 255);
    static TextColor Yellow = TextColor.color(255, 255, 85);

    static Color getColor (TextColor color){
        return Color.fromRGB(color.red(), color.green(), color.blue());
    }

    static void Initialization() {
        textBoldAndNotItalic = new HashMap<>();
        textBoldAndNotItalic.put(TextDecoration.BOLD, TextDecoration.State.TRUE);
        textBoldAndNotItalic.put(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        textBoldAndItalic = new HashMap<>();
        textBoldAndItalic.put(TextDecoration.BOLD, TextDecoration.State.TRUE);
        textBoldAndItalic.put(TextDecoration.ITALIC, TextDecoration.State.TRUE);
    }
}
