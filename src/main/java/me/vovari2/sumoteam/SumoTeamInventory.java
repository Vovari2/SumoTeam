package me.vovari2.sumoteam;

import me.vovari2.sumoteam.Utils.ComponentUtils;
import me.vovari2.sumoteam.Utils.STName;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class SumoTeamInventory {
    public static ItemStack Stick(Material material, List<Pattern> patterns, List<Component> lore, Component displayName) {
        ItemStack stick = new ItemStack(material);
        BannerMeta itemMeta = (BannerMeta) stick.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.setPatterns(patterns);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        stick.setItemMeta(itemMeta);
        stick.addUnsafeEnchantment(Enchantment.KNOCKBACK, 2);
        stick.addUnsafeEnchantment(Enchantment.LOYALTY, 3);
        return stick;
    }

    public static ItemStack Chestplate(int color, List<Component> lore, Component displayName) {
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.setColor(Color.fromRGB(color));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        chestplate.setItemMeta(itemMeta);
        return chestplate;
    }
    public static ItemStack Leggings(int color,  List<Component> lore, Component displayName) {
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) leggings.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.setColor(Color.fromRGB(color));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        leggings.setItemMeta(itemMeta);
        return leggings;
    }
    public static ItemStack Boots(int color, List<Component> lore, Component displayName) {
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) boots.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(lore);
        itemMeta.setColor(Color.fromRGB(color));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setUnbreakable(true);
        boots.setItemMeta(itemMeta);
        return boots;
    }

    public static void giveItems(STName teamName, Player player){
        player.getInventory().clear();
        switch (teamName){
            case RED: giveItemsRed(player); break;
            case BLUE: giveItemsBlue(player); break;
            case GREEN: giveItemsGreen(player); break;
            case YELLOW: giveItemsYellow(player); break;
        }
    }
    // Красная команда
    public static void giveItemsRed(Player player){
        ArrayList<Component> loreStick = new ArrayList<>();
        loreStick.add(Component.text("Пропитана кровью твоих врагов", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        ArrayList<Pattern> patternStick = new ArrayList<>();
        patternStick.add(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_BOTTOM));
        patternStick.add(new Pattern(DyeColor.BLACK, PatternType.TRIANGLE_TOP));
        patternStick.add(new Pattern(DyeColor.RED, PatternType.GRADIENT));
        patternStick.add(new Pattern(DyeColor.RED, PatternType.GRADIENT_UP));
        patternStick.add(new Pattern(DyeColor.BLACK, PatternType.RHOMBUS_MIDDLE));
        patternStick.add(new Pattern(DyeColor.RED, PatternType.CIRCLE_MIDDLE));
        patternStick.add(new Pattern(DyeColor.RED, PatternType.CURLY_BORDER));
        player.getInventory().setItem(0, Stick(Material.WHITE_BANNER, patternStick, loreStick, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Д", TextColor.color(199, 69, 56))).append(Component.text("у", TextColor.color(181, 48, 39))).append(Component.text("б", TextColor.color(163, 22, 22))).append(Component.text("и", TextColor.color(163, 22, 22))).append(Component.text("н", TextColor.color(181, 48, 39))).append(Component.text("а", TextColor.color(199, 69, 56))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreChestplate = new ArrayList<>();
        loreChestplate.add(Component.text("Вы чувствуете ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreChestplate.add(Component.text("как все силы поверженных врагов ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreChestplate.add(Component.text("скопились в ваших руках", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setChestplate(Chestplate(14158860, loreChestplate, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("К", TextColor.color(199, 69, 56))).append(Component.text("и", TextColor.color(181, 48, 39))).append(Component.text("р", TextColor.color(163, 22, 22))).append(Component.text("а", TextColor.color(163, 22, 22))).append(Component.text("с", TextColor.color(181, 48, 39))).append(Component.text("а", TextColor.color(199, 69, 56))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreLeggings = new ArrayList<>();
        loreLeggings.add(Component.text("По вашим ногам ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreLeggings.add(Component.text("словно по кровеносным сосудам ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreLeggings.add(Component.text("волнами пульсирует сила", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setLeggings(Leggings(11405321, loreLeggings, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("П", TextColor.color(199, 69, 56))).append(Component.text("о", TextColor.color(181, 48, 39))).append(Component.text("н", TextColor.color(163, 22, 22))).append(Component.text("о", TextColor.color(163, 22, 22))).append(Component.text("ж", TextColor.color(181, 48, 39))).append(Component.text("и", TextColor.color(199, 69, 56))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreBoots = new ArrayList<>();
        loreBoots.add(Component.text("Неприятное шмыганье и ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("кровавые следы от ботинок ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("поражают в ужас ваших врагов", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setBoots(Boots(8783109, loreBoots, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Б", TextColor.color(199, 69, 56))).append(Component.text("о", TextColor.color(187, 55, 45))).append(Component.text("т", TextColor.color(175, 40, 33))).append(Component.text("и", TextColor.color(163, 22, 22))).append(Component.text("н", TextColor.color(175, 40, 33))).append(Component.text("к", TextColor.color(187, 55, 45))).append(Component.text("и", TextColor.color(199, 69, 56))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));
    }

    // Синяя команда
    public static void giveItemsBlue(Player player){
        ArrayList<Component> loreStick = new ArrayList<>();
        loreStick.add(Component.text("Отдаёт морским бризом и ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreStick.add(Component.text("духом морских приключений", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        ArrayList<Pattern> patternStick = new ArrayList<>();
        patternStick.add(new Pattern(DyeColor.WHITE, PatternType.GRADIENT));
        patternStick.add(new Pattern(DyeColor.CYAN, PatternType.CURLY_BORDER));
        patternStick.add(new Pattern(DyeColor.BLUE, PatternType.TRIANGLES_BOTTOM));
        patternStick.add(new Pattern(DyeColor.BLUE, PatternType.TRIANGLES_TOP));
        patternStick.add(new Pattern(DyeColor.CYAN, PatternType.CROSS));
        patternStick.add(new Pattern(DyeColor.WHITE, PatternType.CIRCLE_MIDDLE));
        player.getInventory().setItem(0, Stick(Material.CYAN_BANNER, patternStick, loreStick, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Д", TextColor.color(73,115,179))).append(Component.text("у", TextColor.color(47,94,164))).append(Component.text("б", TextColor.color(11,73,149))).append(Component.text("и", TextColor.color(11,73,149))).append(Component.text("н", TextColor.color(47,94,164))).append(Component.text("а", TextColor.color(73,115,179))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreChestplate = new ArrayList<>();
        loreChestplate.add(Component.text("Кираса будто тянет вас на приключения", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreChestplate.add(Component.text("к центру поля!", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreChestplate.add(Component.text("Дабы вы, как настоящий пират ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreChestplate.add(Component.text("заполучили желаемый приз!", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setChestplate(Chestplate(678332, loreChestplate, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("К", TextColor.color(73,115,179))).append(Component.text("и", TextColor.color(47,94,164))).append(Component.text("р", TextColor.color(11,73,149))).append(Component.text("а", TextColor.color(11,73,149))).append(Component.text("с", TextColor.color(47,94,164))).append(Component.text("а", TextColor.color(73,115,179))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreLeggings = new ArrayList<>();
        loreLeggings.add(Component.text("Довольно мокро ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreLeggings.add(Component.text("но зато не сковывает ваши движения", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setLeggings(Leggings(607632, loreLeggings, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("П", TextColor.color(73,115,179))).append(Component.text("о", TextColor.color(47,94,164))).append(Component.text("н", TextColor.color(11,73,149))).append(Component.text("о", TextColor.color(11,73,149))).append(Component.text("ж", TextColor.color(47,94,164))).append(Component.text("и", TextColor.color(73,115,179))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreBoots = new ArrayList<>();
        loreBoots.add(Component.text("Вы чувствуете ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("как сила 7 морей ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("волнами несёт вас прямо к победе", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setBoots(Boots(799334, loreBoots, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Б", TextColor.color(73,115,179))).append(Component.text("о", TextColor.color(56,101,169))).append(Component.text("т", TextColor.color(37,87,159))).append(Component.text("и", TextColor.color(11,73,149))).append(Component.text("н", TextColor.color(37,87,159))).append(Component.text("к", TextColor.color(56,101,169))).append(Component.text("и", TextColor.color(73,115,179))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));
    }

    // Зелёная команда
    public static void giveItemsGreen(Player player){
        ArrayList<Component> loreStick = new ArrayList<>();
        loreStick.add(Component.text("Сама сила леса ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreStick.add(Component.text("наделяет ваши руки зарядом бодрости и", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreStick.add(Component.text("прекрасным запахом мяты", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        ArrayList<Pattern> patternStick = new ArrayList<>();
        patternStick.add(new Pattern(DyeColor.BROWN, PatternType.CURLY_BORDER));
        patternStick.add(new Pattern(DyeColor.BROWN, PatternType.TRIANGLES_TOP));
        patternStick.add(new Pattern(DyeColor.BROWN, PatternType.TRIANGLE_BOTTOM));
        patternStick.add(new Pattern(DyeColor.BROWN, PatternType.STRIPE_MIDDLE));
        patternStick.add(new Pattern(DyeColor.BROWN, PatternType.TRIANGLE_TOP));
        patternStick.add(new Pattern(DyeColor.GREEN, PatternType.TRIANGLES_TOP));
        patternStick.add(new Pattern(DyeColor.GREEN, PatternType.TRIANGLES_BOTTOM));
        patternStick.add(new Pattern(DyeColor.GREEN, PatternType.RHOMBUS_MIDDLE));
        patternStick.add(new Pattern(DyeColor.LIME, PatternType.CIRCLE_MIDDLE));
        patternStick.add(new Pattern(DyeColor.GREEN, PatternType.FLOWER));
        patternStick.add(new Pattern(DyeColor.LIME, PatternType.CURLY_BORDER));
        player.getInventory().setItem(0, Stick(Material.GREEN_BANNER, patternStick, loreStick, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Д", TextColor.color(73,231,61))).append(Component.text("у", TextColor.color(47,170,37))).append(Component.text("б", TextColor.color(25,133,17))).append(Component.text("и", TextColor.color(25,133,17))).append(Component.text("н", TextColor.color(47,170,37))).append(Component.text("а", TextColor.color(73,231,61))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreChestplate = new ArrayList<>();
        loreChestplate.add(Component.text("Кираса сделана из нитей тысячи шёлкопрядов и", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreChestplate.add(Component.text("покрашенная в природный, зелённый цвет", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setChestplate(Chestplate(2536723, loreChestplate, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("К", TextColor.color(73,231,61))).append(Component.text("и", TextColor.color(47,170,37))).append(Component.text("р", TextColor.color(25,133,17))).append(Component.text("а", TextColor.color(25,133,17))).append(Component.text("с", TextColor.color(47,170,37))).append(Component.text("а", TextColor.color(73,231,61))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreLeggings = new ArrayList<>();
        loreLeggings.add(Component.text("Природа поддержит вас", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreLeggings.add(Component.text("на пути к вашей цели", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setLeggings(Leggings(1803298, loreLeggings, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("П", TextColor.color(73,231,61))).append(Component.text("о", TextColor.color(47,170,37))).append(Component.text("н", TextColor.color(25,133,17))).append(Component.text("о", TextColor.color(25,133,17))).append(Component.text("ж", TextColor.color(47,170,37))).append(Component.text("и", TextColor.color(73,231,61))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreBoots = new ArrayList<>();
        loreBoots.add(Component.text("Вы слышите ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("как приятный шелест листев ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("шуршит под вашими ногами", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setBoots(Boots(1660193, loreBoots, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Б", TextColor.color(73,231,61))).append(Component.text("о", TextColor.color(57,197,46))).append(Component.text("т", TextColor.color(41,165,32))).append(Component.text("и", TextColor.color(25,133,17))).append(Component.text("н", TextColor.color(41,165,32))).append(Component.text("к", TextColor.color(57,197,46))).append(Component.text("и", TextColor.color(73,231,61))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));
    }

    // Жёлтая команда
    public static void giveItemsYellow(Player player){
        ArrayList<Component> loreStick = new ArrayList<>();
        loreStick.add(Component.text("На запах как мёд", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreStick.add(Component.text("а может и на вкус как мёд?", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        ArrayList<Pattern> patternStick = new ArrayList<>();
        patternStick.add(new Pattern(DyeColor.WHITE, PatternType.CROSS));
        patternStick.add(new Pattern(DyeColor.BROWN, PatternType.TRIANGLE_TOP));
        patternStick.add(new Pattern(DyeColor.ORANGE, PatternType.STRIPE_TOP));
        patternStick.add(new Pattern(DyeColor.YELLOW, PatternType.STRIPE_BOTTOM));
        patternStick.add(new Pattern(DyeColor.ORANGE, PatternType.CURLY_BORDER));
        patternStick.add(new Pattern(DyeColor.ORANGE, PatternType.BORDER));
        player.getInventory().setItem(0, Stick(Material.ORANGE_BANNER, patternStick, loreStick, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Д", TextColor.color(216,198,98))).append(Component.text("у", TextColor.color(233,211,68))).append(Component.text("б", TextColor.color(250,224,3))).append(Component.text("и", TextColor.color(250,224,3))).append(Component.text("н", TextColor.color(233,211,68))).append(Component.text("а", TextColor.color(216,198,98))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreChestplate = new ArrayList<>();
        loreChestplate.add(Component.text("Её свет озаряет ваши очи", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setChestplate(Chestplate(16764424, loreChestplate, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("К", TextColor.color(216,198,98))).append(Component.text("и", TextColor.color(233,211,68))).append(Component.text("р", TextColor.color(250,224,3))).append(Component.text("а", TextColor.color(250,224,3))).append(Component.text("с", TextColor.color(233,211,68))).append(Component.text("а", TextColor.color(216,198,98))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreLeggings = new ArrayList<>();
        loreLeggings.add(Component.text("Вы чувствуете ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreLeggings.add(Component.text("как тепло солнца ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreLeggings.add(Component.text("наполняет ваше тело", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setLeggings(Leggings(13542411, loreLeggings, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("П", TextColor.color(216,198,98))).append(Component.text("о", TextColor.color(233,211,68))).append(Component.text("н", TextColor.color(250,224,3))).append(Component.text("о", TextColor.color(250,224,3))).append(Component.text("ж", TextColor.color(233,211,68))).append(Component.text("и", TextColor.color(216,198,98))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));

        ArrayList<Component> loreBoots = new ArrayList<>();
        loreBoots.add(Component.text("Вы ощущаете ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("как пол превращается в раскалённую плазму ", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        loreBoots.add(Component.text("под вашими ногами", TextColor.color(170, 170, 170)).decorations(ComponentUtils.textBoldAndItalic));
        player.getInventory().setBoots(Boots(10386699, loreBoots, Component.text("═", TextColor.color(255, 132, 0)).append(Component.text("╡", TextColor.color(221, 160, 29))).append(Component.text("Б", TextColor.color(216,198,98))).append(Component.text("о", TextColor.color(227,207,79))).append(Component.text("т", TextColor.color(239,215,55))).append(Component.text("и", TextColor.color(250,224,3))).append(Component.text("н", TextColor.color(239,215,55))).append(Component.text("к", TextColor.color(227,207,79))).append(Component.text("и", TextColor.color(216,198,98))).append(Component.text("╞", TextColor.color(221, 160, 29))).append(Component.text("═", TextColor.color(255, 132, 0))).decorations(ComponentUtils.textBoldAndNotItalic)));
    }
}
