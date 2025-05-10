package net.hypixel.orangejuice.service.generator;

import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class TextService {

    public static GeneratedObject generate(
        String text,
        @Nullable Boolean centered,
        @Nullable Integer alpha,
        @Nullable Integer padding,
        @Nullable Integer maxLineLength,
        @Nullable Boolean renderBorder
    ) {
        centered = centered != null && centered;
        alpha = alpha == null ? MinecraftTooltip.DEFAULT_ALPHA : alpha;
        padding = padding == null ? MinecraftTooltip.DEFAULT_PADDING : padding;
        maxLineLength = maxLineLength == null ? MinecraftTooltipGenerator.DEFAULT_MAX_LINE_LENGTH : maxLineLength;
        renderBorder = renderBorder != null && renderBorder;

        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder();
        MinecraftTooltipGenerator tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .withItemLore(text)
            .withAlpha(alpha)
            .withPadding(padding)
            .withMaxLineLength(maxLineLength)
            .isTextCentered(centered)
            .isPaddingFirstLine(false)
            .disableRarityLineBreak(false)
            .withRenderBorder(renderBorder)
            .build();

        generatorImageBuilder.addGenerator(tooltipGenerator);
        return generatorImageBuilder.build();
    }
}
