package net.hypixel.orangejuice.service.generator;

import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.impl.MinecraftItemGenerator;
import net.hypixel.orangejuice.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import org.jetbrains.annotations.Nullable;

public class ItemService {

    public static GeneratedObject generate(
        String itemId,
        @Nullable String data,
        @Nullable Boolean enchanted,
        @Nullable Boolean hoverEffect,
        @Nullable String skinValue
    ) {
        enchanted = enchanted != null && enchanted;
        hoverEffect = hoverEffect != null && hoverEffect;

        GeneratorImageBuilder item = new GeneratorImageBuilder();

        if (itemId.equalsIgnoreCase("player_head") && skinValue != null) {
            item.addGenerator(new MinecraftPlayerHeadGenerator.Builder()
                .withSkin(skinValue)
                .build());
        } else {
            item.addGenerator(new MinecraftItemGenerator.Builder()
                .withItem(itemId)
                .withData(data)
                .isEnchanted(enchanted)
                .withHoverEffect(hoverEffect)
                .isBigImage()
                .build()
            );
        }

        return item.build();
    }
}
