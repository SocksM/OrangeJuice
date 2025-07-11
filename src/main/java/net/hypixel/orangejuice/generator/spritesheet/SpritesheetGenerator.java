package net.hypixel.orangejuice.generator.spritesheet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.hypixel.orangejuice.NerdBotApiApp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SpritesheetGenerator {

    public static final int IMAGE_HEIGHT = 256;
    public static final int IMAGE_WIDTH = 256;
    public static final int LARGE_IMAGE_WIDTH = 300;
    public static final int LARGE_IMAGE_HEIGHT = 300;
    private static final int ATLAS_WIDTH = 1_024 * 8;
    private static final List<TextureInfo> textureInfo = new ArrayList<>();

    public static void main(String[] args) {
        String path = "./src/main/resources/minecraft/textures/items";
        String outputDir = "./src/main/resources/minecraft/spritesheets";
        String atlasName = "minecraft_texture_atlas.png";
        String jsonFileName = "atlas_coordinates.json";

        List<String> textureNames = new ArrayList<>();
        textureInfo.addAll(loadTextures(path, textureNames));

        System.out.println("Loaded " + textureInfo.size() + " textures.");

        BufferedImage atlas = createTextureAtlas();
        saveTextureAtlas(atlas, outputDir, atlasName, jsonFileName);
        System.out.println("Texture atlas generation complete!");
    }

    /**
     * Save the texture atlas to the given output directory
     *
     * @param inputDir     The input directory
     * @param textureNames The names of the textures
     *
     * @return The loaded textures as a list
     */
    private static List<TextureInfo> loadTextures(String inputDir, List<String> textureNames) {
        List<TextureInfo> textureInfoList = new ArrayList<>();
        File folder = new File(inputDir);
        File[] files = folder.listFiles(new PNGFileFilter());

        if (files != null) {
            System.out.println("Loading textures from: " + folder.getAbsolutePath());

            // Sort alphabetically
            Arrays.sort(files, Comparator.comparing(File::getName));

            for (File file : files) {
                try {
                    BufferedImage texture = ImageIO.read(file);
                    System.out.println("Loaded texture: " + file.getName());

                    if (texture.getWidth() != IMAGE_WIDTH || texture.getHeight() != IMAGE_HEIGHT) {
                        System.out.println("Resizing texture: " + file.getName() + " to " + IMAGE_WIDTH + "x" + IMAGE_HEIGHT);
                        texture = resizeImage(texture);
                    }

                    TextureInfo textureInfo = new TextureInfo(file.getName(), texture, -1, -1);
                    textureInfoList.add(textureInfo);
                    textureNames.add(file.getName());
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }

        return textureInfoList;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage) {
        BufferedImage resizedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        graphics2D.drawImage(originalImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        graphics2D.dispose();
        return resizedImage;
    }

    /**
     * Create a texture atlas from the given textures and the s
     *
     * @return The texture atlas image
     */
    private static BufferedImage createTextureAtlas() {
        int atlasHeight = 0;
        int x = 0;
        int y = 0;

        BufferedImage atlas = new BufferedImage(ATLAS_WIDTH, 1, BufferedImage.TYPE_INT_ARGB);

        int totalTextures = textureInfo.size();
        double progressStep = 1.0 / totalTextures;
        double progress = 0.0;

        for (TextureInfo textureInfo : textureInfo) {
            int frameWidth = IMAGE_WIDTH;

            // Move to the next row
            if (x + frameWidth > ATLAS_WIDTH) {
                x = 0;
                y += atlasHeight;
            }

            atlasHeight = IMAGE_HEIGHT;

            for (int yPos = 0; yPos < textureInfo.getImage().getHeight(); yPos += IMAGE_HEIGHT) {
                BufferedImage frame = textureInfo.getImage().getSubimage(0, yPos, frameWidth, Math.min(IMAGE_HEIGHT, textureInfo.getImage().getHeight() - yPos));

                atlas = extendTextureAtlas(atlas, x, y, frame);

                textureInfo.setX(x);
                textureInfo.setY(y);

                x += frameWidth;
            }

            progress += progressStep;
            System.out.print("\r");
            System.out.printf("Texture atlas creation progress: %.2f%%", progress * 100);
        }

        return atlas;
    }

    /**
     * Extends the texture atlas to fit the new texture at the specified position
     *
     * @param atlas   The texture atlas to extend
     * @param x       The x position of the new texture
     * @param y       The y position of the new texture
     * @param texture The new texture to add
     *
     * @return The extended texture atlas with the new texture added
     */
    private static BufferedImage extendTextureAtlas(BufferedImage atlas, int x, int y, BufferedImage texture) {
        int newWidth = Math.max(atlas.getWidth(), x + texture.getWidth());
        int newHeight = Math.max(atlas.getHeight(), y + texture.getHeight());

        BufferedImage newAtlas = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        newAtlas.getGraphics().drawImage(atlas, 0, 0, null);
        newAtlas.getGraphics().drawImage(texture, x, y, null);

        return newAtlas;
    }

    /**
     * Saves the texture atlas to the specified output directory
     *
     * @param atlas        The texture atlas to save
     * @param outputDir    The output directory to save the texture atlas to
     * @param atlasName    The name of the texture atlas file
     * @param jsonFileName The name of the JSON file containing the texture coordinates
     */
    private static void saveTextureAtlas(BufferedImage atlas, String outputDir, String atlasName, String jsonFileName) {
        File outputFolder = new File(outputDir);
        outputFolder.mkdirs();

        File outputFile = new File(outputFolder, atlasName);

        try {
            System.out.println();
            System.out.println("Saving texture atlas to: " + outputFile.getAbsolutePath());
            ImageIO.write(atlas, "png", outputFile);
            System.out.println("Texture atlas saved successfully!");

            File jsonFolder = new File("./src/main/resources/minecraft/json");
            saveTextureCoordinatesJson(jsonFolder, jsonFileName);
        } catch (IOException exception) {
            System.err.println("Failed to save texture atlas!");
            exception.printStackTrace();
        }
    }

    /**
     * Saves the texture coordinates to a JSON file
     *
     * @param outputFolder The output folder to save the JSON file to
     * @param jsonFileName The name of the JSON file
     */
    private static void saveTextureCoordinatesJson(File outputFolder, String jsonFileName) {
        JsonArray jsonArray = new JsonArray();

        for (TextureInfo info : textureInfo) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", info.name.split("\\.")[0]);
            jsonObject.addProperty("x", info.getX());
            jsonObject.addProperty("y", info.getY());
            jsonObject.addProperty("size", IMAGE_WIDTH);
            jsonArray.add(jsonObject);
        }

        File jsonFile = new File(outputFolder, jsonFileName);

        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            NerdBotApiApp.GSON.toJson(jsonArray, fileWriter);
            System.out.println("Texture coordinates JSON file saved successfully!");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * A file filter for PNG files
     */
    private static class PNGFileFilter implements FileFilter {
        @Override
        public boolean accept(File file) {
            return file.isFile() && file.getName().toLowerCase().endsWith(".png");
        }
    }

    /**
     * A class containing information about a texture
     */
    @Getter
    static class TextureInfo {
        private final String name;
        private final BufferedImage image;
        @Setter
        private int x;
        @Setter
        private int y;

        public TextureInfo(String name, BufferedImage image, int x, int y) {
            this.name = name;
            this.image = image;
            this.x = x;
            this.y = y;
        }
    }
}