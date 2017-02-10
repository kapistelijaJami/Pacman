package pacman.objects;

import pacman.framework.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import pacman.framework.Direction;
import pacman.framework.Tile;
import pacman.game.Level;
import pacman.game.Pacman;

/**
 * Luokka tarjoaa metodeita pelaajan ohjaamisen ja törmäämisen hallintaan,
 * sekä pitää kirjaa pelaajan sijainnista ja suunnasta.
 */
public class Player extends GameObject {
    
    private Direction nextDirection;
    
    public Player(int x, int y) {
        super(x, y);
        width = 24;
        height = 24;
        
        velocity = 3;
        
        this.direction = Direction.LEFT;
        this.nextDirection = this.direction;
    }
    
    public void setNextDirection(Direction nextDir) {
        this.nextDirection = nextDir;
    }
    
    public Direction getNextDirection() {
        return this.nextDirection;
    }
    
    public boolean nextDirectionIsWall(Tile[][] tiles) {
        if (nextDirection == Direction.LEFT) {
            Tile tile = tiles[getCenterCoordY() / Pacman.TILE_WIDTH][getCenterCoordX() / Pacman.TILE_WIDTH - 1];
            if (tile != null && tile.isWall()) {
                return true;
            }
        } else if (nextDirection == Direction.DOWN) {
            Tile tile = tiles[getCenterCoordY() / Pacman.TILE_WIDTH + 1][getCenterCoordX() / Pacman.TILE_WIDTH];
            if (tile != null && tile.isWall()) {
                return true;
            }
        } else if (nextDirection == Direction.RIGHT) {
            Tile tile = tiles[getCenterCoordY() / Pacman.TILE_WIDTH][getCenterCoordX() / Pacman.TILE_WIDTH + 1];
            if (tile != null && tile.isWall()) {
                return true;
            }
        } else if (nextDirection == Direction.UP) {
            Tile tile = tiles[getCenterCoordY() / Pacman.TILE_WIDTH - 1][getCenterCoordX() / Pacman.TILE_WIDTH];
            if (tile != null && tile.isWall()) {
                return true;
            }
        }
        
        return false;
    }
    
    public void collision(Level level) {
        Tile[][] tiles = level.getTiles();
        
        int centerCoordX = getCenterCoordX();
        int centerCoordY = getCenterCoordY();
        
        Tile tile = tiles[centerCoordY / Pacman.TILE_HEIGHT][centerCoordX / Pacman.TILE_WIDTH];
        
        if (tile == null) {
            return;
        }
        
        //syötävät
        if (tile.isFood()) {
            tile.setIsFood(false);
        } else if (tile.isEnergizer()) {
            tile.setIsEnergizer(false);
        }
    }
    
    @Override
    public void update(Level level) {
        Tile[][] tiles = level.getTiles();
        
        //VAIHDETAAN SUUNTAA
        if (direction.isOpposite(nextDirection)) { //tehdään U-käännös
            direction = nextDirection;
        } else if (this.direction != this.nextDirection && isPossibleToTurn() && !nextDirectionIsWall(tiles)) { //käännytään
            direction = nextDirection;
        }
        
        //LIIKKUMINEN JA TÖRMÄÄMINEN
        moveAndCollide(tiles); //tarkista vielä tunnelissa liikkuminen uudestaan
        
        collision(level);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x - width / 2 + 4, y - height / 2 + 4, width * 2 - 8, height * 2 - 8); //oikean kokoinen pacman
        //g.fillRect(x, y, width, height); //testitarkoitukseen
    }
}
