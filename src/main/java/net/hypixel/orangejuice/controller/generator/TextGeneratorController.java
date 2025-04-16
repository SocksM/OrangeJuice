package net.hypixel.orangejuice.controller.generator;

import net.hypixel.orangejuice.requestmodel.generator.TextGeneratorRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.service.Generator;
import net.hypixel.orangejuice.util.HttpUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Tag(name = "Text Generator", description = "APIs for text generation")
@Log4j2
@RestController
@RequestMapping("/generator/text")
public class TextGeneratorController {

    @Operation(summary = "Generate text", description = "Generates text based on the provided request")
    @PostMapping("")
    public ResponseEntity generate(@RequestBody TextGeneratorRequest request) {
        try {
            return HttpUtil.properApiImageReturn(
                Generator.generateText(
                    request.getText(),
                    request.getCentered(),
                    request.getAlpha(),
                    request.getPadding(),
                    request.getMaxLineLength(),
                    request.getRenderBorder()
                )
            );
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }
}