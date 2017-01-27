package pacman.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import pacman.objects.Player;

public class KeyInput extends KeyAdapter {
    private Player player;
    
    public KeyInput(Player player) {
        this.player = player;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        Direction direction = player.getDirection();
        
        if (key == KeyEvent.VK_LEFT) {
            direction.setDirection("left");
        }
        
        if (key == KeyEvent.VK_DOWN) {
            direction.setDirection("down");
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            direction.setDirection("right");
        }
        
        if (key == KeyEvent.VK_UP) {
            direction.setDirection("up");
        }
    }
    
    public Player getPlayer() {
        return player;
    }
}
