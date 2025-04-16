package net.hypixel.orangejuice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.hypixel.orangejuice.config.AppConfig;
import net.hypixel.orangejuice.internalapi.Environment;
import net.hypixel.orangejuice.metrics.PrometheusMetrics;
import net.hypixel.orangejuice.util.JsonUtil;
import net.hypixel.orangejuice.util.json.adapter.ColorTypeAdapter;
import net.hypixel.orangejuice.util.json.adapter.InstantTypeAdapter;
import net.hypixel.orangejuice.util.json.adapter.UUIDTypeAdapter;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.UUID;

@Log4j2
@SpringBootApplication
public class NerdBotApiApp {

    @Getter
    private static AppConfig config;

    public static final Gson GSON = new GsonBuilder() // TODO: check if all adapters are needed (i dont understand gson)
        .setPrettyPrinting()
        .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
        .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
        .registerTypeAdapter(Color.class, new ColorTypeAdapter())
        .create();

    public static void main(String[] args) {
        loadConfig();

        System.setProperty("swagger.enabled", String.valueOf(config.isSwaggerEnabled()));

        if (config.getMetricsConfig().isEnabled()) {
            PrometheusMetrics.enableMetrics();
        }

        SpringApplication.run(NerdBotApiApp.class, args);
    }

    private static void loadConfig() {
        String fileName;
        if (System.getProperty("api.config") != null) {
            fileName = System.getProperty("api.config");
            log.info("Found config property: " + fileName);
        } else {
            log.info("Config property not defined, going to default path!");
            fileName = Environment.getEnvironment().name().toLowerCase() + ".config.json";
        }

        try {
            log.info("Loading config file from '" + fileName + "'");
            File file = new File(fileName);
            config = (AppConfig) JsonUtil.jsonToObject(file, AppConfig.class);

            log.info("Loaded config from " + file.getAbsolutePath());
        } catch (FileNotFoundException exception) {
            log.error("Could not find config file " + fileName);
            System.exit(-1);
        }
    }
}