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

public class GameObjectMoveAndCollideTest {
    private Tile[][] tiles;
    private Level level;
    
    @Before
    public void setUp() {
        level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png");
        Pacman game = new Pacman();
        level.makeLevelFromImage(game);
        
        tiles = level.getTiles();
    }
    
    @Test
    public void moveAndCollideTestLeft() {
        Player p = new Player(324, 552);
        for (int i = 0; i < 200; i++) {
            p.update(level);
        }
        assertEquals(144, p.getX());
    }
    
    @Test
    public void moveAndCollideTestRight() {
        Player p = new Player(324, 552);
        p.setDirection(Direction.RIGHT);
        p.setNextDirection(Direction.RIGHT);
        for (int i = 0; i < 200; i++) {
            p.update(level);
        }
        assertEquals(504, p.getX());
    }
    
    @Test
    public void moveAndCollideTestUp() {
        Player p = new Player(504, 552);
        p.setDirection(Direction.UP);
        p.setNextDirection(Direction.UP);
        for (int i = 0; i < 200; i++) {
            p.update(level);
        }
        assertEquals(24, p.getY());
    }
    
    @Test
    public void moveAndCollideTestDown() {
        Player p = new Player(504, 552);
        p.setDirection(Direction.DOWN);
        p.setNextDirection(Direction.DOWN);
        for (int i = 0; i < 200; i++) {
            p.update(level);
        }
        assertEquals(624, p.getY());
    }
    
    @Test
    public void moveAndCollideTestLeftTunnel() {
        Player p = new Player(144, 336);
        for (int i = 0; i < 200; i++) {
            p.update(level);
        }
        assertEquals(432, p.getX());
        assertEquals(336, p.getY());
    }
    
    @Test
    public void moveAndCollideTestRightTunnel() {
        Player p = new Player(432, 336);
        p.setDirection(Direction.RIGHT);
        p.setNextDirection(Direction.RIGHT);
        
        for (int i = 0; i < 200; i++) {
            p.update(level);
        }
        assertEquals(216, p.getX());
        assertEquals(336, p.getY());
    }
}
