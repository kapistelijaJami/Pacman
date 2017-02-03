package pacman.objects;

import pacman.framework.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import pacman.framework.Direction;
import pacman.framework.Tile;
import pacman.game.Level;
import pacman.game.Pacman;

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
    
    public boolean isPossibleToTurn() { //jos liikkuu tämän päivityksen aikana nykyisen Tilen keskikohdan yli, niin voi kääntyä
        if (direction == Direction.LEFT) {
            int newX = this.x - velocity;
            if (getCenterCoordX() % Pacman.TILE_WIDTH >= Pacman.TILE_WIDTH / 2 && getCenterCoordX(newX) % Pacman.TILE_WIDTH < Pacman.TILE_WIDTH / 2) {
                //pacmanin keskikohta ylittää tilen keskikohdan tällä päivityksellä
                return true;
            }
        } else if (direction == Direction.DOWN) {
            int newY = this.y + velocity;
            if (getCenterCoordY() % Pacman.TILE_HEIGHT <= Pacman.TILE_HEIGHT / 2 && getCenterCoordY(newY) % Pacman.TILE_HEIGHT > Pacman.TILE_HEIGHT / 2) {
                return true;
            }
        } else if (direction == Direction.RIGHT) {
            int newX = this.x + velocity;
            if (getCenterCoordX() % Pacman.TILE_WIDTH <= Pacman.TILE_WIDTH / 2 && getCenterCoordX(newX) % Pacman.TILE_WIDTH > Pacman.TILE_WIDTH / 2) {
                return true;
            }
        } else if (direction == Direction.UP) {
            int newY = this.y - velocity;
            if (getCenterCoordY() % Pacman.TILE_HEIGHT >= Pacman.TILE_HEIGHT / 2 && getCenterCoordY(newY) % Pacman.TILE_HEIGHT < Pacman.TILE_HEIGHT / 2) {
                return true;
            }
        }
        
        return false;
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
    
    public int getCenterCoordX() {
        return this.x + Pacman.TILE_WIDTH / 2;
    }
    
    public int getCenterCoordY() {
        return this.y + Pacman.TILE_HEIGHT / 2;
    }
    
    //millä tahansa arvolla
    public int getCenterCoordX(int x) {
        return x + Pacman.TILE_WIDTH / 2;
    }
    
    public int getCenterCoordY(int y) {
        return y + Pacman.TILE_HEIGHT / 2;
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
            
            //tässä voi tehdä vielä ylimääräisiä tarkistuksia, ettei ole mahdollista kääntyä mutkia nopeammin eri kerroilla
            //nyt on mahdollista kääntyä max nopeuden - 1 verran pikseliä aikaisemmin kuin käännöksen keskikohta on
            //mutta tämä näyttäisi toimivan aika hyvin kuitenkin
            
            //vastaa käytännössä original pacmanissä olevaa cornering kääntymistä:
            //http://www.gamasutra.com/db_area/images/feature/3938/cornering.png
        }
        
        
        //LIIKKUMINEN JA TÖRMÄÄMINEN
        if (direction == Direction.LEFT) {
            this.x -= velocity;
            
            int nextTileCoordX = (getCenterCoordX() - Pacman.TILE_WIDTH / 2) / Pacman.TILE_WIDTH;
            
            Tile nextTile = tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][nextTileCoordX];
            
            if (nextTile != null && nextTile.isWall()) {
                this.x = (nextTileCoordX + 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän oikealle puolelle
            }
        } else if (direction == Direction.DOWN) {
            this.y += velocity;
            
            int nextTileCoordY = (getCenterCoordY() + Pacman.TILE_HEIGHT / 2 + 1) / Pacman.TILE_HEIGHT;
            
            Tile nextTile = tiles[nextTileCoordY][getCenterCoordX() / Pacman.TILE_WIDTH];
            
            if (nextTile != null && nextTile.isWall()) {
                this.y = (nextTileCoordY - 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän yläpuolelle
            }
        } else if (direction == Direction.RIGHT) {
            this.x += velocity;
            
            int nextTileCoordX = (getCenterCoordX() + Pacman.TILE_WIDTH / 2 + 1) / Pacman.TILE_WIDTH;
            
            Tile nextTile = tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][nextTileCoordX];
            
            if (nextTile != null && nextTile.isWall()) {
                this.x = (nextTileCoordX - 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän oikealle puolelle
            }
        } else if (direction == Direction.UP) {
            this.y -= velocity;
            
            int nextTileCoordY = (getCenterCoordY() - Pacman.TILE_HEIGHT / 2) / Pacman.TILE_HEIGHT;
            
            Tile nextTile = tiles[nextTileCoordY][getCenterCoordX() / Pacman.TILE_WIDTH];
            
            if (nextTile != null && nextTile.isWall()) {
                this.y = (nextTileCoordY + 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän alapuolelle
            }
        }
        
        
        collision(level);
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        //g.fillRect(x - width / 2 + 2, y - height / 2 + 2, width * 2 - 4, height * 2 - 4); //oikean kokoinen pacman
        g.fillRect(x, y, width, height); //testitarkoitukseen
    }
}
