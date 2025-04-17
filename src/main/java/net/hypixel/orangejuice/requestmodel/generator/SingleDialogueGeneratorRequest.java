package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ABIPHONE_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.ABIPHONE_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.DIALOGUE_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.MAX_LINE_LENGTH_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.MAX_LINE_LENGTH_EXAMPLE;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.SKIN_VALUE_DESCRIPTION;
import static net.hypixel.orangejuice.requestmodel.ApiDocsConstants.SKIN_VALUE_EXAMPLE;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request to generate multi npc dialogue")
@Data
public class SingleDialogueGeneratorRequest {

    @Schema(description = "Name of the npc", example = "Your best friend", requiredMode = Schema.RequiredMode.REQUIRED)
    private String npcName;

    @Schema(description = DIALOGUE_DESCRIPTION, example = "[\"Hello there!\",\"How are you doing?\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private String[] dialogue;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private int maxLineLength;

    @Schema(description = ABIPHONE_DESCRIPTION, example = ABIPHONE_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private boolean abiphone;

    @Schema(description = SKIN_VALUE_DESCRIPTION, example = SKIN_VALUE_EXAMPLE, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String skinValue;
}