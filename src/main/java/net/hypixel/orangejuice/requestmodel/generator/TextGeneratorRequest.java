package net.hypixel.orangejuice.requestmodel.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Schema(description = "Request model for text generation")
@Data
public class TextGeneratorRequest extends GeneratorRequestModelBase {

    @Schema(description = "Text to generate", example = "Hello, Hydar?")
    private String text;

    @Schema(description = "Whether the text should be centered", example = "false")
    private Boolean centered;

    @Schema(description = ALPHA_DESCRIPTION, example = ALPHA_EXAMPLE)
    private Integer alpha;

    @Schema(description = PADDING_DESCRIPTION, example = PADDING_EXAMPLE)
    private Integer padding;

    @Schema(description = MAX_LINE_LENGTH_DESCRIPTION, example = MAX_LINE_LENGTH_EXAMPLE)
    private Integer maxLineLength;

    @Schema(description = RENDER_BORDER_DESCRIPTION, example = RENDER_BORDER_EXAMPLE)
    private Boolean renderBorder;
}