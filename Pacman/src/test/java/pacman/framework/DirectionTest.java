
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
    public void oppositeTest() {
        Direction down = Direction.DOWN;
        Direction up = Direction.UP;
        Direction left = Direction.LEFT;
        Direction right = Direction.RIGHT;
        
        assertEquals(down.opposite(), up);
        assertEquals(up.opposite(), down);
        assertEquals(left.opposite(), right);
        assertEquals(right.opposite(), left);
    }
    
    @Test
    public void isOppositeTest() {
        Direction down = Direction.DOWN;
        Direction up = Direction.UP;
        Direction left = Direction.LEFT;
        Direction right = Direction.RIGHT;
        
        assertTrue(down.isOpposite(up));
        assertTrue(up.isOpposite(down));
        assertTrue(left.isOpposite(right));
        assertTrue(right.isOpposite(left));
    }
}
