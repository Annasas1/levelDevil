import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Spikes {
    private Rectangle2D.Double hitbox;
    private Color color = Color.RED;

    public Spikes(double x, double y, double width, double height) {
        hitbox = new Rectangle2D.Double(x, y, width, height);
    }

    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fill(hitbox);
    }

    public boolean checkCollision(Rectangle2D.Double playerHitbox) {
        return hitbox.intersects(playerHitbox);
    }

    public void setPosition(double x, double y) {
        hitbox.x = x;
        hitbox.y = y;
    }
}
