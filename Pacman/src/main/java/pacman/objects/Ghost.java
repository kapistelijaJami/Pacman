/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Random;
import pacman.framework.Direction;
import pacman.framework.GameObject;
import pacman.framework.Tile;
import pacman.game.Level;
import pacman.game.Pacman;
import pacman.imagehandling.Animation;
import pacman.imagehandling.Textures;

/**
 * Luokka tarjoaa metodeita haamujen ohjaamisen ja törmäämisen hallintaan,
 * sekä pitää kirjaa pelaajan sijainnista ja suunnasta.
 */
public class Ghost extends GameObject {
    
    private Color color;
    private Pacman game;
    
    private boolean frightened;
    private int frightenedCounter;
    private boolean dead;
    
    private boolean insideCage; //häkin sisällä
    private boolean getOut; //haluaa häkistä ulos
    
    private Animation animation;
    private Animation animationFrightened;

    /**
     * Alustetaan muuttujat.
     * @param x X-koordinaatti alussa
     * @param y Y-koordinaatti alussa
     */
    public Ghost(int x, int y) {
        super(x, y);
        
        width = 24;
        height = 24;
        
        velocity = 3;
        
        this.direction = Direction.LEFT;
        
        frightened = false;
        dead = false;
        getOut = false;
        insideCage = true;
    }
    
    public void setColor(Color color) {
        this.color = color;
        
        Textures textures = new Textures();
        textures.getGhostImages();
        
        int speed = 5;
        
        if (color == Color.red) {
            this.animation = new Animation(speed, textures.getGhostImage(0), textures.getGhostImage(1));
        } else if (color == Color.pink) {
            this.animation = new Animation(speed, textures.getGhostImage(2), textures.getGhostImage(3));
        } else if (color == Color.cyan) {
            this.animation = new Animation(speed, textures.getGhostImage(4), textures.getGhostImage(5));
        } else if (color == Color.orange) {
            this.animation = new Animation(speed, textures.getGhostImage(6), textures.getGhostImage(7));
        }
        
        this.animationFrightened = new Animation(speed, textures.getGhostImage(8), textures.getGhostImage(9));
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setGame(Pacman game) {
        this.game = game;
    }
    
    public void setInsideCage(boolean inside) {
        this.insideCage = inside;
    }
    
    public boolean getInsideCage() {
        return this.insideCage;
    }
    
    public void setGetOut(boolean getOut) {
        this.getOut = getOut;
    }
    
    public boolean getGetOut() {
        return this.getOut;
    }
    
    /**
     * Metodi tarkistaa onko haamu nykyisen kohde-Tilen kohdalla.
     * @param level Pelikenttä, jota käytetään targetin etsimisessä.
     * @return Onko haamu nykyisen kohdepalan kohdalla
     */
    public boolean isOnTargetTile(Level level) {
        Tile target = getTargetTile(level);
        Tile[][] tiles = level.getTiles();
        
        int ghostX = getCenterCoordX() / Pacman.TILE_WIDTH;
        int ghostY = getCenterCoordY() / Pacman.TILE_HEIGHT;
        
        if (target == tiles[ghostY][ghostX]) {
            return true;
        }
        
        return false;
    }
    
    public boolean isFrightened() {
        return frightened;
    }
    
    /**
     * Metodi vaihtaa haamun suuntaa, ja pidentää frightened tilaa.
     */
    public void extendFrightened() {
        setDirection(getDirection().opposite());
        frightenedCounter = 0;
        frightened = true;
        velocity = 2;
    }
    
    /**
     * Metodi tarkistaa tuleeko frightened tila poistaa, vai kasvatetaanko frightenedCounter -muuttujaa lisää.
     */
    public void updateFrightened() {
        if (frightenedCounter >= Pacman.frightenedTime) {
            velocity = 3;
            frightened = false;
            frightenedCounter = 0;
        } else {
            frightenedCounter++;
        }
    }
    
    /**
     * Metodi palauttaa haamun kohde-Tilen kun scatter -tila on aktiivisena.
     * @param level Pelikenttä, jota käytetään target-Tilen palauttamisessa.
     * @return Target-Tile
     */
    public Tile getTargetScatterTile(Level level) {
        Tile[][] tiles = level.getTiles();
        if (this.color == Color.red) {
            return tiles[0][tiles[0].length - 1];
        } else if (this.color == Color.pink) {
            return tiles[0][0];
        } else if (this.color == Color.cyan) {
            return tiles[tiles.length - 1][tiles[0].length - 1];
        } else {
            return tiles[tiles.length - 1][0];
        }
    }
    
    /**
     * Metodi palauttaa Tile -olion, joka on kohteena kun haamu haluaa ulos häkistä.
     * @param level Pelikenttä, jolta kysytään Tile -oliota
     * @return Tile -olio
     */
    public Tile getOutTargetTile(Level level) {
        return level.getGetOutTile();
    }
    
    /**
     * Metodi laskee haamulle kohde-Tilen, ja palauttaa sen.
     * @param level Pelikenttä, jota käytetään targetin laskemiseen
     * @return Tile -olio
     */
    public Tile getTargetTile(Level level) {
        if (getOut) {
            return getOutTargetTile(level);
        }
        
        if (game.getScatter()) {
            return getTargetScatterTile(level);
        }
        
        Tile[][] tiles = level.getTiles();
        
        Player player = game.getPlayer();
        Tile playerTile = tiles[player.getCenterCoordY() / Pacman.TILE_HEIGHT][player.getCenterCoordX() / Pacman.TILE_WIDTH];
        
        if (this.color == Color.red) { //Blinky
            return playerTile;
        } else if (this.color == Color.pink) { //Pinky
            Direction playerDir = player.getDirection();
            int playerX = player.getCenterCoordX() / Pacman.TILE_WIDTH;
            int playerY = player.getCenterCoordY() / Pacman.TILE_HEIGHT;
            
            if (playerDir == Direction.UP) {
                playerY = Math.max(0, playerY - 4);
            } else if (playerDir == Direction.LEFT) {
                playerX = Math.max(0, playerX - 4);
            } else if (playerDir == Direction.DOWN) {
                playerY = Math.min(tiles.length - 1, playerY + 4);
            } else if (playerDir == Direction.RIGHT) {
                playerX = Math.min(tiles[0].length - 1, playerX + 4);
            }
            
            return tiles[playerY][playerX];
        } else if (this.color == Color.cyan) { //Inky
            Ghost blinky = game.getGhostHandler().getGhost(0);
            int blinkyTileX = blinky.getCenterCoordX() / Pacman.TILE_WIDTH;
            int blinkyTileY = blinky.getCenterCoordY() / Pacman.TILE_HEIGHT;
            int playerTileX = player.getCenterCoordX() / Pacman.TILE_WIDTH;
            int playerTileY = player.getCenterCoordY() / Pacman.TILE_HEIGHT;
            
            //jos pacman oikealla, niin inky enemmän oikealle, jos pacman vasemmalla, niin inky vasemmalle
            int newX = Math.max(0, Math.min(tiles[0].length - 1, playerTileX + (playerTileX - blinkyTileX)));
            //samalla tavalla Y akselilla
            int newY = Math.max(0, Math.min(tiles.length - 1, playerTileY + (playerTileY - blinkyTileY)));
            
            return tiles[newY][newX];
        } else if (this.color == Color.orange) { //Clyde
            if (playerTile.distance(tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][getCenterCoordX() / Pacman.TILE_WIDTH]) <= 8) { //pelaaja lähempänä kuin 8 tilea
                return tiles[tiles.length - 1][0]; //vasen alakulma
            } else { //pelaaja yli 8 tilen päässä
                return playerTile;
            }
        }
        
        return tiles[tiles.length - 1][0]; //vasen alakulma
    }
    
    /**
     * Metodi päättää suunnan, johon liikutaan seuraavaksi, kun frightened tila on aktiivisena.
     * @param level Pelikenttä
     */
    public void decideDirectionFrightened(Level level) {
        Tile[][] tiles = level.getTiles();
        int centerCoordX = getCenterCoordX();
        int centerCoordY = getCenterCoordY();
        
        Random random = new Random();
        int number = 0;
        int i;
        for (i = 0; i < 100; i++) { //100 kertaa, jos ei löydy, ni ei vaihdeta
            number = random.nextInt(4); //0, 1, 2 tai 3, eli up, left, down, right
            if (number == direction.opposite().getCorrespondingNumber()) {
                continue; //ei haluta kääntyä taaksepäin, joten otetaan uusiksi
            }
            
            int tileX = centerCoordX;
            int tileY = centerCoordY;
            
            if (number == 0) { //UP
                tileY -= Pacman.TILE_HEIGHT;
            } else if (number == 1) { //LEFT
                tileX -= Pacman.TILE_WIDTH;
            } else if (number == 2) { //DOWN
                tileY += Pacman.TILE_HEIGHT;
            } else if (number == 3) { //RIGHT
                tileX += Pacman.TILE_WIDTH;
            }
            
            if (tileX >= 0 && tileY >= 0 && tileX < tiles[0].length * Pacman.TILE_WIDTH && tileY < tiles.length * Pacman.TILE_HEIGHT) {
                Tile tile = tiles[tileY / Pacman.TILE_HEIGHT][tileX / Pacman.TILE_WIDTH];
                if (tile.isWall()) {
                    continue; //seinä, joten otetaan uusiksi
                }
            } else {
                continue;
            }
            break;
        }

        if (i == 100) {
            return;
        }
        
        if (direction.opposite() == Direction.getCorrespondingDirection(number)) {
            System.out.println("");
        }
        direction = Direction.getCorrespondingDirection(number);
    }
    
    /**
     * Metodi päättää suunnan, johon liikutaan seuraavaksi.
     * @param level Pelikenttä
     */
    public void decideDirection(Level level) {
        if (frightened) {
            decideDirectionFrightened(level);
            return;
        }
        
        HashMap<Direction, Double> distances = new HashMap<>();
        
        Tile targetTile = getTargetTile(level);
        
        Tile[][] tiles = level.getTiles();
        
        int centerCoordX = getCenterCoordX();
        int centerCoordY = getCenterCoordY();
        
        //UP
        int tileX = centerCoordX;
        int tileY = centerCoordY - Pacman.TILE_HEIGHT;
        
        if (tileY >= 0) {
            Tile tileUp = tiles[tileY / Pacman.TILE_HEIGHT][tileX / Pacman.TILE_WIDTH];
            if (!tileUp.isWall() || getOut && tileUp.isGhostHatch()) { //jos ylhäällä on ghostHatch ja halutaan ulos, niin valitaan se suunta
                distances.put(Direction.UP, tileUp.distance(targetTile));
            }
        }
        
        //LEFT
        tileX = centerCoordX - Pacman.TILE_WIDTH;
        tileY = centerCoordY;
        
        if (tileX >= 0) {
            Tile tileLeft = tiles[tileY / Pacman.TILE_HEIGHT][tileX / Pacman.TILE_WIDTH];
            if (!tileLeft.isWall()) {
                distances.put(Direction.LEFT, tileLeft.distance(targetTile));
            }
        }
        
        //DOWN
        tileX = centerCoordX;
        tileY = centerCoordY + Pacman.TILE_HEIGHT;
        
        if (tileY < tiles.length * Pacman.TILE_HEIGHT) {
            Tile tileDown = tiles[tileY / Pacman.TILE_HEIGHT][tileX / Pacman.TILE_WIDTH];
            if (!tileDown.isWall()) {
                distances.put(Direction.DOWN, tileDown.distance(targetTile));
            }
        }
        
        //RIGHT
        tileX = centerCoordX + Pacman.TILE_WIDTH;
        tileY = centerCoordY;
        
        if (tileX < tiles[0].length * Pacman.TILE_WIDTH) {
            Tile tileRight = tiles[tileY / Pacman.TILE_HEIGHT][tileX / Pacman.TILE_WIDTH];
            if (!tileRight.isWall()) {
                distances.put(Direction.RIGHT, tileRight.distance(targetTile));
            }
        }
        
        if (distances.containsKey(this.direction.opposite())) {
            distances.remove(this.direction.opposite());
        }
        
        if (distances.isEmpty()) {
            return;
        }
        
        Direction smallestDistance = null;
        
        for (Direction dir : distances.keySet()) {
            if (smallestDistance == null) {
                smallestDistance = dir;
            } else {
                if (distances.get(smallestDistance) > distances.get(dir)) {
                    smallestDistance = dir;
                }
            }
        }
        
        this.direction = smallestDistance;
    }
    
    /**
     * Metodi tarkistaa törmääkö haamu pelaajan kanssa, ja muokkaa niiden tilaa tarvittaessa.
     * @param level Pelikenttä
     */
    public void collision(Level level) {
        Tile[][] tiles = level.getTiles();
        
        Player player = game.getPlayer();
        Tile playerTile = tiles[player.getCenterCoordY() / Pacman.TILE_HEIGHT][player.getCenterCoordX() / Pacman.TILE_WIDTH];
        
        Tile ghostTile = tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][getCenterCoordX() / Pacman.TILE_WIDTH];
        
        if (playerTile == ghostTile) {
            if (frightened) {
                dead = true;
                frightened = false;
                player.setPisteet(player.getPisteet() + 200);
            } else {
                game.setPaused(true);
            }
        }
    }
    
    public void runAnimation() {
        if (frightened) {
            animationFrightened.runAnimation();
        } else {
            animation.runAnimation();
        }
    }

    @Override
    public void update(Level level) {
        if (dead) { //vain silmät, tee jotain muuta
            return;
        }
        
        if (frightened) {
            updateFrightened();
        }
        
        if (isPossibleToTurn()) { //päätä seuraava suunta
            decideDirection(level);
        }
        
        //LIIKKUMINEN JA TÖRMÄÄMINEN
        moveAndCollide(level);
        
        //OSUUKO PELAAJAAN
        collision(level);
        
        runAnimation();
    }

    @Override
    public void render(Graphics g) {
        if (dead) { //vain silmät
            return;
        }
        
        int startX = x - width / 2 + 4;
        int startY = y - height / 2 + 4;
        
        if (frightened) {
            g.drawImage(animationFrightened.getCurrentImage(), startX, startY, null);
        } else {
            g.drawImage(animation.getCurrentImage(), startX, startY, null);
        }
        
        
        //g.fillRect(x, y, width, height); //testitarkoitukseen
        //g.fillRect(x - width / 2 + 4, y - height / 2 + 4, width * 2 - 8, height * 2 - 8); //oikean kokoinen Ghost
    }
}
