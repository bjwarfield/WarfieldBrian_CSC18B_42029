
package edu.rcc.student.bwarfield.cis18b.ShootEmUp_v1.GameState;

//abstract class for gamestates

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class GameState {

    protected GameStateManager gsm;

    public abstract void init();

    public abstract void gameUpdate(long delta);

    public abstract void gameRender(Graphics2D g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public abstract void keyTyped(int k);
    
    public abstract void mousePressed(MouseEvent e) ;

    public abstract void mouseReleased(MouseEvent e) ;

    public abstract void mouseMoved(MouseEvent e) ;

    // The list of all the entities
    private ArrayList entities = new ArrayList();

    public ArrayList getEntities() {
        return entities;
    }

}

