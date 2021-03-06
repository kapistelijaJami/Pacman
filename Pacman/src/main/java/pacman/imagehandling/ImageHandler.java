package pacman.imagehandling;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageHandler {
    
    /**
     * Metodi kääntää kuvan vaakasuunnassa (peilikuva).
     * @param image Kuva, jota halutaan muokata
     * @return Kuva, joka on käännetty
     */
    public static BufferedImage flipImageHorizontally(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }
    
    /**
     * Metodi pyörittää kuvaa.
     * @param image Kuva, jota pyöritetään
     * @param degrees Kuinka monta astetta pyöritetään
     * @return Kuva, jota on pyöritetty
     */
    public static BufferedImage rotateImage(BufferedImage image, int degrees) {
        double rotationRequired = Math.toRadians(degrees);
        double locationX = image.getWidth() / 2 + 1;
        double locationY = image.getHeight() / 2 + 1;
        
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        
        return op.filter(image, null);
    }
}
