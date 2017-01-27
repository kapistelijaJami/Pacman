package pacman.framework;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DirectionTest {
    
    public DirectionTest() {
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
    public void contsructorTest() {
        Direction d = new Direction("up");
        assertEquals("up", d.getDirection());
        
        Direction d2 = new Direction("blup");
        assertEquals("left", d2.getDirection());
        
        Direction d3 = new Direction("left");
        assertEquals("left", d3.getDirection());
        
        Direction d4 = new Direction("down");
        assertEquals("down", d4.getDirection());
        
        Direction d5 = new Direction("right");
        assertEquals("right", d5.getDirection());
    }
    
    @Test
    public void getAndSetDirectionTest() {
        Direction d = new Direction("up");
        
        d.setDirection("left");
        assertEquals("left", d.getDirection());
        
        d.setDirection("right");
        assertEquals("right", d.getDirection());
        
        d.setDirection("gup");
        assertEquals("right", d.getDirection());
    }
    
    @Test
    public void equalsMethodsTest() {
        Direction d = new Direction("up");
        Direction d2 = new Direction("left");
        
        assertFalse(d.equals(d2));
        
        d2.setDirection("up");
        assertTrue(d.equals(d2));
        
        assertFalse(d.equals("test"));
        
        d.setDirection("right");
        
        assertTrue(d.equals("right"));
        
        assertTrue(d2.equals("up"));
    }
}
