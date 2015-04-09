package edu.rcc.student.bwarfield.cis18b.ShootEmUp_v1.GameState;

import edu.rcc.student.bwarfield.CIS18B.ShootEmUp_v1.Main.GamePanel;
import edu.rcc.student.bwarfield.cis18b.ShootEmUp_v1.TileMap.Background;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class MenuState extends GameState {

    //background image
    private Background bg;

    //menu options
    private int currentChoice = 0;
    private String[] option = {"Practice", "Beginer", "Advanced"};
    private Rectangle practice = new Rectangle(35, 140, 220, 320);
    private Rectangle beginer = new Rectangle(290, 140, 220, 320); 
    private Rectangle advanced = new Rectangle(545, 140, 220, 320);
    
    //constructor method
    //@param GameStateManager
    public MenuState(GameStateManager gsm){
        this.gsm = gsm;
        
        //load background image
        try {
            bg = new Background("resources/backgrounds/darkwall.jpg", 1);
            bg.setPosition((GamePanel.G_WIDTH-1024)/2, (GamePanel.G_HEIGHT-768)/2);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    //initialize gamestate
    @Override
    public void init() {

    }

    //update gamestate
    //@param delta timing vector
    @Override
    public void gameUpdate(long delta) {
        bg.gameUpdate(delta);//update background position 
    }

    //render inamge buffer (bottom later first, top layer last)
    //@param game graphics context
    @Override
    public void gameRender(Graphics2D g) {
        //background
        bg.gameRender(g);
        
        g.setColor(Color.green);
        g.drawRect(practice.x, practice.y, practice.width, practice.height);
        g.setColor(Color.yellow);
        g.drawRect(beginer.x, beginer.y, beginer.width, beginer.height);
        g.setColor(Color.red);
        g.drawRect(advanced.x, advanced.y, advanced.width, advanced.height);
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
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
