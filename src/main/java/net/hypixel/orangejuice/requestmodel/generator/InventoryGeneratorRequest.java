package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.hypixel.orangejuice.requestmodel.generator.submodels.InventoryItem;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.RENDER_BORDER_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.RENDER_BORDER_EXAMPLE;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate an inventory")
@Data
public class InventoryGeneratorRequest {

    @Schema(description = "The inventory string to be used for the generation")
    private InventoryItem[] inventoryItems;

    @Schema(description = "The number of rows in the inventory", example = "6")
    private int rows;

    @Schema(description = "The number of slots per row in the inventory", example = "9")
    private int slotsPerRow;

    @Schema(description = "The hovered item string to be used for the generation", example = "TODO") // TODO: example
    private String hoveredItemString;

    @Schema(description = "The name of the container", example = "My Inventory")
    private String containerName;

    @Schema(description = RENDER_BORDER_DESCRIPTION, example = RENDER_BORDER_EXAMPLE)
    private boolean renderBorder;
}