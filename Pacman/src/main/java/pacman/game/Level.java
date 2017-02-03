package pacman.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.framework.Tile;
import pacman.image_handling.ImageLoader;
import pacman.objects.Player;

public class Level {
    private Tile[][] tiles;
    private BufferedImage levelImage;
    
    public Level(int width, int height) { //width ja height Tile koossa (ei pikseleissä)
        tiles = new Tile[height][width];
    }
    
    public void loadLevelImage(String path) {
        ImageLoader loader = new ImageLoader();
        levelImage = loader.loadImage(path);
    }
    
    public BufferedImage getLevelImage() {
        return this.levelImage;
    }
    
    public Tile[][] getTiles() {
        return this.tiles;
    }
    
    public void makeLevelFromImage(Pacman game) {
        int w = levelImage.getWidth();
        int h = levelImage.getHeight();

        
        for (int yy = 0; yy < h; yy++) {
            for (int xx = 0; xx < w; xx++) { //käydään läpi rivi kerrallaan
                int pixel = levelImage.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                //KAIKKI SEINÄT
                if (red == 0 && green == 0 && blue == 255) { //pystysuora
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    
                } else if (red == 0 && green == 0 && blue == 200) { //vaakasuora
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    
                } else if (red == 0 && green == 0 && blue == 120) { //kulmakohta
                    //TÄSSÄ PÄÄTTELE MITEN PÄIN KULMA TULEE
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    
                } else if (red == 0 && green == 0 && blue == 70) { //reuna
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    
                } else if (red == 0 && green == 0 && blue == 50) { //reunan kulmat
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                }
                
                
                //SYÖTÄVÄT
                if (red == 130 && green == 130 && blue == 130) { //pieni pallo
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsFood(true);
                    
                } else if (red == 255 && green == 255 && blue == 255) { //iso pallo
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsEnergizer(true);
                }
                

                //PLAYER
                if (red == 255 && green == 255 && blue == 0) {
                    game.setPlayer(new Player(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, yy * Pacman.TILE_HEIGHT)); //luodaan pelaaja
                }
            }
        }
    }
    
    public void render(Graphics g) {
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile != null) {
                    tile.render(g);
                }
            }
        }
    }
}
