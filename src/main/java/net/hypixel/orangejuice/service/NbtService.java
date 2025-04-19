package net.hypixel.orangejuice.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.image.MinecraftTooltip;
import net.hypixel.orangejuice.generator.impl.MinecraftItemGenerator;
import net.hypixel.orangejuice.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.orangejuice.generator.impl.tooltip.MinecraftTooltipGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.util.Util;
import net.hypixel.orangejuice.util.exception.NbtParseException;
import net.hypixel.orangejuice.util.model.Pair;
import org.jetbrains.annotations.Nullable;

public class NbtService {

    // TODO find a better way to return the gen command because this is a bit messy (There shouldn't be a discord command in the general API)(aslo double return pair is wacky)
    public static Pair<GeneratedObject, String> parseNbtString(
        String nbt,
        @Nullable Integer alpha,
        @Nullable Integer padding
    ) {
        alpha = alpha == null ? MinecraftTooltip.DEFAULT_ALPHA : alpha;
        padding = padding == null ? MinecraftTooltip.DEFAULT_PADDING : padding;

        JsonObject jsonObject = JsonParser.parseString(nbt).getAsJsonObject();
        JsonObject tagObject = jsonObject.get("tag").getAsJsonObject();
        GeneratorImageBuilder generatorImageBuilder = new GeneratorImageBuilder();

        if (jsonObject.get("id").getAsString().contains("skull")) {
            String value = jsonObject.get("id").getAsString();
            value = value.replace("minecraft:", "")
                .replace("skull", "player_head");
            jsonObject.addProperty("id", value);
        }

        if (jsonObject.get("id").getAsString().equalsIgnoreCase("player_head")
            && tagObject.get("SkullOwner") != null) {
            JsonArray textures = tagObject.get("SkullOwner").getAsJsonObject()
                .get("Properties").getAsJsonObject()
                .get("textures").getAsJsonArray();

            if (textures.size() > 1) {
                throw new NbtParseException("Multiple textures found for player head! Please provide a single texture.");
            }

            String base64 = textures.get(0).getAsJsonObject().get("Value").getAsString();

            generatorImageBuilder.addGenerator(new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(base64)
                .build()
            );
        } else {
            generatorImageBuilder.addGenerator(new MinecraftItemGenerator.Builder()
                .withItem(jsonObject.get("id").getAsString())
                //.isEnchanted(enchanted) TODO: determine if the item is enchanted
                .isBigImage()
                .build());
        }

        int maxLineLength = Util.getLongestLine(jsonObject.get("tag").getAsJsonObject()
            .get("display").getAsJsonObject()
            .get("Lore").getAsJsonArray()
            .asList()
            .stream()
            .map(JsonElement::getAsString)
            .toList()).getRight();

        MinecraftTooltipGenerator.Builder tooltipGenerator = new MinecraftTooltipGenerator.Builder()
            .parseNbtJson(jsonObject)
            .withAlpha(alpha)
            .withPadding(padding)
            .withRenderBorder(true)
            .isPaddingFirstLine(true)
            .withMaxLineLength(maxLineLength);

        GeneratedObject generatedObject = generatorImageBuilder.addGenerator(tooltipGenerator.build()).build();
        String parsedCommand =
            "Your NBT input has been parsed into a slash command:" + System.lineSeparator() +
                "```" + System.lineSeparator() + tooltipGenerator.buildSlashCommand() + "```";

        return new Pair<>(generatedObject, parsedCommand);
    }
}
