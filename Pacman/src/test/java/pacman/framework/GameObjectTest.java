package pacman.framework;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.game.Level;
import pacman.game.Pacman;
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
    
    @Test
    public void getDirectionTest() {
        Player p = new Player(10, 15);
        p.setDirection(Direction.UP);
        assertEquals(Direction.UP, p.getDirection());
    }
    
    @Test
    public void isPossibleToTurnTest() { //jaa tätä testiä osiin
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
}
