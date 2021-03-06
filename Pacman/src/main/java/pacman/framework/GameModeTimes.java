package pacman.framework;

import java.awt.Color;
import pacman.game.Pacman;
import pacman.objects.Ghost;

/**
 * Luokka tietää pelin tilan muuttumiseen tarvittavat ajankohdat ja tarjoaa metodit pelin tilan tarkistamiseen.
 */
public class GameModeTimes {
    
    /**
     * Metodi tarkistaa tuleeko pelitilan olla tällä päivityksellä scatter.
     * 
     * @param updates Missä vaiheessa peliä tällä hetkellä mennään
     * @return tuleeko tällä päivityksellä olla scatter vai ei
     */
    public static boolean getScatter(int updates) {
        double updatesPerSecond = Pacman.updatesPerSecond;
        if (updates <= updatesPerSecond * 7) { //7s
            return true;
        } else if (updates > updatesPerSecond * 7 && updates <= updatesPerSecond * 27) { //20s
            return false;
        } else if (updates > updatesPerSecond * 27 && updates <= updatesPerSecond * 34) { //7s
            return true;
        } else if (updates > updatesPerSecond * 34 && updates <= updatesPerSecond * 54) { //20s
            return false;
        } else if (updates > updatesPerSecond * 54 && updates <= updatesPerSecond * 59) { //5s
            return true;
        } else if (updates > updatesPerSecond * 59 && updates <= updatesPerSecond * 79) { //20s
            return false;
        } else if (updates > updatesPerSecond * 79 && updates <= updatesPerSecond * 84) { //5s
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Metodi tarkistaa milloin haamun tulee päästä häkistään ulos.
     * 
     * @param ghost Tarkistettava haamu
     * @param updates Missä vaiheessa peliä tällä hetkellä mennään
     * @return voiko haamun päästää jo häkistään ulos
     */
    public static boolean getGhostOut(Ghost ghost, int updates) {
        double updatesPerSecond = Pacman.updatesPerSecond;
        if (ghost.getColor() == Color.cyan) {
            if (updates > updatesPerSecond * 5) {
                return true;
            }
        } else if (ghost.getColor() == Color.orange) {
            if (updates > updatesPerSecond * 15) {
                return true;
            }
        } else {
            return true;
        }
        
        return false;
    }
}
