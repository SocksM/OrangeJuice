package net.hypixel.orangejuice.controller;

import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.service.NbtService;
import net.hypixel.orangejuice.util.HttpUtil;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/nbtparse")
public class NbtParseController {

    @GetMapping("/{nbt}/image")
    public ResponseEntity nbtParseImage(
        @PathVariable String nbt,
        @RequestParam(required = false) @Nullable Integer alpha,
        @RequestParam(required = false) @Nullable Integer padding
    ) {
        try {
            return HttpUtil.properApiImageReturn(NbtService.parseNbtString(nbt, alpha, padding).value1());
        } catch (GeneratorException | IOException exception) {
            log.error("Encountered an error while generating the image", exception);
            return ResponseEntity.status(500).body("An error occurred during image generation: " + exception.getCause());
        }
    }

    @GetMapping("/{nbt}/arguments")
    public ResponseEntity nbtParseArguments(
        @PathVariable String nbt,
        @RequestParam(required = false) @Nullable Integer alpha,
        @RequestParam(required = false) @Nullable Integer padding
    ) {
        return ResponseEntity.status(501).body("This endpoint is not implemented yet"); // TODO
    }
}