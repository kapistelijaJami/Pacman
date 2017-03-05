package pacman.imagehandling;

import java.awt.image.BufferedImage;

public class Animation {

    private int speed;

    private int updatesFromLastFrame;
    private int currentFrame;

    private BufferedImage[] images;

    /**
     * Konstruktori luo Animation olion ja alustaa sen muuttujat.
     * @param speed Animaation toistonopeus
     * @param images Kuvat, joista animaatio koostuu
     */
    public Animation(int speed, BufferedImage... images) {
        this.updatesFromLastFrame = 0;
        this.currentFrame = 0;
        this.speed = speed;
        this.images = new BufferedImage[images.length];

        for (int i = 0; i < images.length; i++) {
            this.images[i] = images[i];
        }
    }

    /**
     * Metodi vaihtaa animaation kuvaa tarpeen mukaan, nopeudesta riippuen.
     */
    public void runAnimation() {
        updatesFromLastFrame++;
        if (updatesFromLastFrame > speed) {
            updatesFromLastFrame = 0;
            nextFrame();
        }
    }

    /**
     * Metodi vaihtaa animaation kuvaa.
     */
    public void nextFrame() {
        currentFrame++;

        if (currentFrame >= images.length) {
            currentFrame = 0;
        }
    }
    
    /**
     * Metodi palauttaa animaation tämänhetkisen kuvan.
     * @return Tämänhetkinen kuva
     */
    public BufferedImage getCurrentImage() {
        return images[currentFrame];
    }
    
    /**
     * Metodi nollaa animaation.
     */
    public void reset() {
        this.updatesFromLastFrame = 0;
        this.currentFrame = 0;
    }
}
