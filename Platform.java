import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Platform {
    private Rectangle2D.Double bounds;
    private boolean active = false; 
    private double moveSpeed = 2;

    public Platform(double x, double y, double width, double height) {
        bounds = new Rectangle2D.Double(x, y, width, height);
    }

    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fill(bounds);
    }
    public void activate() {
        active = true; 
    }
    public void update() {
        if (active) {
            bounds.y +=moveSpeed; 
        }
    }
}
