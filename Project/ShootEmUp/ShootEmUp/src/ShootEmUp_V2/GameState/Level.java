package ShootEmUp_V2.GameState;

import ShootEmUp_V2.Entity.BulletHole;
import ShootEmUp_V2.Entity.Door;
import ShootEmUp_V2.Entity.TargetEntity;
import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Util.Background;
import ShootEmUp_V2.Util.Font;
import ShootEmUp_V2.Util.Sprite;
import java.util.ArrayList;

public abstract class Level extends GameState {

    protected boolean shooting = false;
    protected final double shotDelay = 0.05;
    protected double lastShot = 0;
    protected int shotsFired;
    protected int shotsHit;
    protected int objective;
    protected int objectivePoints;
    protected boolean shotLimit;
    protected int bullets;
    protected int hitScore;
    protected int hitAccScore;
    protected double quickHit = 2;
    protected int quickHitScore;

    protected int stage;
    protected final int RULES = 0;
    protected final int LIMITS = 1;
    protected final int SHOOTING = 2;
    protected final int RESULTS = 3;

    protected boolean safety;
    protected double startTime;
    protected double timer;
    protected double endTimer;
    protected final Sprite grey = ResourceFactory.get().getSprite("resources/sprites/reticle/flash.png");
    protected final Sprite red = ResourceFactory.get().getSprite("resources/redPix.png");
    protected final Sprite blue = ResourceFactory.get().getSprite("resources/bluPix.png");
    protected final Sprite purple = ResourceFactory.get().getSprite("resources/purPix.png");
    protected final Font whiteFont = GameStateManager.whiteMenu;
    protected final Font yellowFont = GameStateManager.yellowMenu;

    protected Background wall;
    protected Background port;
    protected Door door;

    protected ArrayList<TargetEntity> targets;
    protected ArrayList removeList;
    protected ArrayList<BulletHole> bulletHoles;
}
