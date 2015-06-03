package ShootEmUp_V2.Java2D;

import ShootEmUp_V2.GameState.GameStateManager;
import ShootEmUp_V2.Main.GameWindow;
import ShootEmUp_V2.Main.GameWindowCallback;
import ShootEmUp_V2.Util.Keyboard;
import ShootEmUp_V2.Util.Mouse;
import ShootEmUp_V2.Util.SystemTimer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Java2DGameWindow extends Canvas implements GameWindow {

    //frame Dimentions
    private int G_WIDTH = 800;
    private int G_HEIGHT = 600;
    //the Callback which should be notified of events caused by this window
    private GameWindowCallback callback;
    //the frame in which the canvas will be displayed
    private final JFrame frame;
    //loop control
    private boolean running = true;
    //buffer for page flipping
    private BufferStrategy strategy;
    //2d rendering canvas
    private Graphics2D g;
    //counting system
    public static final int FPS = 60;
    public static float averageFPS;

    //Game State Manager
    private GameStateManager gsm;

    //create a new window to render using Java 2D    
    public Java2DGameWindow() {
        frame = new JFrame();
    }

    //set title of the game window
    //@param title for the game window
    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }

    //set game display resolution
    //@param x horizontal resolution
    //@param y vertical resolution
    @Override
    public void setResolution(int x, int y) {
        G_WIDTH = x;
        G_HEIGHT = y;
    }

    //set frame visibility
    //@param frame visibility
    @Override
    public void setVisable(boolean bool){
        frame.setVisible(bool);
    }
    
    //request frame focus
    @Override
    public void requestFocus(){
        frame.requestFocus();
    }
    
    //get window height and/or width
    //@return int vertical and/or horizontal witdh
    @Override
    public int getWidth() {
        return G_WIDTH;
    }

    @Override
    public int getHeight() {
        return G_HEIGHT;
    }

    //start the game window rendering the display
    @Override
    public void startRendering() {
        JPanel panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(G_WIDTH, G_HEIGHT));
        panel.setLayout(null);

        Keyboard.init(this);
        Mouse.init(this);

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        frame.getContentPane().setCursor(blankCursor);

        //setup our canvad size and put it into the content of the frame
        setBounds(0, 0, G_WIDTH, G_HEIGHT);
        panel.add(this);

        //tell AWT not to repaint this frame 
        setIgnoreRepaint(true);

        //make the window visible
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //add listner to respond to respond to window close event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(gsm.getSession() != null){
                    gsm.getSession().submitSession();
                }
                if (callback != null) {
                    callback.windowClosed();
                } else {
                    System.exit(0);
                }
            }
        });

        //request the focus
        requestFocus();

        //create the buffereing strategy with will allow AWT to manage accelerated graphics
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        //notify callback if exists
        if (callback != null) {
            callback.init();
        }

        //initialize game state manager
        gsm = new GameStateManager();

        //start game loop
        gameLoop();
    }

    //set the callback that should be notified on the window events
    //@param callback The callback that should be notified
    @Override
    public void setGameWindowCallback(GameWindowCallback callback) {
        this.callback = callback;
    }

    //check if a particular key is pressed
    //@param keyCode te code associated with the key to check
    //@return True if the particular key is pressed
    @Override
    public boolean isKeyPressed(int keyCode) {
        return Keyboard.isPressed(keyCode);
    }

    //retrieve the current accelerated graphics context (package scoped)
    Graphics2D getDrawGraphics() {
        return g;
    }

    //main game loop
    public void gameLoop() {

        //set timing intervals
        double lastLoopTime;
        lastLoopTime = SystemTimer.getTime();
        double delta;//time reference for loop duration
        double totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = FPS;

        //Main game loop
        while (running) {
            //get timing reference
            delta = SystemTimer.getTime() - lastLoopTime;
            lastLoopTime = SystemTimer.getTime();

            //update game mechanics
            gameUpdate(delta);
            //render the buffered image
            gameRender();
            //swap the buffer
            gameDraw();

            //wait the remaining time to get desired fps
            SystemTimer.sync(FPS);

            //calculate FPS
            totalTime += SystemTimer.getTime() - lastLoopTime;
            frameCount++;
            if (frameCount == maxFrameCount) {
                averageFPS = 1 / ((float) totalTime / frameCount);
                frameCount = 0;
                totalTime = 0;
            }

        }
    }

    //update game based on gametime
    //@param delta timedistance between loops
    private void gameUpdate(double delta) {
        gsm.gameUpdate(delta);
    }

    //draw game elements to buffer
    private void gameRender() {
        //clear screen with black
        g = (Graphics2D) strategy.getDrawGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, G_WIDTH, G_HEIGHT);

        //tell gamestate managet to render game elements
        gsm.gameRender();
    }

    //FPS Getter
    //@returns Frames per Second 
    @Override
    public float getFPS() {
        return Math.round(averageFPS * 100) / 100.0f;
    }

    //swap buffer strategy
    private void gameDraw() {
        g.dispose();
        strategy.show();
    }

    //send keyTyped events to gamestate manager
    //@param keyEvent keyCode
    @Override
    public void keyTyped(int key) {
        gsm.keyTyped(key);
    }

    //send keypressed events to gamestate manager
    //@param keyEvent keyCode
    @Override
    public void keyPressed(int key) {
        gsm.keyPressed(key);
    }

    //send keyReleased events to gamestate manager
    //@param keyEvent keyCode
    @Override
    public void keyReleased(int key) {
        gsm.keyReleased(key);
    }

    @Override
    public void mouseMoved(Point point) {
        gsm.mouseMoved(point);
    }

    @Override
    public void mousePressed(int button) {
        gsm.mousePressed(button);
    }

}
