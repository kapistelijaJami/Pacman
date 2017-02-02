package pacman.objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Direction;

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
        p.setVelocity(5);
        
        p.setDirection(Direction.UP);
        p.update();
        
        assertEquals(45, p.getY());
        
        p.setDirection(Direction.RIGHT);
        p.update();
        
        assertEquals(29, p.getX());
        
        p.setVelocity(8);
        
        p.setDirection(Direction.LEFT);
        p.update();
        
        assertEquals(21, p.getX());
        
        p.setDirection(Direction.DOWN);
        p.update();
        
        assertEquals(53, p.getY());
    }
}
