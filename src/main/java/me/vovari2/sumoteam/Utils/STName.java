package me.vovari2.sumoteam.Utils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;

public enum STName {
    RED ("red", FireworkEffect.builder().withColor(Color.RED, ComponentUtils.getColor(ComponentUtils.Red)).with(FireworkEffect.Type.BALL).build()),
    BLUE ("blue", FireworkEffect.builder().withColor(Color.BLUE, ComponentUtils.getColor(ComponentUtils.Blue)).with(FireworkEffect.Type.BALL).build()),
    GREEN ("green", FireworkEffect.builder().withColor(Color.GREEN, ComponentUtils.getColor(ComponentUtils.Green)).with(FireworkEffect.Type.BALL).build()),
    YELLOW ("yellow", FireworkEffect.builder().withColor(Color.YELLOW, ComponentUtils.getColor(ComponentUtils.Yellow)).with(FireworkEffect.Type.BALL).build()),
    DEFAULT ("default", FireworkEffect.builder().withColor(Color.GRAY, ComponentUtils.getColor(ComponentUtils.Gray)).with(FireworkEffect.Type.BALL).build()),
    UNSET ("-", FireworkEffect.builder().withColor(Color.GRAY, ComponentUtils.getColor(ComponentUtils.Gray)).with(FireworkEffect.Type.BALL).build());

    private final String name;
    private final FireworkEffect effect;

    STName(String name, FireworkEffect effect){
        this.name = name;
        this.effect = effect;
    }

    public String getString(){
        return name;
    }
    public FireworkEffect getEffect(){
        return effect;
    }

    public final static STName[] listGameTeam = new STName[]{STName.RED, STName.BLUE, STName.GREEN, STName.YELLOW};

    public static STName getName(String str){
        if (str.equals("-"))
            return STName.UNSET;
        return STName.valueOf(str.toUpperCase());
    }
    public static boolean isSTName(String str){
        for (STName name : STName.values())
            if (name.getString().equals(str.toLowerCase()))
                return true;
        return false;
    }
}
