package me.vovari2.sumoteam;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.HashMap;
import java.util.Map;

class ComponentUtils {

    static Map<TextDecoration, TextDecoration.State> textBoldAndNotItalic;
    static Map<TextDecoration, TextDecoration.State> textBoldAndItalic;
    static TextColor White;
    static TextColor Gold;
    static TextColor Green;
    static TextColor Gray;
    static TextColor Aqua;

    static {
        textBoldAndNotItalic = new HashMap<>();
        textBoldAndNotItalic.put(TextDecoration.BOLD, TextDecoration.State.TRUE);
        textBoldAndNotItalic.put(TextDecoration.ITALIC, TextDecoration.State.FALSE);

        textBoldAndItalic = new HashMap<>();
        textBoldAndItalic.put(TextDecoration.BOLD, TextDecoration.State.TRUE);
        textBoldAndItalic.put(TextDecoration.ITALIC, TextDecoration.State.TRUE);

        White = TextColor.color(255,255,255);
        Gold = TextColor.color(255, 170, 0);
        Green = TextColor.color(85, 255, 85);
        Gray = TextColor.color(170,170,170);
        Aqua = TextColor.color(85, 255, 255);
    }
}
