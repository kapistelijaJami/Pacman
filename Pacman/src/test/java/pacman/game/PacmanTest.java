package pacman.game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Tile;

public class PacmanTest {
    
    public PacmanTest() {
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
    public void initTest() {
        Pacman game = new Pacman();
        
        game.init();
        
        Level level = game.getLevel();
        
        assertTrue(level != null);
        
        Tile[][] tiles = level.getTiles();
        
        assertTrue(tiles != null);
        
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                assertTrue(tile != null);
            }
        }
    }
}
