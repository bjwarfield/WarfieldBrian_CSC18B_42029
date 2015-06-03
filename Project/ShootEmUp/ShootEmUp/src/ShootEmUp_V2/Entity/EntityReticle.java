package ShootEmUp_V2.Entity;

import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Mouse;
import ShootEmUp_V2.Util.Sprite;
import ShootEmUp_V2.Util.SystemTimer;
import java.awt.Rectangle;

public class EntityReticle extends Entity {

    private Sprite[] frames;
    private int currentFrame;
    private double lastFrameChange = 0;
    private Sprite flash;

    int shot = 0;
    Sprite[] shoot = new Sprite[3];
    private double flashFrame;

    public EntityReticle(int x, int y, String ref) {
        super(0, 0, "blu".equals(ref) ? "resources/sprites/reticle/reticleblu.png" : "resources/sprites/reticle/reticlered.png");
        frames = new Sprite[8];

        int tileSize = sprite.getWidth() / 8;

        for (int i = 0; i < 8; i++) {
            frames[i] = ResourceFactory.get().getSubSprite(
                    sprite.getRef(),
                    i * tileSize,
                    0,
                    tileSize,
                    tileSize);

        }

        this.sprite = frames[0];

        //bullet flash
        Sprite tempSprite = ResourceFactory.get().getSprite("resources/sprites/reticle/shot.png");
        int bulFlashTileSize = tempSprite.getWidth()/3;
        for (int i = 0; i < 3; i++) {
            shoot[i] = ResourceFactory.get().getSubSprite(tempSprite.getRef(), i * bulFlashTileSize, 0, bulFlashTileSize, bulFlashTileSize);
        }
        flash = ResourceFactory.get().getSprite("resources/sprites/reticle/flash.png");

        this.hitBox = new Rectangle(x, y, 1, 1);
    }

    @Override
    public void doLogic() {
        if (SystemTimer.getTime() - lastFrameChange > 0.04) {
            currentFrame = (int) (((x + y) / 16) % 8);
            lastFrameChange = SystemTimer.getTime();
        }
        this.sprite = frames[currentFrame];

        hitBox.setLocation((int) x, (int) y);

    }

    @Override
    public void draw() {
        super.draw();

        if (shot == 3) {//15% chande to draw screen flash
            if (Math.random() > 0.75) {
                flash.drawToSize(0, 0, ResourceFactory.get().getGameWindow().getWidth(), ResourceFactory.get().getGameWindow().getHeight());
            }
        }

        //draw bullet flash
        if (shot > 0) {
            shoot[shot - 1].draw((int) (x - (shoot[shot - 1].getWidth() / 2)), (int) (y - (shoot[shot - 1].getHeight() / 2)));
            if (SystemTimer.getTime() - flashFrame > 0.04) {
                shot--;
                flashFrame = SystemTimer.getTime();
            }
        }
    }

    @Override
    public void move(Double delta) {
        if (Mouse.getPos().x >= 0) {
            this.x = Mouse.getPos().x;
            this.y = Mouse.getPos().y;
        }
    }

    public void shoot() {
        shot = 3;

    }

}
