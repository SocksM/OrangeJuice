package net.hypixel.orangejuice.service;

import net.hypixel.orangejuice.generator.data.Rarity;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.spritesheet.Spritesheet;

import java.util.Arrays;
import java.util.List;

public class AutoComplete {
    public static List<String> itemNamesAutoCompletes() {
        return Spritesheet.getImageMap().keySet()
            .stream()
            .toList();
    }

    public static List<String> itemRaritiesAutoCompletes() {
        return Rarity.getRarityNames();
    }

    public static List<String> tooltipSideAutoCompletes() {
        return Arrays.stream(MinecraftTooltipGenerator.TooltipSide.values())
            .map(MinecraftTooltipGenerator.TooltipSide::name)
            .toList();
    }
}
