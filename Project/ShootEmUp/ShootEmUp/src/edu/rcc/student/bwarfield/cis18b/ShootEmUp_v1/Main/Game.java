
package edu.rcc.student.bwarfield.CIS18B.ShootEmUp_v1.Main;

import javax.swing.JFrame;

//author Brian Warfield
public class Game{
    public static void main (String[] args){
        //Create Game Frame
        JFrame window = new JFrame("Shoot 'Em Up!");
        //set application to close on exit
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //set game area
        window.setContentPane(new GamePanel());
        //activate frame
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}
