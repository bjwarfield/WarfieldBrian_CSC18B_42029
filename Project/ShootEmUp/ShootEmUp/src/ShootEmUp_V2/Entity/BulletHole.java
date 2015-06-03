
package ShootEmUp_V2.Entity;

import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Sprite;


public class BulletHole extends Entity {

    private final Sprite[] holes; 
    
    public BulletHole(int x, int y, String ref) {
        super(x, y, "resources/sprites/effects/bulletHole.png");
        
        holes = new Sprite[10];
        int colSize = sprite.getWidth()/holes.length;
        int rowSize = sprite.getHeight();
        
        for (int i=0; i<holes.length; i++){
            holes[i] = ResourceFactory.get().getSubSprite(
                    sprite.getRef(),
                    colSize*i,
                    0,
                    colSize,
                    rowSize);
        }
        
        sprite = holes[(int)(Math.random()*10)];
        
    }

    @Override
    public void doLogic() {
        
    }
    
}
