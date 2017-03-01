package pacman.imagehandling;

import java.awt.image.BufferedImage;

public class Animation {

    private int speed;

    private int updatesFromLastFrame;
    private int currentFrame;

    private BufferedImage[] images;

    public Animation(int speed, BufferedImage... images) {
        this.updatesFromLastFrame = 0;
        this.currentFrame = 0;
        this.speed = speed;
        this.images = new BufferedImage[images.length];

        for (int i = 0; i < images.length; i++) {
            this.images[i] = images[i];
        }
    }

    public void runAnimation() {
        updatesFromLastFrame++;
        if (updatesFromLastFrame > speed) {
            updatesFromLastFrame = 0;
            nextFrame();
        }
    }

    public void nextFrame() {
        currentFrame++;

        if (currentFrame >= images.length) {
            currentFrame = 0;
        }
    }
    
    public BufferedImage getCurrentImage() {
        return images[currentFrame];
    }
    
    public void reset() {
        this.updatesFromLastFrame = 0;
        this.currentFrame = 0;
    }
}
