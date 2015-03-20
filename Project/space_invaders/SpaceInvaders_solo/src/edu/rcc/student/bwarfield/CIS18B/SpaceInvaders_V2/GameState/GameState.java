package edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.GameState;

import java.awt.Graphics2D;

public abstract class GameState {
    protected GameStateManager gsm;
    public abstract void init();
    public abstract void gameUpdate();
    public abstract void gameRender(Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
