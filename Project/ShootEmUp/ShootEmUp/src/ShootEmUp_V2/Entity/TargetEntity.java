package ShootEmUp_V2.Entity;

import ShootEmUp_V2.GameState.GameState;
import ShootEmUp_V2.Util.SystemTimer;

public abstract class TargetEntity extends Entity {

    protected GameState game;//current gameState
    protected int width;//target width
    protected int height;//target height
    protected int value;//target value
    protected boolean dying;//target status to play explosioin animation
    protected boolean dead;//target status to remove from gamestate
    protected double startTime;//time of target creation
    protected double quickDraw = 0;//time between target creation and first shot

    //constructor method
    //@param game current gameState
    //@param x horizontal postion
    //@param y vertical postion
    //@param ref reference to target image sprite
    //@param value target point value
    public TargetEntity(GameState game, int x, int y, String ref, int value) {
        super(x, y, ref);

        this.value = value;
        this.game = game;
        width = sprite.getWidth();
        height = sprite.getHeight();

        startTime = SystemTimer.getTime();
    }

    //get target width
    //@return target width
    public int getWidth() {
        return width;
    }

    //set target width
    //@param desired target width
    public void setWidth(int width) {
        this.width = width;
    }

    //get target height
    //@return target height
    public int getHeight() {
        return height;
    }
    
    //set target height
    //@param desired target height
    public void setHeight(int height) {
        this.height = height;
    }

    //check dying status
    //@return true if dead
    public boolean isDead() {
        return dead;
    }

    //check dying statuc
    //@return true if dying
    public boolean isDying() {
        return dying;
    }

    //get point value
    //@return
    public int getValue() {
        return value;
    }

    //shoot target
    public void hit() {
        dying = true;
        if (quickDraw == 0) {
            quickDraw = SystemTimer.getTime() - startTime;
        }
    }

    //draw target with x/y coord at center point
    @Override
    public void draw() {
        if (!dying) {//draw explosion to size of target
            sprite.drawToSize((int) x - (width / 2), (int) y - (height / 2), width, height);
        } else {
            super.draw();
        }
    }

    //get quickdraw Time
    //@return quickdraw time
    public double quickDrawTime(){
        return quickDraw;
    }
    
    //check collission
    //@param px horizontal position of player
    //@param py vertical position of player
    abstract public boolean contains(int px, int py);

}
