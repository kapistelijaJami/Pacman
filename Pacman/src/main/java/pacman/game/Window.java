package pacman.game;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window {
    public Window(int width, int height, String title, Pacman game) {
        JFrame frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension(width, height)); //laitetaan ikkunan koko
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ikkuna sulkeutuu rastista
        frame.setResizable(false); //ikkunaa ei voida venyttää
        frame.setLocationRelativeTo(null); //ikkuna syntyy näytön keskelle
        frame.add(game); //lisätään peli ikkunaan
        frame.pack(); //pakataan kaikki
        frame.setVisible(true); //laitetaan ikkuna näkyväksi
        
        game.start();
    }
}
