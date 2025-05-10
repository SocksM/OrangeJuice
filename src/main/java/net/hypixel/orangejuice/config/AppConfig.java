package net.hypixel.orangejuice.config;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AppConfig {

    /**
     * Configuration for metrics that the bot will be grabbing
     */
    private final MetricsConfig metricsConfig = new MetricsConfig();

    /**
     * The length of time in milliseconds for an image to be generated before timing out
     */
    private final long imageGeneratorTimeoutMs = 3_000;

    /**
     * Whether Swagger UI is enabled
     */
    private final boolean swaggerEnabled = true;
}