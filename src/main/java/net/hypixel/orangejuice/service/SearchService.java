package net.hypixel.orangejuice.service;

import net.hypixel.orangejuice.generator.data.Gemstone;
import net.hypixel.orangejuice.generator.data.Icon;
import net.hypixel.orangejuice.generator.data.Rarity;
import net.hypixel.orangejuice.generator.data.Stat;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.spritesheet.Spritesheet;
import net.hypixel.orangejuice.util.Util;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class SearchService {
    public static List<String> itemNames(@Nullable String searchTerm) {
        return Spritesheet.getImageMap()
            .keySet()
            .stream()
            .filter(s -> Util.isNullOrBlank(searchTerm) || s.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<String> itemRarities(@Nullable String searchTerm) {
        return Rarity.getRarityNames()
            .stream()
            .filter(s -> Util.isNullOrBlank(searchTerm) || s.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<Icon> icons(@Nullable String searchTerm) {
        return Icon.getIcons()
            .stream()
            .filter(i -> Util.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<Stat> stats(@Nullable String searchTerm) {
        return Stat.getStats()
            .stream()
            .filter(i -> Util.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<Gemstone> gemstones(@Nullable String searchTerm) {
        return Gemstone.getGemstones()
            .stream()
            .filter(i -> Util.isNullOrBlank(searchTerm) || i.getName().toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }

    public static List<String> tooltipSide(@Nullable String searchTerm) {
        return Arrays.stream(MinecraftTooltipGenerator.TooltipSide.values())
            .map(MinecraftTooltipGenerator.TooltipSide::name)
            .filter(s -> Util.isNullOrBlank(searchTerm) || s.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }
}