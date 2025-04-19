package net.hypixel.orangejuice.service.generator;

import net.hypixel.orangejuice.generator.image.GeneratorImageBuilder;
import net.hypixel.orangejuice.generator.impl.MinecraftPlayerHeadGenerator;
import net.hypixel.orangejuice.generator.item.GeneratedObject;
import net.hypixel.orangejuice.requestmodel.generator.HeadGeneratorRequest;

public class HeadService {

    public static GeneratedObject generate(String skinValue) {
        if (skinValue == null) {
            skinValue = DEFAULT_SKIN_VALUE;
        }

        return new GeneratorImageBuilder()
            .addGenerator(new MinecraftPlayerHeadGenerator.Builder().withSkin(skinValue).build())
            .build();
    }
}