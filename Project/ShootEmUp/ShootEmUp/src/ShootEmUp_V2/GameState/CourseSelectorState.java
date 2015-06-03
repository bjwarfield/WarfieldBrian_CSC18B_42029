package ShootEmUp_V2.GameState;

import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Users.Session;
import ShootEmUp_V2.Users.User;
import ShootEmUp_V2.Util.Font;
import ShootEmUp_V2.Util.Sprite;
import ShootEmUp_V2.Util.SystemTimer;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CourseSelectorState extends GameState {

    private Sprite psa;
    private boolean safety;
    double startTime;
    private final String[] difficulties = {"Practice", "Expert"};
    private int menuMode;

    private final int PSA = 0;
    private final int DIFFICULTY = 1;
    private final int PRACTICE = 2;
    private final int EXPERT1 = 3;
    private final int EXPERT2 = 4;
    private final int EXPERT3 = 5;
    private final int EXPERT4 = 6;


    private ArrayList<MenuItem> difficultyModes;
    private ArrayList<MenuItem> practiceStages;
    private String currentDifficulty;
    private int levelCount;

    private int currentChoice;
    private boolean flinching;
    private int flash = 0;
    private double flinchTime = 0;

    //constructor method
    public CourseSelectorState(GameStateManager gsm) {
        this.gsm = gsm;
        init();

    }

    //initialize Entities
    @Override
    public void init() {


        //get PSA image
        psa = ResourceFactory.get().getSprite("resources/backgrounds/winnersPSA.png");
        //turn on saftey
        safety = true;
        //get start time
        startTime = SystemTimer.getTime();
        //initialize stage arrayLists
        difficultyModes = new ArrayList<>();
        practiceStages = new ArrayList<>();

        //populate arrayLists
        //Difficulty list
        for (int i = 0; i < difficulties.length; i++) {
            MenuItem item = new MenuItem(difficulties[i], "resources/sprites/reticle/flash.png");
            item.setWidth(240);
            item.setHeight(350);
            difficultyModes.add(item);
        }
        //practice list
        for (int i = 0; i < 4; i++) {
            MenuItem item = new MenuItem("test", "resources/sprites/reticle/flash.png");
            item.setWidth(280);
            item.setHeight(210);
            practiceStages.add(item);
        }
        //Expert1 list
        //Expert2 list
        //Expert3 list
        //Expert4 list
  
        //initialize menuMode
        menuMode = PSA;
        flinching = false;

    }

    //evoke menu selection
    private void select() {
        if (menuMode == DIFFICULTY) {
            if (currentChoice == 0) {
                menuMode = PRACTICE;
                currentDifficulty = "Practice";
                levelCount = 1;
                gsm.setSession(new Session());
            } else if (currentChoice == 1) {
                menuMode = EXPERT1;
                currentDifficulty = "Expert";
                levelCount = 1;
                gsm.setSession(new Session());
            } 
        } else if (menuMode == PRACTICE) {
            if (currentChoice == 0) {
                gsm.setState(GameStateManager.PRACTICE1_01);
                practiceStages.get(0).setCompleted(true);
                
            }
        }

    }
    
    public String getLevel(){
        return currentDifficulty + " " + Integer.toString(levelCount);
    }
    
    public void nextLevel(){
        levelCount++;
    }

    //update game entities
    @Override
    public void gameUpdate(double delta) {

        //if safety off
        if (!safety) {
            //move player
            gsm.getPlayer().move(delta);
            //do player logic
            gsm.getPlayer().doLogic();

            if (menuMode == DIFFICULTY) {
                //update menu selection when player hovers over target
                for (int i = 0; i < difficultyModes.size(); i++) {
                    if (difficultyModes.get(i).getZone().contains(gsm.getPlayer().getHitBox())) {
                        currentChoice = i;
                    }
                }
            } else if (menuMode == PRACTICE) {
                for (int i = 0; i < practiceStages.size(); i++) {
                    if (practiceStages.get(i).getZone().contains(gsm.getPlayer().getHitBox())) {
                        currentChoice = i;
                    }
                }
            }
        }
    }

    @Override
    public void gameRender() {
        //display psa message
        if (menuMode == PSA) {
            psa.draw(0, 0);
        }
        //after 3 secs show menus
        if (SystemTimer.getTime() - startTime > 3) {
            //if comming from PSA, swith to select difficulty mode and turn safety off
            if (menuMode == PSA) {
                menuMode = DIFFICULTY;
                safety = false;
            }

            //show difficulty modes while highlighting the current selection
            if (menuMode == DIFFICULTY) {
                for (MenuItem dm : difficultyModes) {
                    String s = dm.getGameState();
                    Font selectedFont;
                    if (difficultyModes.indexOf(dm) == currentChoice) {
                        selectedFont = GameStateManager.yellowMenu;
                    } else {
                        selectedFont = GameStateManager.whiteMenu;
                    }
                    int i = difficultyModes.indexOf(dm);
                    dm.setX(i * 400 + 80);
                    dm.setY(40);

                    //
                    if (difficultyModes.indexOf(dm) == currentChoice && flinching) {
                        flash++;
                        if (flash > 4) {
                            dm.draw();
                            if (flash % 8 == 0) {
                                flash = 0;
                            }
                        }
                        if (flinchTime - SystemTimer.getTime() < 0) {
                            flinching = false;
                            select();
                            safety = false;
                            flash = 0;
                        }
                    } else {
                        dm.draw();
                    }

                    selectedFont.draw(s, (i * 400 + 200) - (selectedFont.getStringWidth(s) / 2), 62);

                }
            }
            if (menuMode == PRACTICE) {
                GameStateManager.yellowMenu.draw(getLevel(), 30, 569);
                GameStateManager.yellowMenu.draw(User.getUserHandle(), 30, 0);
                for (int i = 0; i < practiceStages.size(); i++) {
                    practiceStages.get(i).setX((360 * (i % 2)) + 80);
                    practiceStages.get(i).setY((240 * (i / 2)) + 40);

                    if (i == currentChoice && flinching) {
                        flash++;
                        if (flash > 4) {
                            practiceStages.get(i).draw();
                            if (flash % 8 == 0) {
                                flash = 0;
                            }
                        }
                        if (flinchTime - SystemTimer.getTime() < 0) {
                            flinching = false;
                            select();
                            safety = false;
                            flash = 0;
                        }
                    } else {
                        practiceStages.get(i).draw();
                    }

                    if (practiceStages.get(i).isCompleted()) {
                        //retrieve player stage status
                    }
                }
            }
            gsm.getPlayer().draw();
        }

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
            gsm.getPlayer().shoot();
        }

        if (!safety && menuMode == DIFFICULTY) {
            //currently hovering over menu item
            if (difficultyModes.get(0).getZone().contains(gsm.getPlayer().getHitBox()) || difficultyModes.get(1).getZone().contains(gsm.getPlayer().getHitBox()) ) {
                safety = true;
                flinching = true;
                flinchTime = SystemTimer.getTime() + 1;
//                select();

            }
        } else if (!safety && menuMode == PRACTICE) {
            //if cursor is hovering above menuitem and menutime is not completed
            if ((practiceStages.get(0).getZone().contains(gsm.getPlayer().getHitBox()) && !practiceStages.get(0).isCompleted())
                    || (practiceStages.get(1).getZone().contains(gsm.getPlayer().getHitBox()) && !practiceStages.get(1).isCompleted()) 
                    || (practiceStages.get(2).getZone().contains(gsm.getPlayer().getHitBox()) && !practiceStages.get(2).isCompleted()) 
                    || (practiceStages.get(3).getZone().contains(gsm.getPlayer().getHitBox())) && !practiceStages.get(3).isCompleted()) {
                safety = true;
                flinching = true;
                flinchTime = SystemTimer.getTime() + 1;
            }
        }
    }

    @Override
    public void mouseMoved(Point point) {

    }

}

//MenuItem class is used to collect and list game levels for menu selection
class MenuItem {

    private String gameState;
    private boolean completed;
    private Sprite image;
    private int x, y;
    int width, height;
    private Rectangle zone;

    public MenuItem(String gameState, String ref) {
        this.gameState = gameState;
        this.image = ResourceFactory.get().getSprite(ref);
        this.x = this.y = 0;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.zone = new Rectangle(x, y, width, height);
        this.completed = false;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String GameState) {
        this.gameState = GameState;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setImage(Sprite image) {
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        zone.x = x;
    }

    public int getY() {
        return y;

    }

    public void setY(int y) {
        this.y = y;
        zone.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        zone.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        zone.height = height;
    }

    public Rectangle getZone() {
        return zone;
    }

    public void draw() {
        this.image.drawToSize(x, y, width, height);
    }

}
