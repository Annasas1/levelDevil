import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.DimensionUIResource;
/**
 * We make the player here.
 * 
 * @author Anna Sas 20255082
 * @date 2-9-2025
 */

//create a class for the player in which we define multiple methods wich will
//define the players' movement. 
public class Player {
    //Set starting coordinates for the player
    //int x = 250;
    //int y = 300;
    private double width = 60;
    private double height = 60;
    private double velocityX = 0;
    private double velocityY = 0;
    private double gravity = 1;
    private boolean onGround = true; 
    private Rectangle2D.Double previousHitbox;

    //public Rectangle2D.Double boundingBox;
    private Rectangle2D.Double hitbox;

    public Player(int startX, int startY) {
        hitbox = new Rectangle2D.Double(startX, startY, width, height); 
    }
    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }
    public void moveRight() {
        velocityX = 5; 
    }

    public void moveLeft() {
        velocityX = -5;
    }

    public void stopMoving() {
    velocityX = 0;
    }
    /* 
    void moveUp() {
        y -= 5;
    }
        */

    void jump() {
        if (onGround) {
            velocityY = -20;
            onGround = false;
            System.out.println("Im jumping!");
        }
    }

    public double getVelocityY() {
        return velocityY;
    }

    //Here we create an update so the jump looks smooth
    //The jump makes the charachter stop moving forward -> we have to fix this
    public void update() {
        previousHitbox = (Rectangle2D.Double) hitbox.clone(); // save before moving
        hitbox.y += velocityY;
        velocityY += gravity;
        hitbox.x += velocityX;
    }
    
    public void landOn(Platform platform) {
        hitbox.y = platform.getBounds().y - height;
        velocityY = 0;
        onGround = true; 
    }

    public boolean checkCollision(Platform platform) {
        return hitbox.intersects(platform.getBounds());
    }

    public boolean isFalling() {
        return !onGround;
    }

    public void setOnGround(boolean value) {
        this.onGround = value; 
    }

    public void handleHorizontalCollision(Platform platform) {
        Rectangle2D.Double pb = platform.getBounds();
        if (velocityX > 0) { // moving right
            hitbox.x = pb.x - width;
        } else if (velocityX < 0) { // moving left
            hitbox.x = pb.getMaxX();
        }
        velocityX = 0;
    }

    public void handleTopCollision(Platform platform) {

    }

    public Rectangle2D.Double getPreviousHitbox() {
    return previousHitbox;
    }
    
    //private void handleCollision(Player player, Platform platform) {
    // Check if the player was above the platform and is now colliding with the top
    /*if (player.velocityY > 0 && player.boundingBox.getMaxY() <= platform.boundingBox.getMaxY() && player.boundingBox.getMinY() > platform.boundingBox.getMinY()) {
        player.y = platform.boundingBox.getY() - player.height;
        player.velocityY = 0;
        player.isOnPlatform = true; // You'll need to manage this state variable
    }
    */
        
    }


