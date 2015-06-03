package ShootEmUp_V2.Entity;

import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Sprite;

public class Door extends Entity {

    private Sprite sprite2;
    private float x2;

    public Door(int x, int y, String ref) {
        super(x, y, "resources/backgrounds/shutterLeft.jpg");
        this.sprite2 = ResourceFactory.get().getSprite("resources/backgrounds/shutterRight.jpg");
        this.x = 0;
        this.x2 = ResourceFactory.get().getGameWindow().getWidth() / 2;
        this.dx = 30;
    }

    @Override
    public void doLogic() {
    }

    public void doLogic(boolean safety) {

        //move doora based on safety status
        if (!safety) {
            if (x > -(ResourceFactory.get().getGameWindow().getWidth() / 2)) {
                x -= dx;
            }
            if (x2 < ResourceFactory.get().getGameWindow().getWidth()) {
                x2 += dx;
            }
        } else {
            if (x < 0) {
                x += dx;
            }
            if (x2 > ResourceFactory.get().getGameWindow().getWidth() / 2) {
                x2 -= dx;
            }
        }

        //bounds check
        if (x < -(ResourceFactory.get().getGameWindow().getWidth() / 2)) {
            x = -ResourceFactory.get().getGameWindow().getWidth() / 2;
        } else if (x > 0) {
            x = 0;
        }
        if (x2 < ResourceFactory.get().getGameWindow().getWidth() / 2) {
            x2 = ResourceFactory.get().getGameWindow().getWidth() / 2;
        } else if (x2 > ResourceFactory.get().getGameWindow().getWidth()) {
            x2 = ResourceFactory.get().getGameWindow().getWidth();
        }
    }

    public void draw() {
        sprite.drawToSize((int) x, 0, ResourceFactory.get().getGameWindow().getWidth() / 2, ResourceFactory.get().getGameWindow().getHeight());
        sprite2.drawToSize((int) x2, 0, ResourceFactory.get().getGameWindow().getWidth() / 2, ResourceFactory.get().getGameWindow().getHeight());
    }

}
