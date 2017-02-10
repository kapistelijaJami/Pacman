package pacman.game;

/**
 * Luokka käynnistää Pacman pelin.
 */
public class Main {
    public static void main(String[] args) {
        Pacman game = new Pacman();
        game.createWindow();
        game.start();
    }
}
