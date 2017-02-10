/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.objects;

import java.awt.Color;
import java.awt.Graphics;
import pacman.framework.Direction;
import pacman.framework.GameObject;
import pacman.game.Level;

/**
 * Luokka tarjoaa metodeita haamujen ohjaamisen ja törmäämisen hallintaan,
 * sekä pitää kirjaa pelaajan sijainnista ja suunnasta.
 */
public class Ghost extends GameObject {
    
    private Color color;

    public Ghost(int x, int y) {
        super(x, y);
        
        width = 24;
        height = 24;
        
        velocity = 3;
        
        this.direction = Direction.LEFT;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }

    @Override
    public void update(Level level) {
        
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        //g.fillRect(x, y, width, height); //testitarkoitukseen
        g.fillRect(x - width / 2 + 4, y - height / 2 + 4, width * 2 - 8, height * 2 - 8); //oikean kokoinen Ghost
    }
}
