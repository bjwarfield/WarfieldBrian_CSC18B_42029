package ShootEmUp_V2.Entity;

import ShootEmUp_V2.GameState.GameState;
import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Sprite;
import ShootEmUp_V2.Util.SystemTimer;
import java.awt.Rectangle;

public class bodyTarget extends TargetEntity {

    private double lastFrameChange = 0;
    private int expFrame = 0;
    private Sprite[] explode;
    private Sprite[] targetFrames;
    private int currentFrame = 0;
    private boolean reveal = false;

    public bodyTarget(GameState game, int x, int y, String ref, int value) {
        super(game, x, y, "resources/sprites/target/bodyTarget.png", value);

        //parse spritesheet for darget
        targetFrames = new Sprite[3];
        int frameWidth = sprite.getWidth() / targetFrames.length;
        int frameHeight = sprite.getHeight();

        for (int i = 0; i < targetFrames.length; i++) {
            targetFrames[i] = ResourceFactory.get().getSubSprite(
                    sprite.getRef(),
                    frameWidth * i,
                    0,
                    frameWidth,
                    frameHeight);
        }

        sprite = targetFrames[currentFrame];

        //get and parse spritesheet for explosion
        Sprite temp = ResourceFactory.get().getSprite("resources/sprites/effects/break.png");

        explode = new Sprite[9];
        int colSize = temp.getWidth() / explode.length;
        int rowSize = temp.getHeight();

        for (int i = 0; i < explode.length; i++) {
            explode[i] = ResourceFactory.get().getSubSprite(
                    temp.getRef(),
                    colSize * i,
                    0,
                    colSize,
                    rowSize);
        }
    }

    @Override
    public boolean contains(int px, int py) {
        if(reveal){
            if(new Rectangle((int)(width*0.26),(int)y,(int)(width*0.52),(int)(height*0.22)).contains(px, py) ||new Rectangle((int)x,(int)(height*0.22),width,(int)(height*0.78)).contains(px, py)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void doLogic() {
        if (SystemTimer.getTime() - lastFrameChange > 0.04) {
            if (dying) {
                sprite = explode[expFrame < explode.length ? expFrame : explode.length - 1];
                expFrame++;
                lastFrameChange = SystemTimer.getTime();
                if (expFrame >= explode.length) {
                    dead = true;
                }
            }else if(reveal && currentFrame <2){
                currentFrame++;
                sprite = targetFrames[currentFrame];
            }else if(!reveal && currentFrame>0){
                currentFrame--;
                sprite = targetFrames[currentFrame];
            }
        }
    }

    
    public void setReveal(boolean reveal) {
        this.reveal = reveal;
    }

}
