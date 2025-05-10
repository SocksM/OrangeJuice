package net.hypixel.orangejuice.generator.item.overlay;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HoverEffect {

    public static BufferedImage applyHoverEffect(BufferedImage image) {
        BufferedImage hoveredItem = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D hoveredItemGraphics = hoveredItem.createGraphics();
        hoveredItemGraphics.drawImage(image, 0, 0, null);

        hoveredItemGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
        hoveredItemGraphics.setColor(Color.WHITE);
        hoveredItemGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());

        hoveredItemGraphics.dispose();

        return hoveredItem;
    }
}
