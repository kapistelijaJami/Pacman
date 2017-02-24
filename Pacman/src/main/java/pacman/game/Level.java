package pacman.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.framework.Direction;
import pacman.framework.Tile;
import pacman.framework.WallLineDirection;
import pacman.imagehandling.ImageLoader;
import pacman.objects.Ghost;
import pacman.objects.Player;

/**
 * Luokka tarjoaa metodit kentän luomiseen kuvan avulla
 * ja luo sen perusteella Tile-oliot, jotka piirretään render -metodissa.
 */
public class Level {
    private Tile[][] tiles;
    private BufferedImage levelImage;
    private Tile getOutTile;
    
    /**
     * Konstruktori alustaa tiles taulukon.
     * @param width Taulukon leveys (Tile koossa, eli kuinka monta tilea, ei pikseleissä)
     * @param height Taulukon korkeus (Tile koossa, eli kuinka monta tilea, ei pikseleissä)
     */
    public Level(int width, int height) { //width ja height Tile koossa (ei pikseleissä)
        tiles = new Tile[height][width];
    }
    
    /**
     * Metodi lataa kentän käyttäen ImageLoaderia.
     * @param path kuvan sijainti
     */
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
    
    public Tile getGetOutTile() {
        return getOutTile;
    }
    
    /**
     * Metodi luo kentän kuvan avulla ja luo sen perusteella Tile-oliot, sekä peliobjektit.
     * @param game Pacman peli -olio
     */
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
                    tiles[yy][xx].setLineDirection(WallLineDirection.VERTICAL);
                    
                } else if (red == 0 && green == 0 && blue == 200) { //vaakasuora
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    tiles[yy][xx].setLineDirection(WallLineDirection.HORIZONTAL);
                    
                } else if (red == 0 && green == 0 && blue == 120) { //kulmakohta
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    tiles[yy][xx].setIsCornerTile(true);
                    
                } else if (red == 0 && green == 255 && blue == 0) { //haamujen luukku
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT);
                    tiles[yy][xx].setIsWall(true);
                    tiles[yy][xx].setIsGhostHatch(true);
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
                
                //GHOSTS
                if (red == 255 && green == 0 && blue == 0) { //Blinky
                    Ghost ghost = new Ghost(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, yy * Pacman.TILE_HEIGHT);
                    game.setGhost(0, ghost);
                    ghost.setColor(Color.red);
                    ghost.setInsideCage(false);
                    
                    //tehdään tästä myös getOutTile
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT); //tehdään tyhjä tile
                    this.getOutTile = tiles[yy][xx];
                    
                } else if (red == 255 && green == 130 && blue == 255) { //Pinky
                    Ghost ghost = new Ghost(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, yy * Pacman.TILE_HEIGHT);
                    game.setGhost(1, ghost);
                    ghost.setColor(Color.pink);
                    
                } else if (red == 0 && green == 255 && blue == 255) { //Inky
                    Ghost ghost = new Ghost(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, yy * Pacman.TILE_HEIGHT);
                    game.setGhost(2, ghost);
                    ghost.setColor(Color.cyan);
                    
                } else if (red == 255 && green == 130 && blue == 0) { //Clyde
                    Ghost ghost = new Ghost(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, yy * Pacman.TILE_HEIGHT);
                    game.setGhost(3, ghost);
                    ghost.setColor(Color.orange);
                }
                
                //JOS EI OLLUT MIKÄÄN ERIKOINEN TILE, TAI LUOTIIN JOTAIN MUUTA, KUTEN PLAYER, NIIN TEHDÄÄN TYHJÄ TILE TÄSSÄ
                if (tiles[yy][xx] == null) {
                    tiles[yy][xx] = new Tile(xx * Pacman.TILE_WIDTH, yy * Pacman.TILE_HEIGHT); //tehdään tyhjä tile
                }
            }
        }
        
        cornerTileLineDirection(); //TEHDÄÄN KULMAPALOILLE SUUNNAT
    }
    
    /**
     * Metodi päättelee kulmapaloille viivojen suunnat ympärillä olevien seinien perusteella.
     */
    public void cornerTileLineDirection() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) { //käydään kaikki palat läpi
                Tile tile = tiles[y][x];
                if (tile == null) { //ei ollut kulmapala
                    continue;
                }
                if (!tile.isCornerTile()) { //ei ollut kulmapala
                    continue;
                }
                
                Tile[] tilesAround = new Tile[4]; //up, left, down, right

                if (y - 1 >= 0) {
                    tilesAround[0] = tiles[y - 1][x]; //yläpuolella oleva pala
                }
                if (x - 1 >= 0) {
                    tilesAround[1] = tiles[y][x - 1]; //vasemmalla oleva pala
                }
                if (y + 1 < tiles.length) {
                    tilesAround[2] = tiles[y + 1][x]; //alapuolella oleva pala
                }
                if (x + 1 < tiles[y].length) {
                    tilesAround[3] = tiles[y][x + 1]; //oikealla oleva pala
                }

                Direction[] directions = new Direction[2];
                int howManyDirections = 0;

                for (int i = 0; i < tilesAround.length && howManyDirections < 2; i++) { //käydään läpi kaikki ympärillä olevat palat
                    if (tilesAround[i] == null) { //ei ole palaa tässä kohtaa
                        continue;
                    }
                    
                    if (i % 2 == 0 && tilesAround[i].getLineDirection() == WallLineDirection.VERTICAL) { 
                        if (i == 0) {
                            directions[howManyDirections] = Direction.UP;
                        } else {
                            directions[howManyDirections] = Direction.DOWN;
                        }
                        howManyDirections++;
                    } else if (i % 2 == 1 && tilesAround[i].getLineDirection() == WallLineDirection.HORIZONTAL) {
                        if (i == 1) {
                            directions[howManyDirections] = Direction.LEFT;
                        } else {
                            directions[howManyDirections] = Direction.RIGHT;
                        }
                        howManyDirections++;
                    }
                }

                if (howManyDirections < 2) { //ei ole kahta viivaa vieressä, yhdistetään viereiseen kulmapalaan
                    for (int i = 0; i < tilesAround.length && howManyDirections < 2; i++) {
                        if (tilesAround[i] == null) {
                            continue;
                        }
                        if (tilesAround[i].isCornerTile()) {
                            if (i == 0) {
                                directions[howManyDirections] = Direction.UP;
                            } else if (i == 1) {
                                directions[howManyDirections] = Direction.LEFT;
                            } else if (i == 2) {
                                directions[howManyDirections] = Direction.DOWN;
                            } else if (i == 3) {
                                directions[howManyDirections] = Direction.RIGHT;
                            }
                            howManyDirections++;
                        }
                    }
                }

                if (howManyDirections < 2) { //ei tullut selkeää tulosta
                    continue;
                }

                tile.setLineDirection(WallLineDirection.makeCorner(directions[0], directions[1]));
            }
        }
    }
    
    /**
     * Metodi kutsuu kaikkien Tile-olioiden render metodia.
     * @param g piirtämiseen tarvittava olio
     */
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