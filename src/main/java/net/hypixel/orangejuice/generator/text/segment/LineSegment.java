package net.hypixel.orangejuice.generator.text.segment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.hypixel.orangejuice.generator.builder.ClassBuilder;
import net.hypixel.orangejuice.util.Util;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public final class LineSegment {

    private final @NotNull List<ColorSegment> segments;

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static @NotNull List<LineSegment> fromLegacy(@NotNull String legacyText, char symbolSubstitute) {
        return Util.safeArrayStream(legacyText.split("(\n|\\\\n)"))
            .map(line -> TextSegment.fromLegacy(line, symbolSubstitute))
            .toList();
    }

    public int length() {
        return this.getSegments()
            .stream()
            .mapToInt(colorSegment -> colorSegment.getText().length())
            .sum();
    }

    public static class Builder implements ClassBuilder<LineSegment> {

        private final List<ColorSegment> segments = new CopyOnWriteArrayList<>();

        public Builder withSegments(@NotNull ColorSegment... segments) {
            return this.withSegments(Arrays.asList(segments));
        }

        public Builder withSegments(@NotNull Iterable<ColorSegment> segments) {
            segments.forEach(this.segments::add);
            return this;
        }

        @Override
        public @NotNull LineSegment build() {
            return new LineSegment(this.segments.stream().toList());
        }
    }
}