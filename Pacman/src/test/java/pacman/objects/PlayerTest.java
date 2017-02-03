package pacman.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Direction;
import pacman.framework.Tile;
import pacman.game.Level;
import pacman.game.Pacman;

public class PlayerTest {
    
    public PlayerTest() {
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
        Player p = new Player(24, 50);
        assertEquals(24, p.getX());
        assertEquals(50, p.getY());
        assertEquals(3, p.getVelocity());
    }
    
    @Test
    public void updateTest() {
        Player p = new Player(24, 50);
        Level level = new Level(28, 31);
        p.setVelocity(5);
        
        p.setNextDirection(Direction.UP);
        p.setDirection(Direction.UP);
        p.update(level);
        
        assertEquals(45, p.getY());
        
        p.setNextDirection(Direction.RIGHT);
        p.setDirection(Direction.RIGHT);
        p.update(level);
        
        assertEquals(29, p.getX());
        
        p.setVelocity(8);
        
        p.setNextDirection(Direction.LEFT);
        p.setDirection(Direction.LEFT);
        p.update(level);
        
        assertEquals(21, p.getX());
        
        p.setNextDirection(Direction.DOWN);
        p.setDirection(Direction.DOWN);
        p.update(level);
        
        assertEquals(53, p.getY());
        
        
        p.setNextDirection(Direction.UP);
        p.setDirection(Direction.DOWN);
        p.update(level);
        
        assertEquals(45, p.getY());
        
        p.setNextDirection(Direction.UP);
        p.setDirection(Direction.RIGHT);
        p.update(level);
        
        assertEquals(37, p.getY());
        assertEquals(21, p.getX());
    }
    
    @Test
    public void isPossibleToTurnTest() {
        Player p = new Player(2, 0);
        p.setVelocity(3);
        p.setDirection(Direction.LEFT);
        
        assertTrue(p.isPossibleToTurn());
        
        p = new Player(23, 0);
        p.setVelocity(3);
        p.setDirection(Direction.LEFT);
        
        assertFalse(p.isPossibleToTurn());
        
        p = new Player(3, 0);
        p.setVelocity(3);
        p.setDirection(Direction.LEFT);
        
        assertFalse(p.isPossibleToTurn());
        
        
        p = new Player(0, 2);
        p.setVelocity(3);
        p.setDirection(Direction.UP);
        
        assertTrue(p.isPossibleToTurn());
        
        p = new Player(0, 3);
        p.setVelocity(3);
        p.setDirection(Direction.UP);
        
        assertFalse(p.isPossibleToTurn());
        
        p = new Player(0, 0);
        p.setVelocity(3);
        p.setDirection(Direction.UP);
        
        assertTrue(p.isPossibleToTurn());
        
        
        p = new Player(22, 0);
        p.setVelocity(3);
        p.setDirection(Direction.RIGHT);
        
        assertTrue(p.isPossibleToTurn());
        
        p = new Player(21, 0);
        p.setVelocity(3);
        p.setDirection(Direction.RIGHT);
        
        assertFalse(p.isPossibleToTurn());
        
        p = new Player(0, 0);
        p.setVelocity(3);
        p.setDirection(Direction.RIGHT);
        
        assertTrue(p.isPossibleToTurn());
        
        
        p = new Player(0, 22);
        p.setVelocity(3);
        p.setDirection(Direction.DOWN);
        
        assertTrue(p.isPossibleToTurn());
        
        p = new Player(0, 21);
        p.setVelocity(3);
        p.setDirection(Direction.DOWN);
        
        assertFalse(p.isPossibleToTurn());
        
        p = new Player(0, 24);
        p.setVelocity(3);
        p.setDirection(Direction.DOWN);
        
        assertTrue(p.isPossibleToTurn());
    }
    
    @Test
    public void nextDirectionIsWallTest() {
        Level level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png");
        Pacman game = new Pacman();
        level.makeLevelFromImage(game);
        Tile[][] tiles = level.getTiles();
        
        Player p = new Player(13 * 24, 23 * 24);
        p.setDirection(Direction.LEFT);
        
        p.setNextDirection(Direction.UP);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.DOWN);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.LEFT);
        
        p.setVelocity(24);
        p.update(level);
        
        p.setNextDirection(Direction.UP);
        assertFalse(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.DOWN);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.RIGHT);
        
        p.setDirection(Direction.RIGHT);
        p.setVelocity(1);
        p.update(level);
        
        p.setNextDirection(Direction.UP);
        assertFalse(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.DOWN);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.RIGHT);
        
        p.setVelocity(23);
        p.update(level);
        p.setNextDirection(Direction.UP);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.DOWN);
        assertTrue(p.nextDirectionIsWall(tiles));
        
        
        
        p = new Player(6 * 24, 21 * 24);
        p.setDirection(Direction.UP);
        
        p.setNextDirection(Direction.LEFT);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.RIGHT);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.UP);
        
        p.setVelocity(24);
        p.update(level);
        
        p.setNextDirection(Direction.LEFT);
        assertFalse(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.RIGHT);
        assertFalse(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.DOWN);
        
        p.setDirection(Direction.DOWN);
        
        p.setVelocity(1);
        p.update(level);
        
        p.setNextDirection(Direction.LEFT);
        assertFalse(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.RIGHT);
        assertFalse(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.DOWN);
        
        p.setVelocity(23);
        p.update(level);
        
        p.setNextDirection(Direction.LEFT);
        assertTrue(p.nextDirectionIsWall(tiles));
        p.setNextDirection(Direction.RIGHT);
        assertTrue(p.nextDirectionIsWall(tiles));
    }
    
    @Test
    public void collisionTest() {
        Player p = new Player(6 * 24, 21 * 24);
        Level level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png");
        Pacman game = new Pacman();
        level.makeLevelFromImage(game);
        
        Tile[][] tiles = level.getTiles();
        
        p.collision(level);
        
        Tile tile = tiles[(21 * 24 + 12) / Pacman.TILE_HEIGHT][(6 * 24 + 12) / Pacman.TILE_WIDTH];
        tile.setIsFood(true);
        
        p.collision(level);
        assertFalse(tile.isFood());
        
        tile.setIsEnergizer(true);
        p.collision(level);
        assertFalse(tile.isEnergizer());
    }
}
