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

    public Ghost(int x, int y) {
        super(x, y);
        
        width = 24;
        height = 24;
        
        velocity = 3;
        
        this.direction = Direction.LEFT;
        
        frightened = false;
        dead = false;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setGame(Pacman game) {
        this.game = game;
    }
    
    public void extendFrightened() {
        setDirection(getDirection().opposite());
        frightenedCounter = 0;
        frightened = true;
        velocity = 2;
    }
    
    public void updateFrightened() {
        if (frightenedCounter >= Pacman.frightenedTime) {
            velocity = 3;
            frightened = false;
            frightenedCounter = 0;
        } else {
            frightenedCounter++;
        }
    }
    
    public Tile getTargetTile(Tile[][] tiles) {
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
    
    public void decideDirectionFrightened(Tile[][] tiles) {
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
    
    public void decideDirection(Tile[][] tiles) {
        if (frightened) {
            decideDirectionFrightened(tiles);
            return;
        }
        HashMap<Direction, Double> distances = new HashMap<>();
        
        Tile targetTile = getTargetTile(tiles);
        
        int centerCoordX = getCenterCoordX();
        int centerCoordY = getCenterCoordY();
        
        //UP
        int tileX = centerCoordX;
        int tileY = centerCoordY - Pacman.TILE_HEIGHT;
        
        if (tileY >= 0) {
            Tile tileUp = tiles[tileY / Pacman.TILE_HEIGHT][tileX / Pacman.TILE_WIDTH];
            if (!tileUp.isWall()) {
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
    
    public void collision(Level level) {
        Tile[][] tiles = level.getTiles();
        
        Player player = game.getPlayer();
        Tile playerTile = tiles[player.getCenterCoordY() / Pacman.TILE_HEIGHT][player.getCenterCoordX() / Pacman.TILE_WIDTH];
        
        Tile ghostTile = tiles[getCenterCoordY() / Pacman.TILE_HEIGHT][getCenterCoordX() / Pacman.TILE_WIDTH];
        
        if (playerTile == ghostTile) {
            if (frightened) {
                dead = true;
                frightened = false;
            } else {
                game.setPaused(true);
            }
        }
    }

    @Override
    public void update(Level level) {
        if (dead) { //vain silmät, tee jotain muuta
            return;
        }
        Tile[][] tiles = level.getTiles();
        
        if (frightened) {
            updateFrightened();
        }
        
        if (isPossibleToTurn()) { //päätä seuraava suunta
            decideDirection(tiles);
        }
        
        //LIIKKUMINEN JA TÖRMÄÄMINEN
        moveAndCollide(tiles);
        
        collision(level);
    }

    @Override
    public void render(Graphics g) {
        if (dead) { //vain silmät
            return;
        }
        g.setColor(color);
        if (frightened) {
            g.setColor(Color.blue);
        }
        //g.fillRect(x, y, width, height); //testitarkoitukseen
        g.fillRect(x - width / 2 + 4, y - height / 2 + 4, width * 2 - 8, height * 2 - 8); //oikean kokoinen Ghost
    }
}
