package ShootEmUp_V2.GameState;

import java.awt.Point;
import java.util.ArrayList;

public abstract class GameState {

    protected GameStateManager gsm;

    public abstract void init();

    public abstract void gameUpdate(double delta);

    public abstract void gameRender();

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);

    public abstract void keyTyped(int k);

    public abstract void mousePressed(int button);

    public abstract void mouseMoved(Point point);

    //list of game entities
    private final ArrayList entities = new ArrayList();

    public ArrayList getEntities() {
        return entities;
    }

    private final ArrayList removeEntityList = new ArrayList();

    public ArrayList getRemoveEntityList() {
        return removeEntityList;
    }

}
