package pacman.framework;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pacman.game.Pacman;
import pacman.objects.Player;

public class KeyInputTest {
    
    public KeyInputTest() {
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
        Player p = new Player(34, 85);
        KeyInput input = new KeyInput(p);
        assertTrue(input.getPlayer().getX() == 34);
        assertTrue(input.getPlayer().getY() == 85);
    }
    
    @Test
    public void keyPressedTest() {
        Player p = new Player(34, 85);
        JFrame f = new JFrame();
        
        KeyInput input = new KeyInput(p);
        KeyEvent e = new KeyEvent(f, KeyEvent.VK_RIGHT, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'Z');
        
        input.keyPressed(e);
        
        assertEquals(Direction.RIGHT, p.getNextDirection());
        
        e = new KeyEvent(f, KeyEvent.VK_LEFT, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'Z');
        
        p.setNextDirection(Direction.RIGHT);
        input.keyPressed(e);
        
        assertEquals(Direction.LEFT, p.getNextDirection());
        
        e = new KeyEvent(f, KeyEvent.VK_UP, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'Z');
        input.keyPressed(e);
        
        assertEquals(Direction.UP, p.getNextDirection());
        
        e = new KeyEvent(f, KeyEvent.VK_DOWN, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'Z');
        input.keyPressed(e);
        
        assertEquals(Direction.DOWN, p.getNextDirection());
        
        e = new KeyEvent(f, KeyEvent.VK_ENTER, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, 'Z');
        
        Pacman.paused = false;
        input.keyPressed(e);
        assertTrue(Pacman.paused);
        
        Pacman.paused = true;
        input.keyPressed(e);
        assertFalse(Pacman.paused);
        
        e = new KeyEvent(f, KeyEvent.VK_SPACE, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, 'Z');
        
        Pacman.paused = false;
        input.keyPressed(e);
        assertTrue(Pacman.paused);
        
        Pacman.paused = true;
        input.keyPressed(e);
        assertFalse(Pacman.paused);
    }
}
