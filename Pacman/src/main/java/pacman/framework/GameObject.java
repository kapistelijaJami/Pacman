package pacman.framework;

import java.awt.Color;
import java.awt.Graphics;
import pacman.game.Level;
import pacman.game.Pacman;
import pacman.objects.Ghost;
import pacman.objects.Player;

/**
 * Luokka toimii pohjana kaikille peliobjekteille
 * ja pitää kirjaa niiden sijainnista ja suunnasta.
 */
public abstract class GameObject {
    
    protected int x;
    protected int y;
    protected int startX;
    protected int startY;
    
    protected Direction direction;
    protected int velocity = 0;
    
    protected int width;
    protected int height;
    
    /**
     * Kontruktori luo GameObjectin ja asettaa parametrina saadut koordinaatit muuttujiin.
     * @param x X-koordinaatti alussa
     * @param y Y-koordinaatti alussa
     */
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.startX = x;
        this.startY = y;
    }
    
    /**
     * Päivitetään peliobjektia.
     * @param level Pelikenttä, jota käytetään liikkumisessa ja muissakin törmäämisessä
     */
    public abstract void update(Level level);
    
    /**
     * Piirretään peliobjekti.
     * @param g Graphics objekti, jolla piirretään peliobjekti
     */
    public abstract void render(Graphics g);
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void resetPosition() {
        this.x = this.startX;
        this.y = this.startY;
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    
    public int getVelocity() {
        return this.velocity;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getCenterCoordX() {
        return this.x + Pacman.TILE_WIDTH / 2;
    }
    
    public int getCenterCoordY() {
        return this.y + Pacman.TILE_HEIGHT / 2;
    }
    
    /**
     * Metodi palauttaa objektin keskipisteen millä tahansa arvolla.
     * @param x koordinaatti
     * @return keskipisteen koordinaatti
     */
    public int getCenterCoordX(int x) { //millä tahansa arvolla
        return x + Pacman.TILE_WIDTH / 2;
    }
    
    /**
     * Metodi palauttaa objektin keskipisteen millä tahansa arvolla.
     * @param y koordinaatti
     * @return keskipisteen koordinaatti
     */
    public int getCenterCoordY(int y) {
        return y + Pacman.TILE_HEIGHT / 2;
    }
    
    /**
     * Metodi katsoo ylittääkö peliobjektin keskikohta päivityksen aikana nykyisen Tile olion keskikohdan.
     * Jos ylittää niin tässä kohdassa voidaan toteuttaa kääntyminen.
     * @return voidaanko kääntyä
     */
    public boolean isPossibleToTurn() {
        if (direction == Direction.LEFT) {
            int newX = this.x - velocity;
            //jos (keskikohdan oikealla puolella ennen päivitystä && keskikohdan vasemmalla puolella päivityksen jälkeen)
            if (getCenterCoordX() % Pacman.TILE_WIDTH >= Pacman.TILE_WIDTH / 2 && getCenterCoordX(newX) % Pacman.TILE_WIDTH < Pacman.TILE_WIDTH / 2) {
                //keskikohta ylittää tilen keskikohdan tällä päivityksellä
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
    
    /**
     * Metodi liikuttaa peliobjektia ja pysähtyy oikeassa kohdassa, jos seuraava Tile on seinä.
     * @param level Pelikenttä, jota käytetään törmäämisessä.
     */
    public void moveAndCollide(Level level) {
        Tile[][] tiles = level.getTiles();
        if (direction == Direction.LEFT) {
            this.x -= velocity;
            if (this.x <= 0 && this.x > -1 * (Pacman.TILE_WIDTH / 2)) { //ollaan menossa yli laidan tunnelissa
                return;
            } else if (this.x < 0 && this.x <= -1 * (Pacman.TILE_WIDTH / 2)) { //ollaan jo yli laidan tunnelissa
                this.x = Pacman.TILE_WIDTH * 27;
                return;
            }
            
            int nextTileCoordX = (getCenterCoordX() - Pacman.TILE_WIDTH / 2) / Pacman.TILE_WIDTH;
            
            Tile nextTile = tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][nextTileCoordX];
            
            if (nextTile != null && nextTile.isWall()) {
                this.x = (nextTileCoordX + 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän oikealle puolelle
            }
        } else if (direction == Direction.DOWN) {
            this.y += velocity;
            
            int nextTileCoordY = (getCenterCoordY() + Pacman.TILE_HEIGHT / 2 + 1) / Pacman.TILE_HEIGHT;
            
            Tile nextTile = tiles[nextTileCoordY][getCenterCoordX() / Pacman.TILE_WIDTH];
            
            if (this.getClass() == Ghost.class) { //on haamu
                Ghost temp = (Ghost) this;
                if (temp.canCrossHatch() && nextTile != null && nextTile.isGhostHatch()) {
                    return;
                }
            }
            
            if (nextTile != null && nextTile.isWall()) {
                this.y = (nextTileCoordY - 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän yläpuolelle
            }
        } else if (direction == Direction.RIGHT) {
            this.x += velocity;
            
            if (this.x >= Pacman.TILE_WIDTH * 27 && this.x < Pacman.TILE_WIDTH * 28 - Pacman.TILE_WIDTH / 2) { //ollaan menossa yli laidan tunnelissa
                return;
            } else if (this.x >= Pacman.TILE_WIDTH * 28 - Pacman.TILE_WIDTH / 2) { //ollaan jo yli laidan tunnelissa
                this.x = -1 * (Pacman.TILE_WIDTH / 2);
                return;
            }
            
            int nextTileCoordX = (getCenterCoordX() + Pacman.TILE_WIDTH / 2) / Pacman.TILE_WIDTH;
            
            Tile nextTile = tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][nextTileCoordX];
            
            if (nextTile != null && nextTile.isWall()) {
                this.x = (nextTileCoordX - 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän vasemmalle puolelle
            }
        } else if (direction == Direction.UP) {
            this.y -= velocity;
            
            int nextTileCoordY = (getCenterCoordY() - Pacman.TILE_HEIGHT / 2) / Pacman.TILE_HEIGHT;
            
            Tile nextTile = tiles[nextTileCoordY][getCenterCoordX() / Pacman.TILE_WIDTH];
            
            if (this.getClass() == Ghost.class) { //on haamu
                Ghost temp = (Ghost) this;
                if (temp.canCrossHatch() && nextTile != null && nextTile.isGhostHatch()) {
                    return;
                }
            }
            
            if (nextTile != null && nextTile.isWall()) {
                this.y = (nextTileCoordY + 1) * Pacman.TILE_WIDTH; //vie pacmanin seinän alapuolelle
            }
        }
    }
}
