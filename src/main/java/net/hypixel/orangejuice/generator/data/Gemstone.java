package net.hypixel.orangejuice.generator.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.util.JsonLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Gemstone {

    private static final List<Gemstone> GEMSTONES = new ArrayList<>();

    static {
        try {
            GEMSTONES.addAll(JsonLoader.loadFromJson(Gemstone[].class, Objects.requireNonNull(Gemstone.class.getClassLoader().getResource("data/gemstones.json"))));
        } catch (Exception e) {
            log.error("Failed to load gemstone data", e);
        }
    }

    private String name;
    private String icon;
    private String formattedIcon;
    @JsonIgnore
    private Color color;
    private Map<String, String> formattedTiers;

    public static List<Gemstone> getGemstones() {
        return GEMSTONES;
    }

    public static Gemstone byName(String name) {
        return GEMSTONES.stream()
            .filter(gemstone -> gemstone.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }
}
