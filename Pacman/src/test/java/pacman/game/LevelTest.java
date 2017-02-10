package pacman.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Tile;
import pacman.framework.WallLineDirection;
import pacman.objects.Ghost;
import pacman.objects.Player;

public class LevelTest {

    public LevelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void constructorTest() {
        Level level = new Level(28, 31);

        Tile[][] tiles = level.getTiles();

        assertEquals(31, tiles.length);
        for (Tile[] row : tiles) {
            assertEquals(28, row.length);
        }
    }

    @Test
    public void loadLevelImageTest() {
        Level level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png");
        BufferedImage image = level.getLevelImage();

        assertTrue(image != null);
    }

    @Test
    public void makeLevelFromImageTest() {
        Level level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png");
        Pacman game = new Pacman();
        level.makeLevelFromImage(game);

        Tile[][] tiles = level.getTiles();

        boolean isNull = false;
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile == null) {
                    isNull = true;
                }
            }
        }

        assertFalse(isNull);
    }

    @Test
    public void makeLevelFromImageTestAttributes() {
        Level level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png");
        Pacman game = new Pacman();
        level.makeLevelFromImage(game);

        Tile[][] tiles = level.getTiles();

        BufferedImage levelImage = level.getLevelImage();

        int w = levelImage.getWidth();
        int h = levelImage.getHeight();

        for (int yy = 0; yy < h; yy++) {
            for (int xx = 0; xx < w; xx++) { //käydään läpi rivi kerrallaan
                int pixel = levelImage.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                
                assertEquals(xx * Pacman.TILE_WIDTH, tiles[yy][xx].getX());
                assertEquals(yy * Pacman.TILE_WIDTH, tiles[yy][xx].getY());
                
                if (red == 0 && green == 0 && blue == 255) { //pystysuora
                    assertTrue(tiles[yy][xx].isWall());
                    assertEquals(WallLineDirection.VERTICAL, tiles[yy][xx].getLineDirection());
                } else if (red == 0 && green == 0 && blue == 200) { //vaakasuora
                    assertTrue(tiles[yy][xx].isWall());
                    assertEquals(WallLineDirection.HORIZONTAL, tiles[yy][xx].getLineDirection());
                } else if (red == 0 && green == 0 && blue == 120) { //kulmakohta
                    assertTrue(tiles[yy][xx].isWall());
                    assertTrue(tiles[yy][xx].isCornerTile());
                    assertFalse(tiles[yy][xx].getLineDirection() == null);
                } else if (red == 0 && green == 255 && blue == 0) { //haamujen luukku
                    assertTrue(tiles[yy][xx].isWall());
                    assertTrue(tiles[yy][xx].isGhostHatch());
                }
                
                if (red == 130 && green == 130 && blue == 130) { //pieni pallo
                    assertFalse(tiles[yy][xx].isWall());
                    assertTrue(tiles[yy][xx].isFood());
                } else if (red == 255 && green == 255 && blue == 255) { //iso pallo
                    assertFalse(tiles[yy][xx].isWall());
                    assertTrue(tiles[yy][xx].isEnergizer());
                } else if (red == 255 && green == 255 && blue == 0) { //player
                    Player p = game.getPlayer();
                    assertEquals(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, p.getX());
                    assertEquals(yy * Pacman.TILE_HEIGHT, p.getY());
                }
                
                GhostHandler gh = game.getGhostHandler();
                Ghost[] ghosts = gh.getGhosts();
                if (red == 255 && green == 0 && blue == 0) { //Blinky
                    assertEquals(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, ghosts[0].getX());
                    assertEquals(yy * Pacman.TILE_HEIGHT, ghosts[0].getY());
                    assertEquals(Color.red, ghosts[0].getColor());
                } else if (red == 255 && green == 130 && blue == 255) { //Pinky
                    assertEquals(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, ghosts[1].getX());
                    assertEquals(yy * Pacman.TILE_HEIGHT, ghosts[1].getY());
                    assertEquals(Color.pink, ghosts[1].getColor());
                } else if (red == 0 && green == 255 && blue == 255) { //Inky
                    assertEquals(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, ghosts[2].getX());
                    assertEquals(yy * Pacman.TILE_HEIGHT, ghosts[2].getY());
                    assertEquals(Color.cyan, ghosts[2].getColor());
                } else if (red == 255 && green == 130 && blue == 0) { //Clyde
                    assertEquals(xx * Pacman.TILE_WIDTH + Pacman.TILE_WIDTH / 2, ghosts[3].getX());
                    assertEquals(yy * Pacman.TILE_HEIGHT, ghosts[3].getY());
                    assertEquals(Color.orange, ghosts[3].getColor());
                }
            }
        }
    }
}
