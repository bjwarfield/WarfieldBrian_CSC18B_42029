package ShootEmUp_V2.Main;

import com.badlogic.jglfw.Glfw;
import java.awt.Canvas;

public class Game extends Canvas implements GameWindowCallback {

    //the window that is being used to render the game
    private GameWindow window;
    private String windowTitle = "Shoot Em Up!";

    //class constructor
    //@param renderingType constants in resource Factory Class
    public Game(int renderingType) {
        //create a window based ona chosen rendering method
        ResourceFactory.get().setRenderingType(renderingType);
        window = ResourceFactory.get().getGameWindow();

        window.setResolution(800, 600);
        window.setGameWindowCallback(this);
        window.setTitle(windowTitle);
    }

    //start the game window rendering the display
    public void startRendering() {
        window.startRendering();
    }

//    //initialize the common elements for the game
//    public void init() {
//        //start the game
//        startGame();
//    }

    public static void main(String[] args) {

        //initialize glfw library
        boolean glfwResult = Glfw.glfwInit();
        if (glfwResult == false) {
            throw new IllegalStateException("GLFW Failed to initialize");
        }

        //OPEN_GL is now unimportant in the context of this project
//        int result = JOptionPane.showOptionDialog(null, "Java2D or OpenGL?", "Java2D or OpenGL?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Java2D", "OpenGL"}, null);
        int result = 0;

        if (result == 0) {
            Game game = new Game(ResourceFactory.JAVA2D);
            game.startRendering();
        } else if (result == 1) {
            Game game = new Game(ResourceFactory.OPENGL_LWJGL);
            game.startRendering();
        }
    }

    public void windowClosed() {
        Glfw.glfwTerminate();
        System.exit(0);
    }

    @Override
    public void init() {
    }
}
