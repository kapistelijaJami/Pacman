package pacman.objects;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.framework.Direction;
import pacman.objects.Player;

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
        
        Direction d = p.getDirection();
        d.setDirection("up");
        p.update();
        
        assertEquals(45, p.getY());
        
        d.setDirection("right");
        p.update();
        
        assertEquals(29, p.getX());
        
        p.setVelocity(8);
        
        d.setDirection("left");
        p.update();
        
        assertEquals(21, p.getX());
        
        d.setDirection("down");
        p.update();
        
        assertEquals(53, p.getY());
    }
}
