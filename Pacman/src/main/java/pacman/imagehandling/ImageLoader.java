package pacman.imagehandling;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Luokkaa käytetään kuvien lataamiseen tiedostopolun avulla.
 */
public class ImageLoader {
    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
        }
        
        return null;
    }
}
