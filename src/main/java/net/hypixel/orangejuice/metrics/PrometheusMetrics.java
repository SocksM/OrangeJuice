package net.hypixel.orangejuice.metrics;

import net.hypixel.orangejuice.NerdBotApiApp;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PrometheusMetrics {
    public static final Counter HTTP_REQUESTS_AMOUNT = Counter.build()
        .name("http_requests_total")
        .help("Total number of HTTP requests")
        .labelNames("request_type", "url")
        .register();
    public static final Summary HTTP_REQUEST_LATENCY = Summary.build()
        .name("http_requests_latency_seconds")
        .help("Request latency in seconds")
        .labelNames("url")
        .register();

    private static final CollectorRegistry collectorRegistry = new CollectorRegistry();
    private static HTTPServer server;

    private PrometheusMetrics() {
    }

    public static void enableMetrics() {
            log.info("Starting Prometheus metrics server...");

            try {
                if (server != null) {
                    server.close();
                }

                server = new HTTPServer.Builder()
                    .withPort(NerdBotApiApp.getConfig().getMetricsConfig().getPort())
                    .build();

                DefaultExports.initialize();
            } catch (Exception exception) {
                log.error("Failed to start Prometheus metrics server!", exception);
                return;
            }

            collectorRegistry.register(HTTP_REQUESTS_AMOUNT);
            collectorRegistry.register(HTTP_REQUEST_LATENCY);

            log.info("Enabled Prometheus metrics!");
    }
}
