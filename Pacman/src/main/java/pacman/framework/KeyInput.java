package pacman.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import pacman.game.Pacman;
import pacman.objects.Player;

/**
 * Luokka huolehtii käyttäjän näppäinpainalluksista, joilla pelaajaa ohjataan.
 */
public class KeyInput extends KeyAdapter {
    private Player player;
    private Pacman game;
    
    /**
     * Kontruktori alustaa player -muuttujan.
     * @param player Pelaaja
     */
    public KeyInput(Player player, Pacman game) {
        this.player = player;
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            player.setNextDirection(Direction.LEFT);
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            player.setNextDirection(Direction.DOWN);
        } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            player.setNextDirection(Direction.RIGHT);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            player.setNextDirection(Direction.UP);
            
        } else if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) { //pause ja unpause
            if (!Pacman.gameOver) {
                Pacman.paused = !Pacman.paused;
            } else {
                game.restartGame();
            }
        } else if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
    
    public Player getPlayer() {
        return player;
    }
}
