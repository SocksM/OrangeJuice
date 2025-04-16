package net.hypixel.orangejuice.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import lombok.extern.log4j.Log4j2;

import java.io.FileWriter;
import java.io.IOException;

@Log4j2
public class ConfigGenerator {

    private static final String EXAMPLE_NAME_ID = "example_name";
    private static final String EXAMPLE_NAME = "Example Name";
    private static final String EXAMPLE_ID = "1234567890123456789";

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        AppConfig appConfig = new AppConfig();

        String json = gson.toJson(appConfig);

        if (isValidJson(json)) {
            log.info("The provided JSON string is valid!");

            try {
                writeJsonToFile(json);
            } catch (IOException exception) {
                log.error("Error writing JSON to file", exception);
            }
        } else {
            log.error("The provided JSON string is invalid: \n" + json + "\n");
            System.exit(-1);
        }
    }

    private static boolean isValidJson(String jsonStr) {
        try {
            JsonParser.parseString(jsonStr);
            return true;
        } catch (JsonSyntaxException exception) {
            log.error("Invalid JSON string: " + jsonStr);
            return false;
        }
    }

    private static void writeJsonToFile(String json) throws IOException {
        try (FileWriter writer = new FileWriter("./src/main/resources/example-config.json")) {
            writer.write(json);
            log.info("Created JSON file successfully!");
        }
    }
}
