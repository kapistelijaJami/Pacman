package pacman.game;

import java.awt.image.BufferedImage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Tile;

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
}
