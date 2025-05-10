package net.hypixel.orangejuice.service.generator;

import net.hypixel.orangejuice.generator.data.Rarity;
import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.impl.MinecraftInventoryGenerator;
import net.hypixel.orangejuice.generator.impl.MinecraftItemGenerator;
import net.hypixel.orangejuice.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.util.Util;
import org.jetbrains.annotations.Nullable;

public class TooltipService {

    public static GeneratedObject generate(
        String itemName,
        String itemLore,
        @Nullable String type,
        @Nullable String rarity,
        @Nullable String itemId,
        @Nullable String skinValue,
        @Nullable String recipe,
        @Nullable Integer alpha,
        @Nullable Integer padding,
        @Nullable Boolean disableRarityLineBreak,
        @Nullable Boolean enchanted,
        @Nullable Boolean centered,
        @Nullable Boolean paddingFirstLine,
        @Nullable Integer maxLineLength,
        @Nullable String tooltipSide,
        @Nullable Boolean renderBorder
    ) {
        type = type == null ? "" : type;
        rarity = rarity == null ? "none" : rarity;
        alpha = alpha == null ? MinecraftTooltip.DEFAULT_ALPHA : alpha;
        padding = padding == null ? MinecraftTooltip.DEFAULT_PADDING : padding;
        disableRarityLineBreak = disableRarityLineBreak == null || disableRarityLineBreak;
        centered = centered != null && centered;
        enchanted = enchanted != null && enchanted;
        paddingFirstLine = paddingFirstLine == null || paddingFirstLine;
        maxLineLength = maxLineLength == null ? MinecraftTooltipGenerator.DEFAULT_MAX_LINE_LENGTH : maxLineLength;
        renderBorder = renderBorder == null || renderBorder;

        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder();
        MinecraftTooltipGenerator tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .withName(itemName)
            .withRarity(Rarity.byName(rarity))
            .withItemLore(itemLore)
            .withType(type)
            .withAlpha(alpha)
            .withPadding(padding)
            .disableRarityLineBreak(disableRarityLineBreak)
            .withMaxLineLength(maxLineLength)
            .isTextCentered(centered)
            .isPaddingFirstLine(paddingFirstLine)
            .withRenderBorder(renderBorder)
            .build();

        if (!Util.isNullOrBlank(itemId)) {
            if (itemId.equalsIgnoreCase("player_head")) {
                MinecraftPlayerHeadGenerator.Builder generator = new MinecraftPlayerHeadGenerator.Builder()
                    .withScale(-2);

                if (skinValue != null) {
                    generator.withSkin(skinValue);
                }

                generatorImageBuilder.addGenerator(generator.build());
            } else {
                generatorImageBuilder.addGenerator(new MinecraftItemGenerator.Builder()
                    .withItem(itemId)
                    .isEnchanted(enchanted)
                    .isBigImage()
                    .build());
            }
        }

        if (!Util.isNullOrBlank(recipe)) {
            generatorImageBuilder.addGenerator(0, new MinecraftInventoryGenerator.Builder()
                .withRows(3)
                .withSlotsPerRow(3)
                .drawBorder(renderBorder)
                .withInventoryString(recipe)
                .build()
            ).build();
        }

        if (tooltipSide != null && MinecraftTooltipGenerator.TooltipSide.valueOf(tooltipSide.toUpperCase()) == MinecraftTooltipGenerator.TooltipSide.LEFT) {
            generatorImageBuilder.addGenerator(0, tooltipGenerator);
        } else {
            generatorImageBuilder.addGenerator(tooltipGenerator);
        }

        return generatorImageBuilder.build();
    }
}
