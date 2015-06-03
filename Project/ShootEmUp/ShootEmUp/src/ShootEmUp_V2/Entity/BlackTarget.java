package ShootEmUp_V2.Entity;

import ShootEmUp_V2.GameState.GameState;
import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Sprite;
import ShootEmUp_V2.Util.SystemTimer;

public class BlackTarget extends TargetEntity {

    private double lastFrameChange = 0;
    private int expFrame = 0;

    private final Sprite[] explode;

    
    public BlackTarget(GameState game, int x, int y, String ref, int value) {
        super(game, x, y, "resources/sprites/target/black.png", value);
        Sprite temp = ResourceFactory.get().getSprite("resources/sprites/effects/break.png");
        
        explode = new Sprite[9];
        int colSize = temp.getWidth()/explode.length;
        int rowSize = temp.getHeight();
        
        for (int i=0; i<explode.length; i++){
            explode[i] = ResourceFactory.get().getSubSprite(
                    temp.getRef(),
                    colSize*i,
                    0,
                    colSize,
                    rowSize);
        }
        

    }

    @Override
    public void doLogic() {
       if(dying){
           if (SystemTimer.getTime()-lastFrameChange > 0.04){
               sprite = explode[expFrame< explode.length?expFrame:explode.length-1];
               expFrame++;
               lastFrameChange = SystemTimer.getTime();
               if (expFrame >= explode.length){
                   dead = true;
               }
           }
       }
        
    }

    
    
    @Override
    public boolean contains(int px, int py) {
        return width/2 > Math.sqrt((px-x)*(px-x)+(py-y)*(py-y));
    }

}
