package me.vovari2.sumoteam.Honeycomb;

import me.vovari2.sumoteam.SumoTeam;
import me.vovari2.sumoteam.WorldUtils;
import org.bukkit.ChatColor;
import org.bukkit.structure.Structure;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

public class StructureHoneyComb {
    private final Structure[] structures;

    public StructureHoneyComb(Structure[] structures){
        this.structures = structures;
    }

    public Structure getRandomVariation(Random R){
        if (structures.length < 2)
            return structures[0];
        return structures[R.nextInt(structures.length-1)];
    }

    public Structure[] getStructures(){
        return structures;
    }




    public static HashMap<CombType, HashMap<CombColor, StructureHoneyComb>> LoadStructures() {
        String nameFolder = SumoTeam.plugin.getDataFolder() + "/Structures/";
        HashMap<CombType, HashMap<CombColor, StructureHoneyComb>> structures = new HashMap<>();

        HashMap<CombColor, StructureHoneyComb> fullStructures = new HashMap<>();
        fullStructures.put(CombColor.RED, new StructureHoneyComb(LoadFullCombs("red")));
        fullStructures.put(CombColor.BLUE, new StructureHoneyComb(LoadFullCombs("blue")));
        fullStructures.put(CombColor.GREEN, new StructureHoneyComb(LoadFullCombs("green")));
        fullStructures.put(CombColor.YELLOW, new StructureHoneyComb(LoadFullCombs("yellow")));
        fullStructures.put(CombColor.GLASS, new StructureHoneyComb(LoadFullCombs("glass")));
        fullStructures.put(CombColor.ICE, new StructureHoneyComb(LoadFullCombs("ice")));
        structures.put(CombType.FULL, fullStructures);

        HashMap<CombColor, StructureHoneyComb> tramStructures = new HashMap<>();
        tramStructures.put(CombColor.RED, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "redtrampoline.mcstructure")}));
        tramStructures.put(CombColor.BLUE, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "bluetrampoline.mcstructure")}));
        tramStructures.put(CombColor.GREEN, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "greentrampoline.mcstructure")}));
        tramStructures.put(CombColor.YELLOW, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "yellowtrampoline.mcstructure")}));
        tramStructures.put(CombColor.GLASS, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "glasstrampoline.mcstructure")}));
        tramStructures.put(CombColor.ICE, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "icetrampoline1.mcstructure"), LoadStructure(nameFolder + "icetrampoline2.mcstructure")}));
        structures.put(CombType.TRAMPOLINE, tramStructures);

        HashMap<CombColor, StructureHoneyComb> holeStructures = new HashMap<>();
        holeStructures.put(CombColor.GLASS, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "glasshole.mcstructure")}));
        holeStructures.put(CombColor.ICE, new StructureHoneyComb(new Structure[]{LoadStructure(nameFolder + "icehole.mcstructure")}));
        structures.put(CombType.HOLE, holeStructures);

        return structures;
    }

    // Получение нескольких структур через цикл
    private static Structure[] LoadFullCombs(String color){
        Structure[] arrayStructures = new Structure[5];
        for(int i = 0; i < 5; i++)
            arrayStructures[i] = LoadStructure(SumoTeam.plugin.getDataFolder() + "/Structures/" + color + "full" + (i+1) + ".mcstructure");
        return arrayStructures;
    }

    // Получение структуры из файла в папке плагина
    private static Structure LoadStructure(String nameFile) {
        try{
            return WorldUtils.structManager.loadStructure(new File(nameFile));
        }
        catch (Exception error) {
            SumoTeam.plugin.getLogger().info(ChatColor.RED + "Не получилось загрузить файл \"" + nameFile +  "\":");
            SumoTeam.plugin.getLogger().info(ChatColor.RED + error.getMessage());
            return WorldUtils.structManager.createStructure();
        }
    }
}
