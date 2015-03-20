package edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.TileMap;

import edu.rcc.student.bwarfield.CIS18B.SpaceInvaders_V2.Main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Background {

    private BufferedImage image;

    private float x;
    private float y;
    private float dx;
    private float dy;

    private float moveScale;

    public Background(String s, float ms) {
        try {
            image = ImageIO.read(
                    getClass().getResourceAsStream(s)
            );
            moveScale = ms;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPosition(float x, float y) {
        this.x = (x * moveScale) % GamePanel.G_WIDTH;
        this.y = (y * moveScale) % GamePanel.G_HEIGHT;
    }

    public void setVector(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void gameUpdate() {
        x += dx;
        y += dy;
    }

    public void gameRender(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, null);
        if (x + image.getWidth() < GamePanel.G_HEIGHT) {
            g.drawImage(image, (int) x + image.getWidth(), (int) y, null);
        }
        if (x > 0) {
            g.drawImage(image, (int) x - image.getWidth(), (int) y, null);
        }

    }
}
