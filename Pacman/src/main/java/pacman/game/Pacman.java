package pacman.game;

import pacman.ui.Window;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import pacman.framework.KeyInput;
import pacman.objects.Ghost;
import pacman.objects.Player;

/**
 * Luokka luo Pacman pelin ja alustaa sen kentän, peli-ikkunan ja peliobjektit, sekä siirtyy pelisilmukkaan.
 * Pelisilmukasta kutsutaan update ja render metodeita, jotka päivittävät pelin ja piirtävät sen näkyville.
 */
public class Pacman extends Canvas implements Runnable {
    
    public static final int WIDTH = 672;
    public static final int HEIGHT = 744;
    public static final int TILE_WIDTH = 24;
    public static final int TILE_HEIGHT = 24;
    
    public static boolean paused = false; //Lähtee käyntiin Enteriä tai Välilyöntiä painamalla
    
    private Thread thread;
    private boolean running = false;
    
    private Level level;
    private JFrame frame;
    
    private Player player;
    private GhostHandler ghostHandler;
    
    public Pacman() {
        ghostHandler = new GhostHandler(); //tehdään olio hallitsemaan haamuja
    }
    
    public void createWindow() {
        Window window = new Window(WIDTH + 6, HEIGHT + 29, "Pacman", this); //joutui lisäämään +6 ja +29, koska ikkuna ei ollut oikean kokoinen jostain syystä
        this.frame = window.getFrame();
    }
    
    public void setPlayer(Player player) { //tehdään pelaaja Level.java luokasta
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void setGhost(int i, Ghost ghost) {
        ghostHandler.setGhost(i, ghost);
    }
    
    public GhostHandler getGhostHandler() {
        return this.ghostHandler;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    /**
     * Metodi tekee tason kuvasta ja luo kaikki peliobjektit, sekä keyListener.
     */
    public void init() {
        //tehdään taso kuvasta ja luodaan kaikki peliobjektit
        level = new Level(28, 31);
        level.loadLevelImage("/originalMap.png"); //Vaihtoehdot: "/originalMap.png", "/msPacmanMap.png", "/omaMap.png"
        level.makeLevelFromImage(this);
        
        this.addKeyListener(new KeyInput(player)); //luodaan keyListener
        running = true;
    }
    
    /**
     * Metodi käynnistää threadin ja kutsuu run -metodia.
     */
    public synchronized void start() {
        if (running) {
            return;
        }
        
        init(); //tehdään kartta ja luodaan peliobjektit ja keyListener
        this.requestFocus(); //fokusoi ikkunan ettei tarvitse erikseen klikata ikkunaa jotta kontrollit toimisivat
        
        thread = new Thread(this);
        thread.start();
    }
    
    /**
     * Metodi pysäyttää threadin.
     */
    public synchronized void stop() {
        try {
            thread.join(); //pysäyttää threadin
            running = false;
        } catch (Exception e) {
        }
    }
    
    /**
     * Metodi alustaa pelisilmukan muuttujat ja käynnistää silmukan,
     * jossa kutsutaan update ja render -metodeita.
     */
    @Override
    public void run() {
        long timer = System.currentTimeMillis();
        long lastTime = System.nanoTime();
        double updatesPerSecond = 60.0;
        double nanoSecondsPerUpdate = 1000000000 / updatesPerSecond; //päivitysten väliin tarvittavat nanosekunnit
        double delta = 0; //nanosekuntien määrä suhteessa päivitykseen tarvittaviin nanosekunteihin, kun 1, niin tehdään päivitys
        
        int updates = 0; //päivitysten määrä sekunnissa
        int frames = 0; //renderöintien määrä sekunnissa
        
        while (running) { //pelisilmukka
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSecondsPerUpdate; //deltaan lisätään loopiin kuluneet nanosekunnit per päivitykseen tarvittavat nanosekunnit
            lastTime = now;
            
            while (delta >= 1) { //deltan arvo on 1 kun on mennyt 1/updatesPerSecond sekuntia (eli 1/60)
                update(); //päivitetään peliä
                updates++;
                delta--; //deltan arvoa miinustetaan yhdellä (eli putoaa hyvin lähelle lukua 0)
            }
            render();
            frames++;
            
            if (System.currentTimeMillis() - timer > 1000) { //jos on mennyt sekunti viime kerrasta (tänne pääsee siis sekunnin välein)
                timer += 1000; //timer jahtaa currentTimeMillis funktiota
                System.out.println("Updates: " + updates + ", Frames: " + frames); //näitä voi käyttää esim titlessä
                this.frame.setTitle("Pacman " + "Updates: " + updates + ", Frames: " + frames);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
    
    /**
     * Metodi päivittää pelin tilannetta.
     */
    private void update() {
        if (!paused) {
            player.update(level);
        }
    }
    
    /**
     * Metodi piirtää kentän ja peliobjektit.
     */
    private void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        
        Graphics g = bs.getDrawGraphics();
        //piirrä tässä//
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT); //musta tausta
        
        level.render(g); //piirretään leveli
        ghostHandler.render(g); //piirretään haamut
        player.render(g); //piirretään pelaaja
        //piirto loppuu tähän//
        g.dispose();
        bs.show();
    }
}
