import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


/**
 * Scary game object. 
 */
public class Spikes {
    private Rectangle2D.Double hitbox;
    //private Color color = Color.RED;
    private BufferedImage image; //  spike sprite
    private boolean activeMoving = false; 
    private boolean activeMoving2 = false; 

    private double moveSpeed = 8;
    private double lastY; // track previous frame position

    /** To initalize we need the postion, size and the image.
     * Setup of hitbox
     * @param x c
     * @param y c
     * @param width c
     * @param height c
     * @param imagePath c
     */
    public Spikes(double x, double y, double width, double height, String imagePath) {
        hitbox = new Rectangle2D.Double(x, y, width, height);
        lastY = y; // initialize lastY
        try {
            image = ImageIO.read(new File(imagePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Getter method that allows GamePanel to read spikes location (and manipulate pos)
    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }

    /**
     * Drawing.
     * @param g2 e
     */
    public void draw(Graphics2D g2) {
        
        if (image != null) {
            g2.drawImage(image, (int) hitbox.x, (int) hitbox.y, 
                (int) hitbox.width, (int) hitbox.height, null);
        } else { //fallback incase image failes
            Color customColor = Color.decode("#b04309");
            g2.setColor(customColor);
            g2.fill(hitbox);
        }
    }

    //activate platform up
    public void activateMoving() {
        activeMoving = true;
    }

    public void activateMoving2() {
 
        activeMoving2 = true; 
    }
   
    /**
     * Physics of platform. 
     * Stores current pos in bounds.y into lastY
     * Applies movement so changes bounds.y into a new postion
     */
    public void update() {
        lastY = hitbox.y;  // save current position before moving1 lastY =past. Bounds=future.
        if (activeMoving && hitbox.x < 550) {
            hitbox.x += moveSpeed; // move platform down
        }
        if (activeMoving2 && hitbox.x < 500 ) {
            hitbox.x += moveSpeed;
        }
        
        //We save lastY to calculte amount of frames moved
        

    }

    //Is player touching the spikes? Will trigger lose condition in Gamepanel
    public boolean checkCollision(Rectangle2D.Double playerHitbox) {
        return hitbox.intersects(playerHitbox);
    }

    /**
     * Setter. Abilty to change spike with external code. Level editing & traps.
     * @param x c
     * @param y c
     */
    public void setPosition(double x, double y) {
        hitbox.x = x;
        hitbox.y = y;
    }
}
