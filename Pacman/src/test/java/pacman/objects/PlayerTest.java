package pacman.objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Direction;
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
        Pacman.paused = false;
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
    }
}
