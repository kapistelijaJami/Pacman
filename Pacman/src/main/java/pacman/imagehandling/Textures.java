package pacman.imagehandling;

import java.awt.image.BufferedImage;

public class Textures {
    private BufferedImage[] player;
    private BufferedImage[] ghosts;
    private ImageLoader imageLoader;
    
    /**
     * Konstruktori luo tarvittavat muuttujat.
     */
    public Textures() {
        imageLoader = new ImageLoader();
        player = new BufferedImage[20];
        ghosts = new BufferedImage[11];
    }
    
    /**
     * Metodi palauttaa pelaajan indeksiä vastaavan kuvan.
     * @param index Indeksi
     * @return Kuva, joka palautetaan
     */
    public BufferedImage getPlayerImage(int index) {
        return player[index];
    }
    
    /**
     * Metodi palauttaa haamun indeksiä vastaavan kuvan.
     * @param index Indeksi
     * @return Kuva, joka palautetaan
     */
    public BufferedImage getGhostImage(int index) {
        return ghosts[index];
    }
    
    /**
     * Metodi lataa pelaajan kuvat ja asettaa ne taulukkoon.
     */
    public void getPlayerImages() {
        //OIKEALLE
        player[0] = imageLoader.loadImage("/pacmanTextures/pacmanKiinni.png");
        player[1] = imageLoader.loadImage("/pacmanTextures/pacmanMelkeinKiinni.png");
        player[2] = imageLoader.loadImage("/pacmanTextures/pacmanPuolivali.png");
        player[3] = imageLoader.loadImage("/pacmanTextures/pacmanMelkeinAuki.png");
        player[4] = imageLoader.loadImage("/pacmanTextures/pacmanAuki.png");
        
        //ALAS
        for (int i = 0; i < 5; i++) {
            player[i + 5] = ImageHandler.rotateImage(player[i], 90);
        }
        
        //VASEMMALLE
        for (int i = 0; i < 5; i++) {
            player[i + 10] = ImageHandler.flipImageHorizontally(player[i]);
        }
        
        //YLÖS
        for (int i = 0; i < 5; i++) {
            player[i + 15] = ImageHandler.rotateImage(player[i], -90);
        }
    }
    
    /**
     * Metodi lataa haamun kuvat ja asettaa ne taulukkoon.
     */
    public void getGhostImages() {
        ghosts[0] = imageLoader.loadImage("/ghostTextures/blinkyEka.png");
        ghosts[1] = imageLoader.loadImage("/ghostTextures/blinkyToka.png");
        
        ghosts[2] = imageLoader.loadImage("/ghostTextures/pinkyEka.png");
        ghosts[3] = imageLoader.loadImage("/ghostTextures/pinkyToka.png");
        
        ghosts[4] = imageLoader.loadImage("/ghostTextures/inkyEka.png");
        ghosts[5] = imageLoader.loadImage("/ghostTextures/inkyToka.png");
        
        ghosts[6] = imageLoader.loadImage("/ghostTextures/clydeEka.png");
        ghosts[7] = imageLoader.loadImage("/ghostTextures/clydeToka.png");
        
        ghosts[8] = imageLoader.loadImage("/ghostTextures/frightenedEka.png");
        ghosts[9] = imageLoader.loadImage("/ghostTextures/frightenedToka.png");
        
        ghosts[10] = imageLoader.loadImage("/ghostTextures/silmat.png");
    }
}
