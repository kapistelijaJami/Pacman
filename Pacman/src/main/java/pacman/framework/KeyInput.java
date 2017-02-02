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
        
        if (key == KeyEvent.VK_LEFT) {
            player.setDirection(Direction.LEFT);
        }
        
        if (key == KeyEvent.VK_DOWN) {
            player.setDirection(Direction.DOWN);
        }
        
        if (key == KeyEvent.VK_RIGHT) {
            player.setDirection(Direction.RIGHT);
        }
        
        if (key == KeyEvent.VK_UP) {
            player.setDirection(Direction.UP);
        }
    }
    
    public Player getPlayer() {
        return player;
    }
}
