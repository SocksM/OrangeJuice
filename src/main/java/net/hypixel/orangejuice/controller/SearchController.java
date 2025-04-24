package net.hypixel.orangejuice.controller;

import net.hypixel.orangejuice.service.AutoCompleteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/item-id")
    public ResponseEntity itemIds() {
        try {
            return ResponseEntity.ok(AutoCompleteService.itemNamesAutoCompletes());
        } catch (Exception exception) {
            log.error("Encountered an error while getting item ids", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/item-id/{searchTerm}")
    public ResponseEntity itemIds(@PathVariable String searchTerm) {
        try {
            return ResponseEntity.ok(contains(AutoCompleteService.itemNamesAutoCompletes(), searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting item ids", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/rarity")
    public ResponseEntity itemRarities() {
        try {
            return ResponseEntity.ok(AutoCompleteService.itemRaritiesAutoCompletes());
        } catch (Exception exception) {
            log.error("Encountered an error while getting rarities", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/rarity/{searchTerm}")
    public ResponseEntity itemRarities(@PathVariable String searchTerm) {
        try {
            return ResponseEntity.ok(contains(AutoCompleteService.itemRaritiesAutoCompletes(), searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting rarities", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/tooltip-side")
    public ResponseEntity tooltipSide() {
        try {
            return ResponseEntity.ok(AutoCompleteService.tooltipSideAutoCompletes());
        } catch (Exception exception) {
            log.error("Encountered an error while getting tooltip sides", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    @GetMapping("/tooltip-side/{searchTerm}")
    public ResponseEntity tooltipSide(@PathVariable String searchTerm) {
        try {
            return ResponseEntity.ok(contains(AutoCompleteService.tooltipSideAutoCompletes(), searchTerm));
        } catch (Exception exception) {
            log.error("Encountered an error while getting tooltip sides", exception);
            return ResponseEntity.status(500).body("An error occurred while processing the request" + exception.getCause());
        }
    }

    private static List<String> contains(List<String> set, String searchTerm) {
        return set
            .stream()
            .filter(itemName -> itemName.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }
}