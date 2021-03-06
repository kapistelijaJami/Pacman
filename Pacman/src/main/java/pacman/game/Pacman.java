package pacman.game;

import pacman.ui.Window;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import pacman.framework.KeyInput;
import pacman.framework.GameModeTimes;
import pacman.framework.Tile;
import pacman.imagehandling.Textures;
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
    
    public static boolean paused = true; //Lähtee käyntiin Enteriä tai Välilyöntiä painamalla
    public static boolean gameOver = false;
    
    private Thread thread;
    private boolean running = false;
    private String mapPath = "/originalMap.png"; //Vaihtoehdot: "/originalMap.png", "/msPacmanMap.png", "/omaMap.png"
    
    private Level level;
    private JFrame frame;
    
    private Player player;
    private GhostHandler ghostHandler;
    
    public static double updatesPerSecond = 60.0;
    private int gameUpdates;
    
    public static int frightenedTime = (int) updatesPerSecond * 7; //päivitykset * sekunnit
    private boolean scatter = true;
    
    private BufferedImage lives;
    

    /**
     * Konstruktorissa tehdään olio hallitsemaan haamuja.
     */
    public Pacman() {
        ghostHandler = new GhostHandler();
        
        Textures textures = new Textures();
        textures.getPlayerImages();
        this.lives = textures.getPlayerImage(4);
    }
    
    /**
     * Metodi tekee ikkunan.
     */
    public void createWindow() {
        String operatingSystem = System.getProperty("os.name");
        
        int moreX = 6;
        int moreY = 29;
        
        if (operatingSystem.equals("Linux")) {
            moreX = 0;
            moreY = 0;
        }
        
        moreY += 40; //+40px korkeuteen, koska alas tulee pisteet ainakin
        
        Window window = new Window(WIDTH + moreX, HEIGHT + moreY, "Pacman", this); //joutui lisäämään +6 ja +29, koska ikkuna ei ollut oikean kokoinen jostain syystä
        this.frame = window.getFrame();
    }
    
    public void setPlayer(Player player) { //tehdään pelaaja Level.java luokasta
        this.player = player;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    /**
     * Metodi odottaa parametrinaan saavan millisekuntien verran.
     * @param milliseconds odotusaika
     */
    public void sleep(int milliseconds) {
        try {
            thread.sleep(milliseconds);
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Metodi asettaa haamun indeksin osoittamalle paikalle ghostHandler -oliossa olevaan taulukkoon.
     * @param i Indeksi, johon haamu laitetaan
     * @param ghost Haamu, joka laitetaan taulukkoon
     */
    public void setGhost(int i, Ghost ghost) {
        ghostHandler.setGhost(i, ghost);
    }
    
    public GhostHandler getGhostHandler() {
        return this.ghostHandler;
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public boolean getScatter() {
        return this.scatter;
    }
    
    /**
     * Metodi tarkistaa tuleeko pelitilan olla tällä hetkellä scatter vai ei.
     */
    public void checkScatter() {
        boolean newScatter = GameModeTimes.getScatter(gameUpdates);
        if (scatter != newScatter) {
            ghostHandler.oppositeDirection();
        }
        scatter = newScatter;
    }
    
    /**
     * Metodi suorittaa tarvittavat operaatiot kun pacman kuolee.
     */
    public void killPacman() {
        player.setLives(player.getLives() - 1);
        
        if (player.getLives() <= 0) {
            gameOver = true;
            paused = true;
            gameUpdates = 0;
            return;
        }
        
        sleep(1000);
        
        resetPositions();
        
        gameUpdates = 0;
        paused = true;
    }
    
    /**
     * Nollaa pelaajan ja haamujen sijainnit kentällä.
     */
    public void resetPositions() {
        player.resetPosition();
        ghostHandler.resetPositions();
        
        player.reset();
        ghostHandler.resetGhosts();
    }
    
    /**
     * Nollaa kentän, pelaajan ja haamut alkutilaan, mutta ei muuta pisteitä, eikä elämiä.
     */
    public void resetLevel() {
        sleep(1000);
        level.resetFoods();
        resetPositions();
        gameUpdates = 0;
        paused = true;
    }
    
    /**
     * Nollaa koko pelin alkutilaan. Pisteet ja elämätkin nollaantuu.
     */
    public void restartGame() {
        level.resetFoods();
        resetPositions();
        gameUpdates = 0;
        paused = true;
        
        gameOver = false;
        player.setLives(3);
        player.setPoints(0);
    }
    
    /**
     * Metodi tekee tason kuvasta ja luo kaikki peliobjektit, sekä keyListener.
     */
    public void init() {
        //tehdään taso kuvasta ja luodaan kaikki peliobjektit
        level = new Level(28, 31);
        level.loadLevelImage(mapPath);
        level.makeLevelFromImage(this);
        
        gameUpdates = 0;
        
        player.setGame(this); //annetaan pelaajalle peli
        ghostHandler.setGame(this); //annetaan haamuille peli, jota ne tarvitsee esim targetin etsintään
        
        KeyInput input = new KeyInput(player);
        input.setGame(this);
        this.addKeyListener(input); //luodaan keyListener
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
        double nanoSecondsPerUpdate = 1000000000 / updatesPerSecond; //päivitysten väliin tarvittavat nanosekunnit
        double delta = 0; //nanosekuntien määrä suhteessa päivitykseen tarvittaviin nanosekunteihin, kun 1, niin tehdään päivitys
        
        int updates = 0; //päivitysten määrä sekunnissa
        int frames = 0; //renderöintien määrä sekunnissa
        
        while (running) { //pelisilmukka
            /*update();
            render();*/
            long now = System.nanoTime();
            delta += (now - lastTime) / nanoSecondsPerUpdate; //deltaan lisätään loopiin kuluneet nanosekunnit per päivitykseen tarvittavat nanosekunnit
            lastTime = now;
            
            while (delta >= 1) { //deltan arvo on 1 kun on mennyt 1/updatesPerSecond sekuntia (eli 1/60)
                update(); //päivitetään peliä
                updates++;
                render();
                frames++;
                delta--; //deltan arvoa miinustetaan yhdellä (eli putoaa hyvin lähelle lukua 0)
            }
            
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
     * Metodi päivittää pelin tilannetta jokaiselle peliobjektille.
     */
    private void update() {
        if (!paused) {
            checkScatter();
            ghostHandler.checkGetOut(level, gameUpdates);
            
            ghostHandler.update(level);
            player.update(level);
            
            gameUpdates++;
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
        g.fillRect(0, 0, WIDTH, HEIGHT + 40); //musta tausta
        
        level.render(g);
        ghostHandler.render(g);
        player.render(g);
        
        g.setColor(Color.yellow);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Pisteet: " + player.getPoints(), 192, 764);
        
        for (int i = 0; i < player.getLives() - 1; i++) {
            g.drawImage(lives, 24 + (48 * i), 740, null);
        }
        
        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 22));
            g.drawString("Game Over!", 275, 425);
        }
        
        //piirto loppuu tähän//
        g.dispose();
        bs.show();
    }
}
