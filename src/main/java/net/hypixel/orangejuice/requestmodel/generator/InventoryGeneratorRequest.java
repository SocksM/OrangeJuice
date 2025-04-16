package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate an inventory")
@Data
public class InventoryGeneratorRequest extends GeneratorRequestModelBase {

    @Schema(description = "The inventory string to be used for the generation", example = "TODO") // TODO: example
    private String inventoryString;

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