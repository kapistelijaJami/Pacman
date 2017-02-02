package pacman.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import pacman.framework.KeyInput;
import pacman.objects.Player;

public class Pacman extends Canvas implements Runnable {
    
    public static final int WIDTH = 672;
    public static final int HEIGHT = 744;
    
    private Thread thread;
    private boolean running = false;
    
    public static Player player;
    
    public Pacman() {
        new Window(WIDTH, HEIGHT, "Pacman", this);
    }
    
    public void init() {
        player = new Player(WIDTH / 2, HEIGHT / 2); //luodaan pelaaja
        
        this.addKeyListener(new KeyInput(player)); //luodaan keyListener
    }
    
    public synchronized void start() {
        if (running) {
            return;
        }
        
        init(); //tehdään kartta ja luodaan peliobjektit ja keyListener
        this.requestFocus(); //ei tarvitse erikseen klikata ikkunaa jotta kontrollit toimisivat
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop() {
        try {
            thread.join(); //pysäyttää threadin
            running = false;
        } catch (Exception e) {
        }
    }

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
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
    
    private void update() {
        player.update();
    }
    
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
        
        player.render(g); //piirretään pelaaja
        //piirto loppuu tähän//
        g.dispose();
        bs.show();
    }
}
