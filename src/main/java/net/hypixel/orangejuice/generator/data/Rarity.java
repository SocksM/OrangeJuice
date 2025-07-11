package net.hypixel.orangejuice.generator.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.generator.text.ChatFormat;
import net.hypixel.orangejuice.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Rarity {

    private static final List<Rarity> RARITIES = new ArrayList<>();

    static {
        try {
            RARITIES.addAll(JsonLoader.loadFromJson(Rarity[].class, Objects.requireNonNull(Rarity.class.getClassLoader().getResource("data/rarities.json"))));
        } catch (Exception e) {
            log.error("Failed to load rarity data", e);
        }
    }

    private final String name;
    private final String display;
    private final ChatFormat color;

    public static Rarity byName(String name) {
        return RARITIES.stream()
            .filter(rarity -> rarity.getDisplay().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    public static List<String> getRarityNames() {
        return RARITIES.stream().map(Rarity::getName).collect(Collectors.toList());
    }

    public String getColorCode() {
        return String.valueOf(ChatFormat.AMPERSAND_SYMBOL) + color.getCode();
    }

    public String getFormattedDisplay() {
        return getColorCode() + ChatFormat.AMPERSAND_SYMBOL + ChatFormat.BOLD.getCode() + display;
    }
}
