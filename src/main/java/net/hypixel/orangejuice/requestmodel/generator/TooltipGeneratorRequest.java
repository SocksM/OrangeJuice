package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.hypixel.orangejuice.requestmodel.generator.submodels.InventoryItem;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ALPHA_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ALPHA_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.MAX_LINE_LENGTH_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.MAX_LINE_LENGTH_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.PADDING_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.PADDING_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.RECIPE_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.SKIN_VALUE_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.SKIN_VALUE_EXAMPLE;

@Schema(description = "Request model for tooltip generation")
@Data
public class TooltipGeneratorRequest {
    @Schema(description = "Item name", example = "Sword of Legends")
    private String itemName;

    @Schema(description = "Item lore", example = "A legendary sword")
    private String itemLore;

    @Schema(description = "Item type", example = "SWORD")
    private String type;

    @Schema(description = "Item rarity", example = "legendary")
    private String rarity;

    @Schema(description = ITEM_ID_DESCRIPTION, example = ITEM_ID_EXAMPLE)
    private String itemId;

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE)
    private String skinValue;

    @Schema(description = RECIPE_DESCRIPTION)
    private InventoryItem[] recipe;

    @Schema(description = ALPHA_DESCRIPTION, example = ALPHA_EXAMPLE)
    private int alpha;

    @Schema(description = PADDING_DESCRIPTION, example = PADDING_EXAMPLE)
    private int padding;

    @Schema(description = "Disable rarity line break", example = "false")
    private boolean disableRarityLineBreak;

    @Schema(description = "Is enchanted", example = "true")
    private boolean enchanted;

    @Schema(description = "Is centered", example = "false")
    private boolean centered;

    @Schema(description = "Padding for the first line", example = "true")
    private boolean paddingFirstLine;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE)
    private int maxLineLength;

    @Schema(description = "Tooltip side", example = "left")
    private String tooltipSide;

    @Schema(description = "Render border", example = "true")
    private boolean renderBorder;
}