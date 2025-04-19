package net.hypixel.orangejuice.service.generator;

import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.impl.MinecraftInventoryGenerator;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.requestmodel.generator.InventoryGeneratorRequest;
import org.jetbrains.annotations.Nullable;

public class InventoryService {

    public static GeneratedObject generate(
        @Nullable String inventoryString,
        int rows,
        int slotsPerRow,
        @Nullable String hoveredItemString,
        @Nullable String containerName,
        @Nullable Boolean drawBorder
    ) {
        drawBorder = drawBorder == null || drawBorder;
        inventoryString = inventoryString == null ? "" : inventoryString;

        GeneratorImageBuilder generatedObject = new GeneratorImageBuilder()
            .addGenerator(new MinecraftInventoryGenerator.Builder()
                .withRows(rows)
                .withSlotsPerRow(slotsPerRow)
                .drawBorder(drawBorder)
                .drawBackground(true)
                .withContainerTitle(containerName)
                .withInventoryString(inventoryString)
                .build());

        if (hoveredItemString != null) {
            MinecraftTooltipGenerator tooltipGenerator = new MinecraftTooltipGenerator.Builder()
                .withItemLore(hoveredItemString)
                .withAlpha(MinecraftTooltip.DEFAULT_ALPHA)
                .withPadding(MinecraftTooltip.DEFAULT_PADDING)
                .isPaddingFirstLine(false)
                .disableRarityLineBreak(false)
                .withRenderBorder(true)
                .build();

            generatedObject.addGenerator(tooltipGenerator);
        }

        return generatedObject.build();
    }
}