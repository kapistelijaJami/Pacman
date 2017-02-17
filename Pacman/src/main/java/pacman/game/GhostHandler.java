package pacman.game;

import java.awt.Graphics;
import pacman.objects.Ghost;
import pacman.objects.Player;

public class GhostHandler {
    private Ghost[] ghosts;
    
    public GhostHandler() {
        ghosts = new Ghost[4];
    }
    
    public Ghost getGhost(int i) {
        if (i < 0 || i >= ghosts.length) {
            return null;
        }
        return ghosts[i];
    }
    
    public void setGhost(int i, Ghost ghost) {
        if (i < 0 || i >= ghosts.length) {
            return;
        }
        ghosts[i] = ghost;
    }
    
    public Ghost[] getGhosts() {
        return ghosts;
    }
    
    public void setGame(Pacman game) {
        for (Ghost ghost : ghosts) {
            ghost.setGame(game);
        }
    }
    
    public void extendFrightened() {
        for (Ghost ghost : ghosts) {
            ghost.extendFrightened();
        }
    }
    
    public void update(Level level) {
        for (Ghost ghost : ghosts) {
            ghost.update(level);
        }
    }
    
    public void render(Graphics g) {
        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }
    }
}
