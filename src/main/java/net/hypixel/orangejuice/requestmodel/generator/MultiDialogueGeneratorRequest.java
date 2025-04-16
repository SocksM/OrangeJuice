package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate multi npc dialogue")
@Data
public class MultiDialogueGeneratorRequest extends GeneratorRequestModelBase {

    @Schema(description = "Names of the npcs", example = "TODO") // TODO
    private String npcNames;

    @Schema(description = DIALOGUE_DESCRIPTION, example = "TODO") // TODO
    private String dialogue;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE)
    private int maxLineLength;

    @Schema(description = ABIPHONE_DESCRIPTION, example = ABIPHONE_EXAMPLE)
    private boolean abiphone;

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE)
    private String skinValue;
}