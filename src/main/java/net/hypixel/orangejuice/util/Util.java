package net.hypixel.orangejuice.util;

import com.google.gson.JsonObject;
import net.hypixel.orangejuice.NerdBotApiApp;
import net.hypixel.orangejuice.util.model.Pair;
import lombok.extern.log4j.Log4j2;
import net.hypixel.orangejuice.metrics.PrometheusMetrics;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Log4j2
public class Util {
    public static final Pattern UUID_REGEX = Pattern.compile("[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[89aAbB][a-f0-9]{3}-[a-f0-9]{12}");
    public static final Pattern TRIMMED_UUID_REGEX = Pattern.compile("[a-f0-9]{12}4[a-f0-9]{3}[89aAbB][a-f0-9]{15}");
    public static final String SECTION_SYMBOL = "§";
    private static final Pattern ADD_UUID_HYPHENS_REGEX = Pattern.compile("([a-f0-9]{8})([a-f0-9]{4})(4[a-f0-9]{3})([89aAbB][a-f0-9]{3})([a-f0-9]{12})");
    private static final String ALL_PATTERN = "[0-9A-FK-OR]";
    public static final Pattern VANILLA_PATTERN = Pattern.compile(SECTION_SYMBOL + "+(" + ALL_PATTERN + ")", Pattern.CASE_INSENSITIVE);

    private Util() {
    }

    public static boolean isValidBase64(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }

        try {
            Base64.getDecoder().decode(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isSHA256(String string) {
        return string.matches("[a-fA-F0-9]{64}");
    }

    public static String convertCamelCaseToSnakeCase(String camelCase) {
        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }

    public static Pair<String, Integer> getLongestLine(List<String> strings) {
        String longest = "";
        int length = 0;

        for (String string : strings) {
            if (string.length() > length) {
                longest = string;
                length = string.length();
            }
        }

        return new Pair<>(longest, length);
    }

    public static Stream<String> safeArrayStream(String[]... arrays) {
        Stream<String> stream = Stream.empty();

        if (arrays != null) {
            for (String[] array : arrays) {
                stream = Stream.concat(stream, (array == null) ? Stream.empty() : Arrays.stream(array));
            }
        }

        return stream;
    }

    public static JsonObject makeHttpRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(String.format(url))).GET().build();
        String requestResponse;

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        requestResponse = response.body();

        return NerdBotApiApp.GSON.fromJson(requestResponse, JsonObject.class);
    }

    @Nullable
    private static String sendRequestWithFallback(String primaryUrl, String fallbackUrl) {
        try {
            return getHttpResponse(primaryUrl).body();
        } catch (IOException | InterruptedException exception) {
            log.error("An error occurred while sending a request to primary URL!", exception);

            try {
                return getHttpResponse(fallbackUrl).body();
            } catch (IOException | InterruptedException fallbackException) {
                log.error("An error occurred while sending a request to fallback URL!", fallbackException);
                return null;
            }
        }
    }

    private static HttpResponse<String> getHttpResponse(String url, Pair<String, String>... headers) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url)).GET();
        Arrays.asList(headers).forEach(pair -> builder.header(pair.getLeft(), pair.getRight()));
        HttpRequest request = builder.build();
        log.info("Sending HTTP request to " + url + " with headers " + Arrays.toString(headers));
        PrometheusMetrics.HTTP_REQUESTS_AMOUNT.labels(request.method(), url).inc();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static boolean isUUID(String input) {
        return (input != null && !input.isEmpty()) && (input.matches(UUID_REGEX.pattern()) || input.matches(TRIMMED_UUID_REGEX.pattern()));
    }

    /**
     * Converts a string representation (with or without dashes) of a UUID to the {@link UUID} class.
     *
     * @param input unique id to convert.
     *
     * @return converted unique id.
     */
    public static UUID toUUID(String input) {
        if (!isUUID(input)) {
            throw new IllegalArgumentException("Not a valid UUID!");
        }

        if (input.contains("-")) {
            return UUID.fromString(input); // Already has hyphens
        }

        return UUID.fromString(input.replaceAll(ADD_UUID_HYPHENS_REGEX.pattern(), "$1-$2-$3-$4-$5"));
    }

    /**
     * Initializes a font.
     *
     * @param path The path to the font in the resources' folder.
     *
     * @return The initialized font.
     */
    @Nullable
    public static Font initFont(String path, float size) {
        Font font;
        try (InputStream fontStream = Util.class.getResourceAsStream(path)) {
            if (fontStream == null) {
                log.error("Couldn't initialize font: " + path);
                return null;
            }
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(size);
        } catch (IOException | FontFormatException exception) {
            log.error("Couldn't initialize font: " + path, exception);
            return null;
        }
        return font;
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}