package me.vovari2.sumoteam.Utils;

import org.bukkit.entity.Player;

public class STPlayer {
    public Player player;
    public boolean inField;
    public boolean isLive;
    public int timeDeath;

    public STPlayer(Player player){
        this.player = player;
        inField = false;
        isLive = true;
        timeDeath = 0;
    }
}
