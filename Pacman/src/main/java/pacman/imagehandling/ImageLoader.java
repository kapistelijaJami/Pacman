package pacman.imagehandling;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Luokkaa käytetään kuvien lataamiseen tiedostopolun avulla.
 */
public class ImageLoader {
    
    /**
     * Metodi lataa kuvan tiedostopolun avulla.
     * @param path Tiedostopolku
     * @return Kuvatiedosto
     */
    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(getClass().getResource(path));
        } catch (Exception e) {
        }
        
        return null;
    }
}
