package pacman.game;

import java.awt.Graphics;
import pacman.framework.GameModeTimes;
import pacman.objects.Ghost;
import pacman.objects.Player;

/**
 * Luokka huolehtii jokaisesta haamusta pelissä, ja tarjoaa niiden tilan muuttamiseen metodeita.
 */
public class GhostHandler {
    private Ghost[] ghosts;
    
    /**
     * Konstruktori luo taulukon, johon asetetaan haamut.
     */
    public GhostHandler() {
        ghosts = new Ghost[4];
    }
    
    /**
     * Metodi palauttaa taulukosta haamun parametrina saadun indeksin perusteella.
     * @param i Indeksi, jolla haetaan haamu
     * @return Haamu kyseisestä indeksistä
     */
    public Ghost getGhost(int i) {
        if (i < 0 || i >= ghosts.length) {
            return null;
        }
        return ghosts[i];
    }
    
    /**
     * Metodi asettaa haamun indeksin osoittamalle paikalle taulukkoon.
     * @param i Indeksi, johon haamu laitetaan
     * @param ghost Haamu, joka laitetaan taulukkoon
     */
    public void setGhost(int i, Ghost ghost) {
        if (i < 0 || i >= ghosts.length) {
            return;
        }
        ghosts[i] = ghost;
    }
    
    public Ghost[] getGhosts() {
        return ghosts;
    }
    
    /**
     * Metodi asettaa pelin jokaiselle haamulle.
     * @param game Pacman -olio
     */
    public void setGame(Pacman game) {
        for (Ghost ghost : ghosts) {
            ghost.setGame(game);
        }
    }
    
    /**
     * Metodi kutsuu jokaisen haamun extendFirghtened() -metodia.
     */
    public void extendFrightened() {
        for (Ghost ghost : ghosts) {
            ghost.extendFrightened();
        }
    }
    
    /**
     * Metodi asettaa jokaisen haamun suunnaksi vastakkaisen suunnan.
     */
    public void oppositeDirection() {
        for (Ghost ghost : ghosts) {
            if (!ghost.isFrightened()) {
                ghost.setDirection(ghost.getDirection().opposite());
            }
        }
    }
    
    /**
     * Metodi muuttaa haamun getOut -muuttujaa sen mukaan kuinka paljon aikaa on kulunut, ja missä haamu sijaitsee.
     * @param level Pelikenttä, jota käytetään haamun sijainnin tarkistamiseen
     * @param updates Missä vaiheessa peliä tällä hetkellä mennään
     */
    public void checkGetOut(Level level, int updates) {
        for (Ghost ghost : ghosts) {
            if (ghost.getGetOut()) {
                if (ghost.isOnTargetTile(level)) {
                    ghost.setGetOut(false);
                    ghost.setInsideCage(false);
                }
            }
            
            if (ghost.getInsideCage()) {
                ghost.setGetOut(GameModeTimes.getGhostOut(ghost, updates));
            }
        }
    }
    
    /**
     * Metodi kutsuu jokaisen haamun update() -metodia.
     * @param level Pelikenttä, jota käytetään liikkumisessa ja muissakin törmäämisessä
     */
    public void update(Level level) {
        for (Ghost ghost : ghosts) {
            ghost.update(level);
        }
    }
    
    /**
     * Metodi kutsuu jokaisen haamun render() -metodia.
     * @param g Graphics objekti, jolla piirretään peliobjekti
     */
    public void render(Graphics g) {
        for (Ghost ghost : ghosts) {
            ghost.render(g);
        }
    }
}
