package ShootEmUp_V2.Util;

import ShootEmUp_V2.Main.GameWindow;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Mouse {
    
//    private static final boolean[] buttons = new boolean[10];
    private static Point pos = new Point();
    private static GameWindow callback;
    
    
    //initialize the central mouse handler
    public static void init(){
        Toolkit.getDefaultToolkit().addAWTEventListener(new MouseHandler(), AWTEvent.MOUSE_EVENT_MASK);
    }
    
    //initialize the central mouse handler
    //@param the component listened to
    public static void init(Component c){
        c.addMouseMotionListener(new MouseHandler());
        c.addMouseListener(new MouseHandler());
        callback = (GameWindow) c;
    }
    
    //get the current position of the cursor
    //@return point position of mouse
    public static Point getPos(){
        return pos;
    }
    
    private static class MouseHandler extends MouseAdapter implements AWTEventListener{

        @Override
        public void mousePressed(MouseEvent e){
            if (e.isConsumed()){
                return;
            }
            callback.mousePressed(e.getButton());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(e.isConsumed()){
                return;
            }
            pos = e.getPoint();
        }
        
        @Override
        public void mouseDragged(MouseEvent e){
            if(e.isConsumed()){
                return;
            }
            pos = e.getPoint();
        }
        
        
        
        //notification that an event has occured in the AWT event system
        //@param e the event details
        @Override
        public void eventDispatched(AWTEvent e) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED){
                mousePressed((MouseEvent)e);
            }
            if (e.getID() == MouseEvent.MOUSE_RELEASED){
                mouseReleased((MouseEvent)e);
            }
            if (e.getID() == MouseEvent.MOUSE_MOVED){
                mouseMoved((MouseEvent)e);
            }
            if (e.getID() == MouseEvent.MOUSE_DRAGGED){
                mouseMoved((MouseEvent)e);
                
            }
            
        }


        
    }
}


