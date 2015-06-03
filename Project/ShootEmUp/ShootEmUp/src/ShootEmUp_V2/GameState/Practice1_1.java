package ShootEmUp_V2.GameState;

import ShootEmUp_V2.Entity.BlackTarget;
import ShootEmUp_V2.Entity.BulletHole;
import ShootEmUp_V2.Entity.Door;
import ShootEmUp_V2.Entity.TargetEntity;
import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Users.User;
import ShootEmUp_V2.Util.Background;
import ShootEmUp_V2.Util.Sprite;
import ShootEmUp_V2.Util.SystemTimer;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Practice1_1 extends Level {

    private Sprite targetImg;

    public Practice1_1(GameStateManager gsm) {

        this.gsm = gsm;
        init();
    }

    //initialize game stage
    @Override
    public void init() {
        shotsFired = 0;
        shotsHit = 0;

        wall = new Background("resources/backgrounds/rangeBrick.png", 1);
        door = new Door(0, 0, "");
        port = new Background("resources/backgrounds/rangePort.png", 1);
        targetImg = ResourceFactory.get().getSprite("resources/sprites/target/black.png");

        //turn on saftey
        safety = true;
        //get start time
        startTime = SystemTimer.getTime();
        timer = SystemTimer.getTime() + 3;

        //init game stage
        stage = RULES;

        //init arrayLists
        targets = new ArrayList<>();
        removeList = new ArrayList();
        bulletHoles = new ArrayList<>();

        //init Objectives
        objective = 18;
        objectivePoints = 0;
        hitScore = 0;
        shotLimit = false;

//        //init list of targets to shoot
//        for (int i = 0; i < objective; i++) {
//            TargetEntity target = new BlackTarget(this, (116 * (i % 6)) + 110, (116 * (i / 6)) + 180, "", 100);
//            targets.add(target);
//        }
    }

    //initialize levelStage for results Stage
    private void endShooting() {
        timer = SystemTimer.getTime() + 10;//set new Timer
        hitAccScore = (int) ((shotsHit / (float) shotsFired) * 1000);//calsulate accuracy score
        if(quickHit <= 0 && quickHit >= 0.02){
            quickHitScore = 3000;
        }else{
            quickHitScore = (int) ((-1000*quickHit)+2000);
        }
        stage = RESULTS;//change level stage
    }

    @Override
    public void gameUpdate(double delta) {
        //move player
        gsm.getPlayer().move(delta);
        //do player logic
        gsm.getPlayer().doLogic();
        //if safety off

        door.doLogic(safety);

        if (stage == SHOOTING) {

            //do tarrgert logic and remove dead targets 
            for (TargetEntity target : targets) {
                target.doLogic();

                //if target dies add to score and remove target
                if (target.isDead()) {
                    objectivePoints++;
                    if (target.quickDrawTime() < quickHit) {//get lowest quickdraw time
                        quickHit = target.quickDrawTime();
                    }
                    removeList.add(target);
                }
            }
            //check shots fired
            if (shooting && SystemTimer.getTime() - lastShot > shotDelay) {//timing interval check
                gsm.getPlayer().shoot();//play shot animation
                shotsFired++;//count shots

                //create bullethole at ahot location
                BulletHole hole = new BulletHole((int) gsm.getPlayer().getX(), (int) gsm.getPlayer().getY(), "");
                bulletHoles.add(hole);

                //check for collision with target
                for (TargetEntity target : targets) {
                    if (target.contains((int) gsm.getPlayer().getX(), (int) gsm.getPlayer().getY())) {
                        if (!target.isDying() && !target.isDead()) {

                            shotsHit++;
                            target.hit();
                            hitScore += target.getValue();
                        }
                    }
                    //64 bullet hole max
                    while (bulletHoles.size() > 64) {
                        bulletHoles.remove(0);
                    }
                }

                //track time for shotDelay
                lastShot = SystemTimer.getTime();

            }
            //finish shooting
            shooting = false;

            //clear dead targets from list and clear removeList
            targets.removeAll(removeList);
            removeList.clear();
        }
    }

    @Override
    public void gameRender() {
        //Draw background
        wall.gameRender();

        //draw bullet holes
        for (BulletHole hole : bulletHoles) {
            hole.draw();
        }
        //Shooting stage
        if (stage == SHOOTING) {

            //draw targets
            for (TargetEntity target : targets) {
                target.draw();
            }

        }

        //draw shutter doors
        door.draw();
        //draw rangePort
        port.gameRender();

        //Rules Stage
        if (stage == RULES) {
            //draw background frames
            grey.drawToSize(40, 20, 720, 50);
            red.drawToSize(40, 80, 340, 420);
            blue.drawToSize(420, 80, 340, 420);

            //draw objective text and graphics
            String s = "Shoot all the Targets";
            whiteFont.draw("Shoot", 60, 100);
            grey.drawToSize(60, 130, 300, 160);
            targetImg.drawToSize(120, 150, 75, 75);
            grey.drawToSize(440, 130, 300, 160);
            targetImg.drawToSize(500, 150, 75, 75);
            whiteFont.draw("Shoot", 440, 100);
//      whiteFont.draw("Don't Shoot", 60,350);
            yellowFont.draw(
                    s,
                    (ResourceFactory.get().getGameWindow().getWidth() - whiteFont.getStringWidth(s)) / 2,
                    30);

            if (timer < SystemTimer.getTime()) {//when time i up, switch to limits stage
                timer = SystemTimer.getTime() + 4;
                stage = LIMITS;
            }
        } else if (stage == LIMITS) {//Limits Stage
            if (timer > SystemTimer.getTime() + 1) {
                //draw frames
                grey.drawToSize(40, 20, 720, 50);
                red.drawToSize(40, 80, 240, 420);
                purple.drawToSize(300, 140, 200, 300);
                blue.drawToSize(520, 80, 240, 420);
                //draw limits text
                String s = "Shoot all the Targets";
                whiteFont.draw("Shot Limit", 60, 100);
                whiteFont.draw(shotLimit ? String.valueOf(bullets) : "Unlimited", 60, 130);
                whiteFont.draw("Time Limit", (800 - whiteFont.getStringWidth("Time Limit")) / 2, 140);
                whiteFont.draw("10 Sec", (800 - whiteFont.getStringWidth("Time Limit")) / 2, 170);
                whiteFont.draw("Shot Limit", 540, 100);
                whiteFont.draw(shotLimit ? String.valueOf(bullets) : "Unlimited", 540, 130);
                yellowFont.draw(
                        s,
                        (ResourceFactory.get().getGameWindow().getWidth() - whiteFont.getStringWidth(s)) / 2,
                        30);
            } else if (timer < SystemTimer.getTime()) {//when time is up, switch to shooting stage
                timer = SystemTimer.getTime() + 10;
                safety = false;
                
                //init list of targets to shoot
                for (int i = 0; i < objective; i++) {
                    TargetEntity target = new BlackTarget(this, (116 * (i % 6)) + 110, (116 * (i / 6)) + 180, "", 100);
                    targets.add(target);
                }
                
                //start shooting stage
                stage = SHOOTING;
            }

        } else if (stage == SHOOTING) {

            String time;
            time = Integer.toString((int) ((int) (timer - SystemTimer.getTime()) < 0 ? 0 : (timer - SystemTimer.getTime())));
            yellowFont.draw(time, (800 - whiteFont.getStringWidth(time)) / 2, 550);

            //check if time up or all targets shot
            if (targets.isEmpty()) {

                if (!safety) {//if this is the first time isEmpty returned true
                    endTimer = SystemTimer.getTime();//end stage early
                    safety = true;//disable shooting
                }

                if (endTimer < SystemTimer.getTime() - 1.5) {//time buffer to allow animations to finish
                    endShooting();//prepare for results stage
                }
            }//end isEmpty block

            if (timer < SystemTimer.getTime()) {//if time runs out
                safety = true;//disable shooting
            }
            if (timer < SystemTimer.getTime() - 1.5) {//time buffer to allow animations to finish
                endShooting();//prepare for results stage
            }
        } else if (stage == RESULTS) {
            //draw frames
            grey.drawToSize(40, 20, 720, 50);
            red.drawToSize(40, 80, 340, 420);
            blue.drawToSize(420, 80, 340, 420);
            String s = "Shoot all the Targets";
            yellowFont.draw(
                    s,
                    (ResourceFactory.get().getGameWindow().getWidth() - whiteFont.getStringWidth(s)) / 2,
                    30);
            if (timer > SystemTimer.getTime() + 5) {//for the first 5 secs
                //draw level results
                whiteFont.draw("Tagets Shot: " + objectivePoints, 60, 100);
                if (objectivePoints >= objective) {
                    yellowFont.draw("Win!", 60, 130);
                } else {
                    yellowFont.draw("Fail", 60, 130);
                }
            }
            if (timer < SystemTimer.getTime() + 5) {//for the last 5 secs
                //draw shooting stats
                whiteFont.draw("HIT: " + shotsHit, 60, 100);
                whiteFont.draw("HIT%: " + ((int) ((shotsHit / (float) shotsFired) * 10000)) / 100f, 60, 160);
                whiteFont.draw("QuickHit: "+((int)(quickHit*100))/100f + "s" , 60, 220);
                if (timer < SystemTimer.getTime() + 4) {// for the last 4 secs
                    //draw shooting score
                    whiteFont.draw(String.valueOf(hitScore), 60, 130);
                    whiteFont.draw(String.valueOf(hitAccScore), 60, 190);
                    whiteFont.draw(String.valueOf(quickHitScore), 60, 250);
                }
                if (timer < SystemTimer.getTime() + 3) {//for the last 3 secs
                    //draw the final score
                    whiteFont.draw("Total ", 60, 280);
                    whiteFont.draw(String.valueOf(hitScore + hitAccScore+quickHitScore), 60, 310);
                }

            }
            if (timer < SystemTimer.getTime()) {
                //send score to gsm session instance
                gsm.getSession().addScore(1, objectivePoints >= objective, hitScore + hitAccScore+quickHitScore, shotsFired, shotsHit, ((int)(quickHit*100))/100f);
                
                //return to level select menu
                gsm.returnToState(GameStateManager.COURSESELECTORSTATE);
                ((CourseSelectorState) (gsm.getGameStates().get(GameStateManager.COURSESELECTORSTATE))).nextLevel();
            }
        }

        //draw Difficulty and level number
        yellowFont.draw(((CourseSelectorState) (gsm.getGameStates().get(GameStateManager.COURSESELECTORSTATE))).getLevel(), 30, 569);
        yellowFont.draw(User.getUserHandle(), 30, 0);
        //draw player reticle
        gsm.getPlayer().draw();
    }

    @Override
    public void keyPressed(int k) {

    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public void keyTyped(int k) {

    }

    @Override
    public void mousePressed(int button) {
        if (!safety && button == MouseEvent.BUTTON1) {
            shooting = true;
        }
    }

    @Override
    public void mouseMoved(Point point) {

    }

}
