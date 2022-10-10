package me.vovari2.sumoteam.Modes;

import org.bukkit.ChatColor;

public enum STGameMode {
    CLASSIC (ChatColor.GRAY),
    KING_OF_THE_HILL (ChatColor.GOLD);

    private final ChatColor color;

    STGameMode(ChatColor color){
        this.color = color;
    }

    public ChatColor getChatColor(){
        return color;
    }
}
