package pacman.framework;

import java.awt.Color;
import java.awt.Graphics;
import pacman.game.Pacman;

/**
 * Luokka pitää kirjaa Tile-olion sijainnista kentässä, ja sen attribuuteista,
 * sekä tarjoaa metodeita, joilla voi muuttaa olion tilaa.
 */
public class Tile {
    private int width;
    private int height;
    
    private int x;
    private int y;
    
    private boolean isWall;
    private boolean isFood;
    private boolean isEnergizer;
    private boolean isGhostHatch;
    
    private WallLineDirection lineDirection;
    private boolean isCornerTile;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 24;
        this.height = 24;
        
        this.isWall = false;
        this.isFood = false;
        this.isEnergizer = false; //eli iso pallo
        this.isCornerTile = false;
        this.isGhostHatch = false;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setIsWall(boolean isWall) {
        this.isWall = isWall;
    }
    
    public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }
    
    public void setIsEnergizer(boolean isEnergizer) {
        this.isEnergizer = isEnergizer;
    }
    
    public void setLineDirection(WallLineDirection lineDirection) {
        this.lineDirection = lineDirection;
    }
    
    public WallLineDirection getLineDirection() {
        return this.lineDirection;
    }
    
    public void setIsCornerTile(boolean isCornerTile) {
        this.isCornerTile = isCornerTile;
    }
    
    public boolean isCornerTile() {
        return this.isCornerTile;
    }
    
    public boolean isWall() {
        return this.isWall;
    }
    
    public boolean isFood() {
        return this.isFood;
    }
    
    public boolean isEnergizer() {
        return this.isEnergizer;
    }
    
    public void setIsGhostHatch(boolean isGhostHatch) {
        this.isGhostHatch = isGhostHatch;
    }
    
    public boolean isGhostHatch() {
        return this.isGhostHatch;
    }
    
    /**
     * Metodi piirtää tile -olion sen ominaisuuksien perusteella.
     * 
     * @param g piirtämiseen tarvittava olio
     */
    public void render(Graphics g) {
        if (isGhostHatch) {
            g.setColor(Color.PINK);
            g.fillRect(x, y + Pacman.TILE_HEIGHT / 2 - 1, width, 4);
            
        } else if (isWall) {
            g.setColor(Color.blue);
            
            if (lineDirection != null) {
                if (lineDirection == WallLineDirection.VERTICAL) {
                    g.fillRect(x + Pacman.TILE_WIDTH / 2, y, 2, height);
                } else if (lineDirection == WallLineDirection.HORIZONTAL) {
                    g.fillRect(x, y + Pacman.TILE_HEIGHT / 2, width, 2);
                } else {
                    drawCorner(g); //piirtää kulman
                }
            } else {
                g.fillRect(x, y, width, height);
            }
            
        } else if (isFood) {
            g.setColor(Color.pink);
            g.fillRect(x + (width / 2 - 3), y + (height / 2 - 3), 5, 5);
        } else if (isEnergizer) {
            g.setColor(Color.pink);
            g.fillOval(x + 1, y + 1, width - 2, height - 2);
        }
    }
    
    /**
     * Metodi piirtää kulman.
     * 
     * @param g piirtämiseen tarvittava olio
     */
    public void drawCorner(Graphics g) {
        if (lineDirection == WallLineDirection.UP_LEFT) {
            g.fillRect(x + Pacman.TILE_HEIGHT / 2, y, 2, Pacman.TILE_HEIGHT / 2 + 1); //UP
            g.fillRect(x, y + Pacman.TILE_HEIGHT / 2, Pacman.TILE_WIDTH / 2 + 1, 2); //LEFT

        } else if (lineDirection == WallLineDirection.UP_RIGHT) {
            g.fillRect(x + Pacman.TILE_HEIGHT / 2, y, 2, Pacman.TILE_HEIGHT / 2); //UP
            g.fillRect(x + Pacman.TILE_HEIGHT / 2, y + Pacman.TILE_HEIGHT / 2, Pacman.TILE_WIDTH / 2, 2); //RIGHT

        } else if (lineDirection == WallLineDirection.DOWN_LEFT) {
            g.fillRect(x + Pacman.TILE_HEIGHT / 2, y + Pacman.TILE_HEIGHT / 2, 2, Pacman.TILE_HEIGHT / 2); //DOWN
            g.fillRect(x, y + Pacman.TILE_HEIGHT / 2, Pacman.TILE_WIDTH / 2, 2); //LEFT

        } else if (lineDirection == WallLineDirection.DOWN_RIGHT) {
            g.fillRect(x + Pacman.TILE_HEIGHT / 2, y + Pacman.TILE_HEIGHT / 2, 2, Pacman.TILE_HEIGHT / 2); //DOWN
            g.fillRect(x + Pacman.TILE_HEIGHT / 2, y + Pacman.TILE_HEIGHT / 2, Pacman.TILE_WIDTH / 2, 2); //RIGHT
        }
    }
    
    public double distance(Tile other) { //lasketaan pythagoraan lauseella
        double distanceX = Math.pow(Math.abs(this.x / width - other.x / width), 2); // x^2
        double distanceY = Math.pow(Math.abs(this.y / height - other.y / height), 2); // y^2
        
        return Math.sqrt(distanceX + distanceY); //sqrt(x^2 + y^2) = hypotenuusa
    }
}
