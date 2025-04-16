package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate a recipe")
@Data
public class RecipeGeneratorRequest extends GeneratorRequestModelBase {

    @Schema(description = RECIPE_DESCRIPTION, example = RECIPE_EXAMPLE)
    private String recipe;

    @Schema(description = "Whether to render the background", example = "true")
    private boolean renderBackground;
}
