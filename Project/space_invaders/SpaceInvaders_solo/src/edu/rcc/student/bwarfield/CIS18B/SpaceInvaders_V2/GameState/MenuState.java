package edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.GameState;

import edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.Main.GamePanel;
import edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

    private Background bg;

    private int currentChoice = 0;
    private String[] option = {
        "Start",
        "High Scores",
        "Options",
        "Quit"
    };


    public MenuState(GameStateManager gsm) {
        this.gsm = gsm;

        try {
            bg = new Background("/backgrounds/Space.gif", 1);
            bg.setVector((float) -1, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void gameUpdate() {
        bg.gameUpdate();
    }

    @Override
    public void gameRender(Graphics2D g) {
        bg.gameRender(g);

        //draw title
        g.setColor(new Color(255,0,0));
        g.setFont(new Font("Impact", Font.PLAIN,48));
        g.drawString("Invaders from Space!", (GamePanel.G_WIDTH - g.getFontMetrics().stringWidth("Invaders from Space!") )/2, 200);

        //draw menu options
        for (int i = 0; i < option.length; i++) {
            if (i == currentChoice) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(new Color(200, 200, 200));
            }
            String s = option[i];
            g.drawString(s, (GamePanel.G_WIDTH - g.getFontMetrics().stringWidth(s))/2, 250 + i * 40);
        }

    }

    private void select() {
        if(currentChoice == 0){
        //start
        }
        if(currentChoice == 1){
        //Scores
        }
        if(currentChoice == 2){
        //options
        }
        if(currentChoice == 3){
        //Quit
            System.exit(0);
        }
    }

    @Override
    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ENTER) {
            select();
        }
        if (k == KeyEvent.VK_UP) {
            currentChoice--;
            if(currentChoice <0){
                currentChoice = option.length -1;
            }
        }
        if (k == KeyEvent.VK_DOWN) {
            currentChoice++;
            if(currentChoice >option.length-1){
                currentChoice = 0;
            }
        }
        
    }

    @Override
    public void keyReleased(int k) {

    }

}
