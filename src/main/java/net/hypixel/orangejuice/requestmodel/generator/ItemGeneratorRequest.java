package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.SKIN_VALUE_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.SKIN_VALUE_EXAMPLE;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate a player head")
@Data
public class ItemGeneratorRequest {

    @Schema(description = ITEM_ID_DESCRIPTION, example = ITEM_ID_EXAMPLE)
    private String itemId;

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE)
    private String skinValue;

    @Schema(description = "Adds the hover effect to the item", example = "false")
    private boolean hoverEffect;

    @Schema(description = "Adds the enchanted effect to the item", example = "false")
    private boolean enchanted;

    @Schema(description = "Extra miscellaneous data for the item", example = "") // TODO: example (not sure if its actually applicable here)
    private String data;
}