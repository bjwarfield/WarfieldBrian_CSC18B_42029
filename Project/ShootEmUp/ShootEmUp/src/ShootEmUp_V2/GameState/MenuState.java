package ShootEmUp_V2.GameState;

import ShootEmUp_V2.Entity.EntityReticle;
import ShootEmUp_V2.Main.GameWindow;
import ShootEmUp_V2.Main.ResourceFactory;
import ShootEmUp_V2.Users.User;
import ShootEmUp_V2.Users.UserControlPanel;
import ShootEmUp_V2.Util.Background;
import ShootEmUp_V2.Util.Font;
import ShootEmUp_V2.Util.Mouse;
import ShootEmUp_V2.Util.Sprite;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuState extends GameState {

    //gameWindow
    GameWindow window = ResourceFactory.get().getGameWindow();
    //player icon
    EntityReticle player;

    //safety switch. Player cannot shoot when on.
    private boolean safety = false;

    private Background bg;
    
    private UserControlPanel ucp = null;

    //munu options
    private int currentChoice = 0;
    private final String[] options = {
        "Start Game",
        "Login/Register",
        "Exit"};
    Rectangle[] menuTarget = new Rectangle[options.length];//to detect mouse hover


    //title image
    Sprite logo = ResourceFactory.get().getSprite("resources/title.png");

    //constructor method
    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        init();
    }

    //initialize gamestate instance
    @Override
    public void init() {
        //create new player instance at mouse position
        player = new EntityReticle(Mouse.getPos().x, Mouse.getPos().y, "red");

        //initialize menu target objects
        for (int i = 0; i < menuTarget.length; i++) {
            menuTarget[i] = new Rectangle(0, 0, 0, 0);
        }
    }

    //update gamestate in accordance to gameTime
    @Override
    public void gameUpdate(double delta) {

        //if safety off
        if (!safety) {
            //move player
            player.move(delta);
            //do player logic
            player.doLogic();

            //update menu selection when player hovers over target
            for (int i = 0; i < menuTarget.length; i++) {

                if (menuTarget[i].contains(player.getHitBox())) {
                    currentChoice = i;
                }
            }
        }
    }

    //render game elements to buffer
    @Override
    public void gameRender() {

        //draw title
        logo.draw((window.getWidth() - logo.getWidth()) / 2, 75);

        for (int i = 0; i < options.length; i++) {
            String s;
            
            if(i==1){
                if (User.isLoggedIn()){
                    s = "User Control Panel";
                }else{
                    s = options[i];
                }
            }else{
               s = options[i];
            }
            Font selectedFont;
            if (i == currentChoice) {
                selectedFont = GameStateManager.yellowMenu;
            } else {
                selectedFont = GameStateManager.whiteMenu;
            }
            menuTarget[i].setLocation(
                    ((window.getWidth() - selectedFont.getStringWidth(s)) / 2) - 5,
                    340 + (i * 45) - 5
            );
            menuTarget[i].setSize(
                    selectedFont.getStringWidth(s) + 10,
                    selectedFont.getHeight() + 10
            );
            selectedFont.draw(s, (window.getWidth() - selectedFont.getStringWidth(s)) / 2, 340 + i * 45);
        }

        player.draw();
    }

    //evoke menu selection
    private void select() {
        if (currentChoice == 0) {
            //start
            gsm.setState(GameStateManager.COURSESELECTORSTATE);
            safety = true;//surn dafety on
        }
        if (currentChoice == 1) {
            //register
            if(ucp == null){
                ucp = new UserControlPanel();
            }
            ucp.setVisible(true);
            ucp.requestFocus();
            ResourceFactory.get().getGameWindow().setVisable(false);
            
        }
        if (currentChoice == 2) {
            //Quit
            System.exit(0);
        }

    }

    //keyboard keypressed event
    //@param KeyEvent KeyCode of key pressed
    @Override
    public void keyPressed(int k) {
        //if safety off
        if (!safety) {
            //when enter pressed
            if (k == KeyEvent.VK_ENTER) {
                
                select();
            }
            //when up pressed
            if (k == KeyEvent.VK_UP) {
                currentChoice--;//move selection up one

                if (currentChoice < 0) {//if out if bounds
                    currentChoice = options.length - 1;//reset to bottom
                }
            }
            //when down pressed
            if (k == KeyEvent.VK_DOWN) {
                currentChoice++;//move selection down once
                if (currentChoice > options.length - 1) {//if out of bounds
                    currentChoice = 0;//resot to top
                }
            }
        }
    }

    //keyboard keyReleased event
    //@param KeyEvent KeyCode of key released
    @Override
    public void keyReleased(int k) {

    }

    //keyboard keyTyped event
    //@param KeyEvent KeyCode of key typed
    @Override
    public void keyTyped(int k) {

    }

    //mouse buttonPressed event
    //@param MouseEvent KeyCode of button pressed
    @Override
    public void mousePressed(int button) {
        if (!safety && button == MouseEvent.BUTTON1) {
            player.shoot();
        }

        //if safety off and...
        if (!safety) {
            //currently hovering over menu item
            if (player.getHitBox().intersects(menuTarget[0]) || player.getHitBox().intersects(menuTarget[1]) || player.getHitBox().intersects(menuTarget[2])) {
                
                select();

            }
        }
    }

    @Override
    public void mouseMoved(Point point) {

    }
}
