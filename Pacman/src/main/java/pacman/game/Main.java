package pacman.game;

/**
 * Luokka käynnistää Pacman pelin.
 */
public class Main {
    /**
     * Metodi käynnistää pelin ja luo ikkunan.
     * @param args 
     */
    public static void main(String[] args) {
        Pacman game = new Pacman();
        game.createWindow();
        game.start();
    }
}
