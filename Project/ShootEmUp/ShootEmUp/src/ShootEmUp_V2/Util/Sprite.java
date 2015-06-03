
package ShootEmUp_V2.Util;

import java.awt.Image;

public interface Sprite {

    //get width of the drawn sprite
    //@return int width in pixels of the sprite
    public int getWidth();

    //get the height of the drawn sprite
    //@return int height in pixels
    public int getHeight();
    
    //get sprite image
    //@returns the sprite image
    public Image getImage();
    
    //get sprite image reference
    //@return sprite image path reference
    public String getRef();
    
    //draw the sprite onto the graphics context provided
    //@param x horizontal coodinate
    //@param y vertical coordinate
    public void draw(int x, int y);

     //draw the sprite scales to the given dimensions onto the graphics context provided
    //@param x horizontal coodinate
    //@param y vertical coordinate
    //@param width target image width
    //@param haight target image height
    public void drawToSize(int x, int y, int width, int height);

}
