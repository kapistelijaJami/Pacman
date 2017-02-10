package pacman.framework;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.game.Pacman;

public class TileTest {
    
    public TileTest() {
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
        Tile tile = new Tile(4, 6);
        
        assertFalse(tile.isWall());
        assertFalse(tile.isFood());
        assertFalse(tile.isEnergizer());
        
        tile.setIsWall(true);
        tile.setIsFood(true);
        tile.setIsEnergizer(true);
        
        assertTrue(tile.isWall());
        assertTrue(tile.isFood());
        assertTrue(tile.isEnergizer());
    }
    
    @Test
    public void isCornerTileTest() {
        Tile tile = new Tile(4, 6);
        assertFalse(tile.isCornerTile());
        
        tile.setIsCornerTile(true);
        assertTrue(tile.isCornerTile());
    }
    
    @Test
    public void isGhostHatchTest() {
        Tile tile = new Tile(4, 6);
        assertFalse(tile.isGhostHatch());
        
        tile.setIsGhostHatch(true);
        assertTrue(tile.isGhostHatch());
    }
    
    @Test
    public void renderTest() {
        Tile tile = new Tile(4, 6);
        Pacman game = new Pacman();
        game.createWindow();
        game.createBufferStrategy(3);
        BufferStrategy bs = game.getBufferStrategy();
        
        Graphics g = bs.getDrawGraphics();
        
        tile.setIsWall(true);
        tile.render(g);
        assertEquals(Color.blue, g.getColor());
        tile.setIsWall(false);
        
        tile.setIsFood(true);
        tile.render(g);
        assertEquals(Color.pink, g.getColor());
        tile.setIsFood(false);
        
        tile.setIsEnergizer(true);
        g.setColor(Color.red);
        tile.render(g);
        assertEquals(Color.pink, g.getColor());
        tile.setIsEnergizer(false);
    }
}
