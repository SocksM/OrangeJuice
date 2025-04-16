package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate a player head")
@Data
public class HeadGeneratorRequest extends GeneratorRequestModelBase {

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE)
    private String skinValue;
}