package edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.GameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GameStateManager {

    private ArrayList<GameState> gameStates;
    private int currentState;

    //Gamestate index constants
    public static final int MENUSTATE = 0;
    public static final int LEVEL1STATE = 1;

    public GameStateManager() {
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        gameStates.add(new MenuState(this));

    }
    
    public void setState(int state){
        currentState = state;
        gameStates.get(currentState).init();
    }
    
    public void gameUpdate(){
        gameStates.get(currentState).gameUpdate();
    }
    public void gameRender(Graphics2D g){
        gameStates.get(currentState).gameRender(g);
    } 
    
    public void keyPressed(int k){
        gameStates.get(currentState).keyPressed(k);
    }
    public void keyReleased(int k){
        gameStates.get(currentState).keyReleased(k);
    }
}
