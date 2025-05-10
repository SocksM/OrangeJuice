package net.hypixel.orangejuice.controller;

import net.hypixel.orangejuice.service.SearchService;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/item-id")
    public ResponseEntity itemIds(
        @RequestParam(required = false) @Nullable String searchTerm
    ) {
        try {
            return ResponseEntity.ok(SearchService.itemNames(searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting item ids", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/rarity")
    public ResponseEntity itemRarities(
        @RequestParam(required = false) @Nullable String searchTerm
    ) {
        try {
            return ResponseEntity.ok(SearchService.itemRarities(searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting rarities", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/tooltip-side")
    public ResponseEntity tooltipSide(
        @RequestParam(required = false) @Nullable String searchTerm
    ) {
        try {
            return ResponseEntity.ok(SearchService.tooltipSide(searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting tooltip sides", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/icon")
    public ResponseEntity icons(
        @RequestParam(required = false) @Nullable String searchTerm
    ) {
        try {
            return ResponseEntity.ok(SearchService.icons(searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting icons", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/stat")
    public ResponseEntity stats(
        @RequestParam(required = false) @Nullable String searchTerm
    ) {
        try {
            return ResponseEntity.ok(SearchService.stats(searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting tooltip sides", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/gemstone")
    public ResponseEntity gemstones(
        @RequestParam(required = false) @Nullable String searchTerm
    ) {
        try {
            return ResponseEntity.ok(SearchService.gemstones(searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting tooltip sides", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }
}