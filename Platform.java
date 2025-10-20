import java.awt.*;
import java.awt.geom.Rectangle2D;

/** Chateauuu.
 * Platforms! Surface player can stand on.
 */
public class Platform {
    //Class fields (attributes) will be kept private for encapsulation
    private Rectangle2D.Double bounds; //x,y and width,height
 
    private boolean activeDown = false; 
    
    private boolean activeDownFast = false; 
    //private boolean activeLeft = false;
    //private boolean activeRight = false;
    private boolean activeUp = false;

    private double moveSpeed = 2;
    private double lastY; // track previous frame position
    
    /** Constructor of the platfroms properties. 
     * 
     * @param x e
     * @param y e
     * @param width e
     * @param height e
     */
    public Platform(double x, double y, double width, double height) {
        bounds = new Rectangle2D.Double(x, y, width, height);
        lastY = y; // initialize lastY
    }

    //public getter to read platform position
    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    /**
     * Drawing.
     * @param g wowza. 
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        Color customColor = Color.decode("#222034");
        g2.setColor(customColor);
        g2.fill(bounds);
    }

    //activate platform down
    public void activateDown() {
        activeDown = true; 
    }

     public void activateDownfast() {
        activeDownFast = true; 
    }

    //activate platform up
    public void activateUp() {
        activeUp = true; 
    }
   
    /**
     * Physics of platform. 
     * Stores current pos in bounds.y into lastY
     * Applies movement so changes bounds.y into a new postion
     */
    public void update() {
        lastY = bounds.y;  // save current position before moving1 lastY =past. Bounds=future.
        if (activeDown) {
            bounds.y += moveSpeed; // move platform down
        }
        if (activeUp) {
            bounds.y -= moveSpeed;
        }
        if(activeDownFast) {
            bounds.y += 20;
        }
        //We save lastY to calculte amount of frames moved
        

    }

    //Calcultes the change in the platforms vertical postion
    //When player is standing on platform player calls this method and adds the
    //result to its own Y-pos, so basically if platform moves up 10 px, so does player.
    //Has to happen before gravity and player input. 
    public double getDeltaY() {
        return bounds.y - lastY; // how much platform moved this frame
    }
}
