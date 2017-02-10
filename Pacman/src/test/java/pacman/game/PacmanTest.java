package pacman.game;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Tile;
import pacman.objects.Ghost;

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
    
    @Test
    public void setGhostTest() {
        Pacman game = new Pacman();
        
        Ghost g = new Ghost(45, 0);
        game.setGhost(0, g);
        
        g = new Ghost(40, 0);
        game.setGhost(1, g);
        
        g = new Ghost(20, 0);
        game.setGhost(2, g);
        
        g = new Ghost(10, 0);
        game.setGhost(3, g);
        
        GhostHandler gh = game.getGhostHandler();
        
        assertEquals(45, gh.getGhost(0).getX());
        assertEquals(40, gh.getGhost(1).getX());
        assertEquals(20, gh.getGhost(2).getX());
        assertEquals(10, gh.getGhost(3).getX());
    }
}
