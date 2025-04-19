package net.hypixel.orangejuice.requestmodel.generator.submodels;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ITEM_ID_EXAMPLE;

@Data
public class InventoryItem {

    @Schema(description = ITEM_ID_DESCRIPTION, example = ITEM_ID_EXAMPLE, requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemId;

    @Schema(description = "Where the item should be located in the inventory", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    private int location;

    @Schema(description = "The amount of the item", example = "3", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int amount;

    @Schema(description = "Extra attributes for the item", example = "[\"enchanted\", \"hover\"]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String[] extraData;

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(itemId);

        for (String extra : extraData) {
            result.append(",").append(extra);
        }

        result.append(":[").append(location).append("]");

        if (amount > 0) { // Only appended when an input is given (default int in java is 0)
            result.append(":").append(amount);
        }

        return result.toString();
    }

    public static String toStringFromArray(InventoryItem[] items) {
        if (items == null || items.length == 0) {
            return "";
        }

        if (items.length == 1) {
            return items[0].toString();
        }

        StringBuilder result = new StringBuilder();
        result.append(items[0].toString());
        for (int i = 1; i < items.length; i++) {
            result.append("%%").append(items[i].toString());
        }

        return result.toString();
    }
}