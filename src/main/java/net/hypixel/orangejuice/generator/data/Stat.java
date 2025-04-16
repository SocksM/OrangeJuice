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

@Log4j2
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Stat {

    private static final List<Stat> STATS = new ArrayList<>();

    static {
        try {
            STATS.addAll(JsonLoader.loadFromJson(Stat[].class, Objects.requireNonNull(Stat.class.getClassLoader().getResource("data/stats.json"))));
        } catch (Exception e) {
            log.error("Failed to load stat data", e);
        }
    }

    private String icon;
    private String name;
    private String stat;
    private String display;
    private ChatFormat color;
    private ChatFormat subColor;
    private String parseType;

    public static Stat byName(String name) {
        return STATS.stream()
            .filter(stat -> stat.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    /**
     * In some cases, stats can have multiple colors.
     * One for the number and another for the stat
     *
     * @return Secondary {@link ChatFormat} of the stat
     */
    public ChatFormat getSecondaryColor() {
        if (subColor != null) {
            return subColor;
        } else {
            return color;
        }
    }
}