package pacman.framework;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.objects.Player;

public class GameObjectTest {
    
    public GameObjectTest() {
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
    public void setAndGetVelocityTest() {
        Player p = new Player(10, 15);
        
        p.setVelocity(15);
        assertEquals(15, p.getVelocity());
        
        p.setVelocity(80);
        assertEquals(80, p.getVelocity());
    }
    
    @Test
    public void setAndGetPositionTest() {
        Player p = new Player(10, 15);
        
        p.setX(15);
        assertEquals(15, p.getX());
        
        p.setY(80);
        assertEquals(80, p.getY());
    }
    
    @Test
    public void setAndGetSizeTest() {
        Player p = new Player(10, 15);
        
        p.setWidth(50);
        assertEquals(50, p.getWidth());
        
        p.setHeight(80);
        assertEquals(80, p.getHeight());
    }
}
