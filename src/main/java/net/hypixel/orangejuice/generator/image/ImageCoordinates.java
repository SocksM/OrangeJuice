package net.hypixel.orangejuice.generator.image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.hypixel.orangejuice.generator.impl.MinecraftInventoryGenerator;

/**
 * Represents the coordinates of an image in a {@link MinecraftInventoryGenerator}.
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ImageCoordinates {

    private final int x;
    private final int y;

}
