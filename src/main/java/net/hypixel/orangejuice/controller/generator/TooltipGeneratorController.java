package net.hypixel.orangejuice.controller.generator;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.service.Generator;
import net.hypixel.orangejuice.util.HttpUtil;
import net.hypixel.orangejuice.requestmodel.generator.TooltipGeneratorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/generator/tooltip")
@Tag(name = "Tooltip Generator", description = "APIs for tooltip generation")
public class TooltipGeneratorController {

    @Operation(summary = "Generate tooltip", description = "Generates a tooltip image based on the provided request")
    @PostMapping("")
    public ResponseEntity generate(@RequestBody TooltipGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                Generator.generateTooltip(
                    request.getItemName(),
                    request.getItemLore(),
                    request.getType(),
                    request.getRarity(),
                    request.getItemId(),
                    request.getSkinValue(),
                    request.getRecipe(), // TODO: Make this a list of items instead of a string
                    request.getAlpha(),
                    request.getPadding(),
                    request.getDisableRarityLineBreak(),
                    request.getEnchanted(),
                    request.getCentered(),
                    request.getPaddingFirstLine(),
                    request.getMaxLineLength(),
                    request.getTooltipSide(),
                    request.getRenderBorder()
                )
            );
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}
