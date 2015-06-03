package ShootEmUp_V2.GameState;

import ShootEmUp_V2.Entity.EntityReticle;
import ShootEmUp_V2.Users.Session;
import ShootEmUp_V2.Util.Font;
import ShootEmUp_V2.Util.Mouse;
import java.awt.Point;
import java.util.ArrayList;

public class GameStateManager {

    private final EntityReticle player = new EntityReticle(Mouse.getPos().x, Mouse.getPos().y, "red");
    private int health = 3;
    private int credits = 9;
    private int continues = 0;
    private Session session = null;

    //array list of possible gamestates
    private ArrayList<GameState> gameStates;
    
    //index of current state
    private int currentState;

    //Gamestate index constants
    public static final int MENUSTATE = 0;
    public static final int COURSESELECTORSTATE = 1;
    public static final int PRACTICE1_01 = 2;
    public static final int PRACTICE1_02 = 3;
    public static final int PRACTICE1_03 = 4;
    public static final int PRACTICE1_04 = 5;
    
    public static final Font yellowMenu = new Font("resources/fonts/BankGothic30Yellow.png", "/resources/fonts/BankGothic30.dat");
    //bitmap fonts
    public static final Font whiteMenu = new Font("resources/fonts/BankGothic30White.png", "/resources/fonts/BankGothic30.dat");


    //constructor method
    public GameStateManager() {
        gameStates = new ArrayList<GameState>();
        currentState = MENUSTATE;
        //add gamestates to arrayList in orcer of index constant
        gameStates.add(new MenuState(this));
        gameStates.add(new CourseSelectorState(this));
        gameStates.add(new Practice1_1(this));

    }

    //change gamestate
    //@param index of gamestate in array list (use class constants)
    public void setState(int state) {
        currentState = state;//set current state
        gameStates.get(currentState).init();//initiallize new state
    }
    //change gamestate w/o initialization
    //@param index of gamestate in array list (use class constants)
    public void returnToState(int state) {
        currentState = state;//set current state
    }

    //get gameState array List
    //@return gamestate array 
    public ArrayList<GameState> getGameStates() {
        return gameStates;
    }

    //get player entity
    //@return EntityReticle instance
    public EntityReticle getPlayer() {
        return player;
    }

    //get player health
    //@returns the curent player health
    public int getHealth() {
        return health;
    }
    
    //get game credits
    //@returns game credits
    public int getCredits() {
        return credits;
    }

    public void setHealth(int health) {
        this.health = health>=0?health:0;
    }

    public void setCredits(int credits) {
        this.credits = credits>=0?credits:0;
    }

    public int getContinues() {
        return continues;
    }

    public void setContinues(int continues) {
        this.continues = continues>=0?continues:0;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
    
     
    
    //pass update call to current gamestate
    //@param delta timing vector
    public void gameUpdate(double delta) {
        gameStates.get(currentState).gameUpdate(delta);
    }

    //pass render call to current gamestate
    //@param game graphics context
    public void gameRender() {
        gameStates.get(currentState).gameRender();
    }

    //pass keypress to curent gamestate
    //@param keypress keycode
    public void keyPressed(int k) {
        gameStates.get(currentState).keyPressed(k);
    }

    //pass keyrelease to curent gamestate
    //@param keyrelease keycode
    public void keyReleased(int k) {
        gameStates.get(currentState).keyReleased(k);
    }

    //pass keytyped to curent gamestate
    //@param keytyped keycode
    public void keyTyped(int k) {
        gameStates.get(currentState).keyTyped(k);
    }

    public void mousePressed(int button) {
        gameStates.get(currentState).mousePressed(button);
    }

    public void mouseMoved(Point point) {
        gameStates.get(currentState).mouseMoved(point);
    }
}
