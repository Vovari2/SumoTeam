package me.vovari2.sumoteam.Utils;

import org.bukkit.entity.Player;

public class STPlayer {
    public Player player;
    public boolean inField;
    public boolean inJump;

    public STPlayer(Player player){
        this.player = player;
        inField = false;
        inJump = false;
    }
}
