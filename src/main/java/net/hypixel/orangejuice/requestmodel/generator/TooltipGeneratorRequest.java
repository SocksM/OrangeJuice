package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Request model for tooltip generation")
@Data
public class TooltipGeneratorRequest extends GeneratorRequestModelBase {
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

    @Schema(description = RECIPE_DESCRIPTION, example = RECIPE_EXAMPLE)
    private String recipe;

    @Schema(description = ALPHA_DESCRIPTION, example = ALPHA_EXAMPLE)
    private Integer alpha;

    @Schema(description = PADDING_DESCRIPTION, example = PADDING_EXAMPLE)
    private Integer padding;

    @Schema(description = "Disable rarity line break", example = "false")
    private Boolean disableRarityLineBreak;

    @Schema(description = "Is enchanted", example = "true")
    private Boolean enchanted;

    @Schema(description = "Is centered", example = "false")
    private Boolean centered;

    @Schema(description = "Padding for the first line", example = "true")
    private Boolean paddingFirstLine;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE)
    private Integer maxLineLength;

    @Schema(description = "Tooltip side", example = "left")
    private String tooltipSide;

    @Schema(description = "Render border", example = "true")
    private Boolean renderBorder;
}