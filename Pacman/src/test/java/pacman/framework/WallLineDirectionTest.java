package pacman.framework;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class WallLineDirectionTest {
    
    @Test
    public void makeCornerTestVertical() {
        WallLineDirection dir = WallLineDirection.makeCorner(Direction.UP, Direction.UP);
        assertEquals(WallLineDirection.VERTICAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.UP, Direction.DOWN);
        assertEquals(WallLineDirection.VERTICAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.UP, null);
        assertEquals(WallLineDirection.VERTICAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.DOWN, Direction.UP);
        assertEquals(WallLineDirection.VERTICAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.DOWN, Direction.DOWN);
        assertEquals(WallLineDirection.VERTICAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.DOWN, null);
        assertEquals(WallLineDirection.VERTICAL, dir);
        
        dir = WallLineDirection.makeCorner(null, Direction.DOWN);
        assertEquals(WallLineDirection.VERTICAL, dir);
    }
    
    @Test
    public void makeCornerTestHorizontal() {
        WallLineDirection dir = WallLineDirection.makeCorner(Direction.LEFT, Direction.LEFT);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.LEFT, Direction.RIGHT);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.LEFT, null);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.RIGHT, Direction.LEFT);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.RIGHT, Direction.RIGHT);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
        
        dir = WallLineDirection.makeCorner(Direction.RIGHT, null);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
    }
    
    @Test
    public void makeCornerTestUp() {
        WallLineDirection dir = WallLineDirection.makeCorner(Direction.LEFT, Direction.UP);
        assertEquals(WallLineDirection.UP_LEFT, dir);
        
        dir = WallLineDirection.makeCorner(Direction.UP, Direction.LEFT);
        assertEquals(WallLineDirection.UP_LEFT, dir);
        
        dir = WallLineDirection.makeCorner(Direction.UP, Direction.RIGHT);
        assertEquals(WallLineDirection.UP_RIGHT, dir);
        
        dir = WallLineDirection.makeCorner(Direction.RIGHT, Direction.UP);
        assertEquals(WallLineDirection.UP_RIGHT, dir);
    }
    
    @Test
    public void makeCornerTestDown() {
        WallLineDirection dir = WallLineDirection.makeCorner(Direction.LEFT, Direction.DOWN);
        assertEquals(WallLineDirection.DOWN_LEFT, dir);
        
        dir = WallLineDirection.makeCorner(Direction.DOWN, Direction.LEFT);
        assertEquals(WallLineDirection.DOWN_LEFT, dir);
        
        dir = WallLineDirection.makeCorner(Direction.DOWN, Direction.RIGHT);
        assertEquals(WallLineDirection.DOWN_RIGHT, dir);
        
        dir = WallLineDirection.makeCorner(Direction.RIGHT, Direction.DOWN);
        assertEquals(WallLineDirection.DOWN_RIGHT, dir);
    }
    
    @Test
    public void makeCornerTestBothNull() {
        WallLineDirection dir = WallLineDirection.makeCorner(null, null);
        assertEquals(WallLineDirection.HORIZONTAL, dir);
    }
}
