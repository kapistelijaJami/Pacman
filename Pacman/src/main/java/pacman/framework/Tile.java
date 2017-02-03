package pacman.framework;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {
    private int width;
    private int height;
    
    private int x;
    private int y;
    
    private boolean isWall;
    private boolean isFood;
    private boolean isEnergizer;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 24;
        this.height = 24;
        
        this.isWall = false;
        this.isFood = false;
        this.isEnergizer = false; //eli se iso pallo
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
    
    public boolean isWall() {
        return this.isWall;
    }
    
    public boolean isFood() {
        return this.isFood;
    }
    
    public boolean isEnergizer() {
        return this.isEnergizer;
    }
    
    public void render(Graphics g) {
        if (isWall) {
            g.setColor(Color.blue);
            g.fillRect(x, y, width, height);
        } else if (isFood) {
            g.setColor(Color.pink);
            g.fillRect(x + (width / 2 - 3), y + (height / 2 - 3), 5, 5);
        } else if (isEnergizer) {
            g.setColor(Color.pink);
            g.fillOval(x + 1, y + 1, width - 2, height - 2);
        }
    }
}
