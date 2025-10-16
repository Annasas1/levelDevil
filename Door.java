import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**Here we create the door. Whenever the player intersects with the door we should move to the 'win'
 * screen and the next level will become unlocked. 
 */ 
public class Door {
    //Attributes that define the door
    //We use private (OOP) encapsulation. These variables can only be accesed and
    //modified from within the class Door itself.
    //Other parts of the game will not accidentally change the doors internal state.
    private Rectangle2D.Double bounds; //Position and size in the game
    private BufferedImage image; //sprite

    /**
     * Constructor whch initalizes the door when a new objects is created.
     * Public because GamePanel needs to create a new Door object.
     * @param x e
     * @param y e
     * @param width e
     * @param height e
     * @param imagePath e
     */
    public Door(double x, double y, double width, double height, String imagePath) {
        bounds = new Rectangle2D.Double(x, y, width, height);

        try {
            image = ImageIO.read(new File(imagePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Renders the image of the door. 
     * @param g2 3
     */
    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, (int) bounds.x, (int) bounds.y, 
                (int) bounds.width, (int) bounds.height, null);
        } else { //fallback incase image failes
            Color customColor = Color.decode("#b04309");
            g2.setColor(customColor);
            g2.fill(bounds);
        }
    }

    //Checks if players hitbox intersects with the door's bounds. If yes:win in gamepanel.
    public boolean checkCollision(Rectangle2D.Double playerHitbox) {
        return bounds.intersects(playerHitbox);
    }

    /**
     * Provides external way to code the position of door.
     * @param x coordiante
     * @param y coordinate
     */
    public void setPosition(double x, double y) {
        bounds.x = x;
        bounds.y = y;
    }

    //hitbox of door. This is a getter method (OOP) -> allows collision checks. 
    // (without changing private bounds)
    //This gets the value of a private instance variable from outside the class where it is defined.
    //
    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
