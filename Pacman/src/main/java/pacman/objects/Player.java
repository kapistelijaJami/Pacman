package pacman.objects;

import pacman.framework.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import pacman.framework.Direction;

public class Player extends GameObject {
    
    public Player(int x, int y) {
        super(x, y);
        width = 24;
        height = 24;
        
        velocity = 3;
        
        this.direction = Direction.LEFT;
    }
    
    @Override
    public void update() {
        if (direction == Direction.LEFT) {
            this.x -= velocity;
        } else if (direction == Direction.DOWN) {
            this.y += velocity;
        } else if (direction == Direction.RIGHT) {
            this.x += velocity;
        } else if (direction == Direction.UP) {
            this.y -= velocity;
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(x, y, width, height);
    }
}
