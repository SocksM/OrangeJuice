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

    @GetMapping("/itemid")
    public ResponseEntity itemIds() {
        return ResponseEntity.ok(AutoCompleteService.itemNamesAutoCompletes());
    }

    @GetMapping("/itemid/{searchTerm}")
    public ResponseEntity itemIds(@PathVariable String searchTerm) {
        return ResponseEntity.ok(contains(AutoCompleteService.itemNamesAutoCompletes(), searchTerm));
    }

    @GetMapping("/rarity")
    public ResponseEntity itemRarities() {
        return ResponseEntity.ok(AutoCompleteService.itemRaritiesAutoCompletes());
    }

    @GetMapping("/rarity/{searchTerm}")
    public ResponseEntity itemRarities(@PathVariable String searchTerm) {
        return ResponseEntity.ok(contains(AutoCompleteService.itemRaritiesAutoCompletes(), searchTerm));
    }

    @GetMapping("/tooltipside")
    public ResponseEntity tooltipSide() {
        return ResponseEntity.ok(AutoCompleteService.tooltipSideAutoCompletes());
    }

    @GetMapping("/tooltipside/{searchTerm}")
    public ResponseEntity tooltipSide(@PathVariable String searchTerm) {
        return ResponseEntity.ok(contains(AutoCompleteService.tooltipSideAutoCompletes(), searchTerm));
    }

    private static List<String> contains(List<String> set, String searchTerm) {
        return set
            .stream()
            .filter(itemName -> itemName.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }
}