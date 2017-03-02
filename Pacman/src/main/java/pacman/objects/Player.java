package pacman.objects;

import pacman.framework.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pacman.framework.Direction;
import pacman.framework.Tile;
import pacman.game.Level;
import pacman.game.Pacman;
import pacman.imagehandling.Animation;
import pacman.imagehandling.Textures;

/**
 * Luokka tarjoaa metodeita pelaajan ohjaamisen ja törmäämisen hallintaan,
 * sekä pitää kirjaa pelaajan sijainnista ja suunnasta.
 */
public class Player extends GameObject {
    
    private Direction nextDirection;
    private Pacman game;
    private int pisteet;
    
    private Animation moveRight, moveDown, moveLeft, moveUp;
    
    /**
     * Alustetaan muuttujat.
     * @param x X-koordinaatti alussa
     * @param y Y-koordinaatti alussa
     */
    public Player(int x, int y) {
        super(x, y);
        width = 24;
        height = 24;
        
        velocity = 3;
        
        this.direction = Direction.LEFT;
        this.nextDirection = this.direction;
        this.pisteet = 0;
        
        Textures textures = new Textures();
        textures.getPlayerImages();
        
        int speed = 1;
        
        moveRight = new Animation(speed, textures.getPlayerImage(0), textures.getPlayerImage(1), textures.getPlayerImage(2), textures.getPlayerImage(3), textures.getPlayerImage(4));
        moveDown = new Animation(speed, textures.getPlayerImage(5), textures.getPlayerImage(6), textures.getPlayerImage(7), textures.getPlayerImage(8), textures.getPlayerImage(9));
        moveLeft = new Animation(speed, textures.getPlayerImage(10), textures.getPlayerImage(11), textures.getPlayerImage(12), textures.getPlayerImage(13), textures.getPlayerImage(14));
        moveUp = new Animation(speed, textures.getPlayerImage(15), textures.getPlayerImage(16), textures.getPlayerImage(17), textures.getPlayerImage(18), textures.getPlayerImage(19));
    }
    
    public void setGame(Pacman game) {
        this.game = game;
    }
    
    public void setNextDirection(Direction nextDir) {
        this.nextDirection = nextDir;
    }
    
    public Direction getNextDirection() {
        return this.nextDirection;
    }
    
    public int getPisteet() {
        return this.pisteet;
    }
    
    public void setPisteet(int pisteet) {
        this.pisteet = pisteet;
    }
    
    public void reset() {
        velocity = 3;
        
        this.direction = Direction.LEFT;
        this.nextDirection = this.direction;
        
        moveLeft.reset();
        moveDown.reset();
        moveRight.reset();
        moveUp.reset();
    }
    
    /**
     * Metodi katsoo tuleeko seuraavaan suuntaan liikuttaessa seinä vastaan.
     * @param level Pelikenttä
     * @return Tuleeko seinä vastaan
     */
    public boolean nextDirectionIsWall(Level level) {
        Tile[][] tiles = level.getTiles();
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
    
    /**
     * Metodi tarkistaa törmääkö pelaaja syötävien kanssa, ja muokkaa niiden tilaa tarvittaessa.
     * @param level Pelikenttä
     */
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
            pisteet += 10;
            
            if (level.allFoodEaten()) {
                game.setPaused(true);
                game.init();
            }
        } else if (tile.isEnergizer()) {
            tile.setIsEnergizer(false);
            game.getGhostHandler().extendFrightened();
            pisteet += 50;
        }
    }
    
    public void runAnimation() {
        if (direction == Direction.UP) {
            moveUp.runAnimation();
        } else if (direction == Direction.LEFT) {
            moveLeft.runAnimation();
        } else if (direction == Direction.DOWN) {
            moveDown.runAnimation();
        } else if (direction == Direction.RIGHT) {
            moveRight.runAnimation();
        }
    }
    
    public BufferedImage getCurrentImage() {
        BufferedImage image = null;
        if (direction == Direction.UP) {
            image = moveUp.getCurrentImage();
        } else if (direction == Direction.LEFT) {
            image = moveLeft.getCurrentImage();
        } else if (direction == Direction.DOWN) {
            image = moveDown.getCurrentImage();
        } else if (direction == Direction.RIGHT) {
            image = moveRight.getCurrentImage();
        }
        
        return image;
    }
    
    @Override
    public void update(Level level) {
        //VAIHDETAAN SUUNTAA
        if (direction.isOpposite(nextDirection)) { //tehdään U-käännös
            direction = nextDirection;
        } else if (this.direction != this.nextDirection && isPossibleToTurn() && !nextDirectionIsWall(level)) { //käännytään
            direction = nextDirection;
        }
        
        int oldX = this.x;
        int oldY = this.y;
        //LIIKKUMINEN JA TÖRMÄÄMINEN
        moveAndCollide(level); //tarkista vielä tunnelissa liikkuminen uudestaan
        
        //SYÖDÄÄNKÖ JOTAIN
        collision(level);
        
        //PÄIVITETÄÄN ANIMAATIO
        if (oldX != this.x || oldY != this.y) { //jos ollaan liikuttu
            runAnimation();
        }
    }
    
    @Override
    public void render(Graphics g) {
        int startX = x - width / 2 + 4;
        int startY = y - height / 2 + 4;
        
        g.drawImage(getCurrentImage(), startX, startY, null);
    }
}
