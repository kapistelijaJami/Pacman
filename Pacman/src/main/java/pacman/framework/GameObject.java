package pacman.framework;

import java.awt.Graphics;

public abstract class GameObject {
    
    protected int x;
    protected int y;
    protected Direction direction;
    protected int velocity = 0;
    
    protected int width;
    protected int height;
    
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void update();
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
}
