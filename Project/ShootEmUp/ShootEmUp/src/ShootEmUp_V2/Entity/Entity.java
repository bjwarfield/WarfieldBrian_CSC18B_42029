package ShootEmUp_V2.Entity;

import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Sprite;
import java.awt.Rectangle;

public abstract class Entity {

    //current entity coortinates
    protected float x;//horizontal
    protected float y;//vetrical
    protected Sprite sprite;//sprite for entity
    //speed of entity in pix/sec
    protected float dx;//horizontal speed
    protected float dy;//vertical speed

    //collision detection variables
    protected Rectangle hitBox = new Rectangle();

    //class constructor
    //@param ref string refernece to sprite image for theis entity
    //@param x initial x location
    //@param y initial y location
    public Entity(int x, int y, String ref) {
        this.sprite = ResourceFactory.get().getSprite(ref);
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(
                (int) x - (sprite.getWidth() / 2),
                (int) y - (sprite.getHeight() / 2),
                sprite.getWidth(),
                sprite.getHeight()
        );
    }

        //Get the x location of this entity
    //@return x location
    public float getX() {
        return x;
    }

    //set x location for this entity
    //@param x location
    public void setX(float x) {
        this.x = x;
    }

    //Get the y location of this entity
    //@return y location
    public float getY() {
        return y;
    }

    //set y location for this entity
    //@param y location
    public void setY(float y) {
        this.y = y;
    }

    //set horizontal speed
    //@param dx speed in pixels/sec
    public void setHorizontalMovement(float dx) {
        this.dx = dx;
    }

    //set vertical speed
    //@param dy speed in pixels/sec
    public void setVerticalMovement(float dy) {
        this.dy = dy;
    }

    //get Horizontal speed
    //@return speed in pixels/sec
    public float getHorizontalMovement() {
        return dx;
    }

    //get vertical speed
    //@return speed in pixels/sec
    public float getVerticalMovement() {
        return dy;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
    
    //move method based on timng system
    //@param delta the amount of time passed in milliseconds
    public void move(Double delta) {
        //update location based on move speed
        x += (delta * dx);//shift horizontally
        y += (delta * dy);//shift vertically

    }
    
    //Draw this entity to the graphics context provided
    //@param g The graphics context on which to draw
    public void draw() {
        //center sprite on x/y coords
        sprite.draw((int) x - (sprite.getWidth() / 2), (int) y - (sprite.getHeight() / 2));

    }

    //Do the logic associated with this entity.
    abstract public void doLogic();



}
