package net.hypixel.orangejuice.generator;

import net.hypixel.orangejuice.generator.item.GeneratedObject;

public interface Generator {

    /**
     * Generate an item
     *
     * @return the {@link GeneratedObject generated item}
     */
    GeneratedObject generate();
}
