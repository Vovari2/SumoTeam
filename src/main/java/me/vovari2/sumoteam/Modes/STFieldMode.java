package me.vovari2.sumoteam.Modes;

import org.bukkit.ChatColor;

public enum STFieldMode {
    CLASSIC (ChatColor.GRAY),
    ICE_PLATFORM (ChatColor.AQUA);

    private final ChatColor color;

    STFieldMode(ChatColor color){
        this.color = color;
    }

    public ChatColor getChatColor(){
        return color;
    }
}
