import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Door {
    private Rectangle2D.Double bounds;
    private Color color = Color.YELLOW;

    public Door(double x, double y, double width, double height) {
        bounds = new Rectangle2D.Double(x, y, width, height);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(bounds);
    }

    public boolean checkCollision(Rectangle2D.Double playerHitbox) {
        return bounds.intersects(playerHitbox);
    }

    public void setPosition(double x, double y) {
        bounds.x = x;
        bounds.y = y;
    }

    public Rectangle2D.Double getBounds() {
        return bounds;
    }
}
