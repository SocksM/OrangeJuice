package net.hypixel.orangejuice.generator.spritesheet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.hypixel.orangejuice.NerdBotApiApp;
import net.hypixel.orangejuice.generator.item.overlay.ItemOverlay;
import net.hypixel.orangejuice.generator.item.overlay.OverlayColorOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class OverlaySheet {

    private static final Map<String, ItemOverlay> itemOverlays = new HashMap<>();

    private static BufferedImage smallEnchantGlint;
    private static BufferedImage largeEnchantGlint;

    static {
        try (InputStream overlayStream = OverlaySheet.class.getResourceAsStream("/minecraft/spritesheets/overlays.png")) {
            if (overlayStream == null) {
                throw new IOException("Overlay sprite sheet image not found: /minecraft/spritesheets/overlays.png");
            }
            // Load the overlay sprite sheet image
            BufferedImage overlaySpriteSheet = ImageIO.read(overlayStream);

            // loading color choices for overlays into memory
            try (InputStream colorOptionsStream = OverlaySheet.class.getResourceAsStream("/minecraft/json/overlay_colors.json")) {
                if (colorOptionsStream == null) {
                    throw new IOException("Overlay colors JSON file not found: /minecraft/json/overlay_colors.json");
                }
                OverlayColorOptions[] overlayColorOptionsOptions = NerdBotApiApp.GSON.fromJson(new InputStreamReader(colorOptionsStream), OverlayColorOptions[].class);
                HashMap<String, OverlayColorOptions> foundOverlayColors = new HashMap<>();
                for (OverlayColorOptions overlayColorOptions : overlayColorOptionsOptions) {
                    foundOverlayColors.put(overlayColorOptions.getName(), overlayColorOptions);
                }

                // loading overlays and assigning their color choices and images
                try (InputStream overlayCoordinatesStream = OverlaySheet.class.getResourceAsStream("/minecraft/json/overlay_coordinates.json")) {
                    if (overlayCoordinatesStream == null) {
                        throw new IOException("Overlay coordinates JSON file not found: /minecraft/json/overlay_coordinates.json");
                    }
                    ItemOverlay[] itemOverlayCoordinates = NerdBotApiApp.GSON.fromJson(new InputStreamReader(overlayCoordinatesStream), ItemOverlay[].class);
                    HashMap<String, ItemOverlay> foundOverlays = new HashMap<>();
                    for (ItemOverlay itemOverlay : itemOverlayCoordinates) {
                        int imageDimensions = itemOverlay.isBig() ? SpritesheetGenerator.LARGE_IMAGE_WIDTH : 16;
                        itemOverlay.setImage(overlaySpriteSheet.getSubimage(itemOverlay.getX(), itemOverlay.getY(), imageDimensions, imageDimensions));
                        if (itemOverlay.getName().contains("enchant")) {
                            if (itemOverlay.getName().contains("large")) {
                                largeEnchantGlint = itemOverlay.getImage();
                            } else if (itemOverlay.getName().contains("small")) {
                                smallEnchantGlint = itemOverlay.getImage();
                            }
                            continue;
                        }

                        itemOverlay.setOverlayColorOptions(foundOverlayColors.get(itemOverlay.getColorOptions()));
                        foundOverlays.put(itemOverlay.getName(), itemOverlay);
                    }

                    // assigning the items to their overlay objects
                    try (InputStream itemOverlayBindingStream = OverlaySheet.class.getResourceAsStream("/minecraft/json/item_overlay_binding.json")) {
                        if (itemOverlayBindingStream == null) {
                            throw new IOException("Item overlay binding JSON file not found: /minecraft/json/item_overlay_binding.json");
                        }
                        JsonArray jsonBindings = NerdBotApiApp.GSON.fromJson(new InputStreamReader(itemOverlayBindingStream), JsonArray.class);
                        for (JsonElement jsonElement : jsonBindings) {
                            JsonObject itemData = jsonElement.getAsJsonObject();
                            String itemName = itemData.get("name").getAsString();
                            String overlays = itemData.get("overlays").getAsString();

                            itemOverlays.put(itemName, foundOverlays.get(overlays));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ItemOverlay getOverlay(String overlayId) {
        return itemOverlays.get(overlayId);
    }

    public static BufferedImage getEnchantGlint(boolean isSmall) {
        return isSmall ? smallEnchantGlint : largeEnchantGlint;
    }

}
