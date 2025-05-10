package net.hypixel.orangejuice.service.generator;

import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class SingleDialogueService {

    public static GeneratedObject generate(
        String npcName,
        String[] dialogue,
        @Nullable Integer maxLineLength,
        @Nullable Boolean abiphone,
        @Nullable String skinValue
    ) {
        abiphone = abiphone != null && abiphone;
        maxLineLength = maxLineLength == null ? 91 : maxLineLength;

        for (int i = 0; i < dialogue.length; i++) {
            dialogue[i] = "&e[NPC] " + npcName + "&f: " + (abiphone ? "&b%%ABIPHONE%%&f " : "") + dialogue[i];
            String line = dialogue[i];

            if (line.contains("{options:")) { // TODO: make a better way to check for options (probably just another json object)
                String[] split = line.split("\\{options: ?");
                dialogue[i] = split[0];
                String[] options = split[1].replace("}", "").split(", ");
                dialogue[i] += "\n&eSelect an option: &f";
                for (String option : options) {
                    dialogue[i] += "&a" + option + "&f ";
                }
            }
        }

        MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .withItemLore(String.join("\n", dialogue))
            .withAlpha(0)
            .withPadding(MinecraftTooltip.DEFAULT_PADDING)
            .isPaddingFirstLine(false)
            .disableRarityLineBreak(false)
            .withMaxLineLength(maxLineLength)
            .bypassMaxLineLength(true);

        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder()
            .addGenerator(tooltipGenerator.build());

        if (skinValue != null) {
            MinecraftPlayerHeadGenerator playerHeadGenerator = new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(skinValue)
                .withScale(-2)
                .build();
            generatorImageBuilder.addGenerator(0, playerHeadGenerator);
        }

        return generatorImageBuilder.build();
    }
}