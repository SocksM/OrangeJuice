package net.hypixel.orangejuice.generator.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.hypixel.orangejuice.generator.Generator;
import net.hypixel.orangejuice.generator.builder.ClassBuilder;
import net.hypixel.orangejuice.generator.exception.GeneratorException;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.generator.item.overlay.EnchantmentGlint;
import net.hypixel.orangejuice.generator.item.overlay.HoverEffect;
import net.hypixel.orangejuice.generator.item.overlay.ItemOverlay;
import net.hypixel.orangejuice.generator.item.overlay.OverlayType;
import net.hypixel.orangejuice.generator.spritesheet.OverlaySheet;
import net.hypixel.orangejuice.generator.spritesheet.Spritesheet;
import net.hypixel.orangejuice.util.ImageUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftItemGenerator implements Generator {

    private final String itemId;
    private final String data;
    private final boolean enchanted;
    private final boolean hoverEffect;
    private final boolean bigImage;

    private BufferedImage itemImage;

    @Override
    public GeneratedObject generate() {
        itemImage = Spritesheet.getTexture(itemId.toLowerCase());

        if (itemImage == null) {
            throw new GeneratorException("Item with ID `%s` not found", itemId);
        }

        ItemOverlay itemOverlays = OverlaySheet.getOverlay(itemId.toLowerCase());
        if (itemOverlays != null) {
            itemImage = applyOverlay(itemOverlays);
        }

        if (bigImage && itemImage.getHeight() <= 16 && itemImage.getWidth() <= 16) {
            itemImage = ImageUtil.upscaleImage(itemImage, 10);
        }

        if (enchanted) {
            itemImage = EnchantmentGlint.applyEnchantGlint(itemImage);
        }

        if (hoverEffect) {
            itemImage = HoverEffect.applyHoverEffect(itemImage);
        }

        return new GeneratedObject(itemImage);
    }

    private BufferedImage applyOverlay(ItemOverlay overlay) {
        BufferedImage overlaidItem = new BufferedImage(itemImage.getWidth(), itemImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D overlaidItemGraphics = overlaidItem.createGraphics();
        overlaidItemGraphics.drawImage(itemImage, 0, 0, null);

        String options = (data != null ? data : "");
        switch (overlay.getType()) {
            case NORMAL -> {
                int[] overlayColors = overlay.getOverlayColorOptions().getColorsFromOption(options);
                if (overlayColors != null) {
                    OverlayType.normalOverlay(overlaidItem, overlay.getImage(), overlayColors[0]);
                }
            }
            case MAPPED -> {
                int[] overlayColors = overlay.getOverlayColorOptions().getColorsFromOption(options);
                if (overlayColors != null) {
                    OverlayType.mappedOverlay(overlaidItem, overlay.getImage(), overlay.getOverlayColorOptions().getMap(), overlayColors);
                }
            }
            case DUAL_LAYER -> {
                int[] overlayColors = overlay.getOverlayColorOptions().getColorsFromOption(options);
                if (overlayColors != null) {
                    OverlayType.normalOverlay(overlaidItem, overlaidItem, overlayColors[1]);
                    OverlayType.normalOverlay(overlaidItem, overlay.getImage(), overlayColors[0]);
                }
            }
        }

        return overlaidItem;
    }

    public static class Builder implements ClassBuilder<MinecraftItemGenerator> {
        private String itemId;
        private String data;
        private boolean enchanted;
        private boolean hoverEffect;
        private boolean bigImage;

        public Builder withItem(String itemId) {
            this.itemId = itemId
                .replace("minecraft:", "")
                .replace("skull", "player_head");
            return this;
        }

        public Builder withData(String data) {
            this.data = data;
            return this;
        }

        public Builder isEnchanted(boolean enchanted) {
            this.enchanted = enchanted;
            return this;
        }

        public Builder withHoverEffect(boolean hoverEffect) {
            this.hoverEffect = hoverEffect;
            return this;
        }

        public Builder isBigImage(boolean bigImage) {
            this.bigImage = bigImage;
            return this;
        }

        public Builder isBigImage() {
            return isBigImage(true);
        }

        @Override
        public MinecraftItemGenerator build() {
            return new MinecraftItemGenerator(itemId, data, enchanted, hoverEffect, bigImage);
        }
    }
}
