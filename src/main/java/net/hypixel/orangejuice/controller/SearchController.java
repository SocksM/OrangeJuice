package net.hypixel.orangejuice.controller;

import net.hypixel.orangejuice.service.AutoComplete;
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
        return ResponseEntity.ok(AutoComplete.itemNamesAutoCompletes());
    }

    @GetMapping("/itemid/{searchTerm}")
    public ResponseEntity itemIds(@PathVariable String searchTerm) {
        return ResponseEntity.ok(contains(AutoComplete.itemNamesAutoCompletes(), searchTerm));
    }

    @GetMapping("/rarity")
    public ResponseEntity itemRarities() {
        return ResponseEntity.ok(AutoComplete.itemRaritiesAutoCompletes());
    }

    @GetMapping("/rarity/{searchTerm}")
    public ResponseEntity itemRarities(@PathVariable String searchTerm) {
        return ResponseEntity.ok(contains(AutoComplete.itemRaritiesAutoCompletes(), searchTerm));
    }

    @GetMapping("/tooltipside")
    public ResponseEntity tooltipSide() {
        return ResponseEntity.ok(AutoComplete.tooltipSideAutoCompletes());
    }

    @GetMapping("/tooltipside/{searchTerm}")
    public ResponseEntity tooltipSide(@PathVariable String searchTerm) {
        return ResponseEntity.ok(contains(AutoComplete.tooltipSideAutoCompletes(), searchTerm));
    }

    private static List<String> contains(List<String> set, String searchTerm) {
        return set
            .stream()
            .filter(itemName -> itemName.toLowerCase().contains(searchTerm.toLowerCase()))
            .toList();
    }
}