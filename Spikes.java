import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Box. Or player. Whatever. 
 */
public class Player {
    //Private fields to store info about our lovely player <3
    private double width = 45;
    private double height = 55;
    private double velocityX = 0;
    private double velocityY = 0;
    private double gravity = 1.0;
    // Add a new variable
    private float coyoteTime = 0.1f; // 0.1 seconds of grace
    private float timeSinceGrounded = 0;

    private Rectangle2D.Double hitbox;
    private Rectangle2D.Double previousHitbox;

    private boolean onGround = false;
    private Platform currentPlatform = null; // the platform player is standing on
    
    private BufferedImage image; //  s

    public Platform getCurrentPlatform() {
        return currentPlatform;
    }
    
    /*
    public void update(float deltaTime) {
        if (onGround || currentPlatform != null) {
            timeSinceGrounded = 0; 
        } else {
            timeSinceGrounded += deltaTime;
    }
    */
    
    /** Constructor initalized the starting pos.
     * 
     * @param startX c
     * @param startY c
     */
    public Player(int startX, int startY, String imagePath) {
        hitbox = new Rectangle2D.Double(startX, startY, width, height);
        previousHitbox = (Rectangle2D.Double) hitbox.clone();

        try {
        this.image = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
        System.err.println("Failed to load player image: " + imagePath);
        e.printStackTrace();
    }
    }

    /** Here is our Coyote time logic. This controls the 'grace period'.
     * When grounded timer resets to 0, when in air it will count up, the timer
     * will then determin if the player still has a moment to jump even when walking off ledge.
     * Called every 15ms
     * Handles our movement gravity and timing of box.
     * @param deltaTime e
     */
    public void update(float deltaTime) { 
        if (onGround || currentPlatform != null) {
            timeSinceGrounded = 0; // reset timer when grounded
        } else {
            timeSinceGrounded += deltaTime; // count time in air
        }
        
        previousHitbox = (Rectangle2D.Double) hitbox.clone(); //ESSENTAL!!!where player was before
        
        // apply horizontal movement FIRST!!
        hitbox.x += velocityX;

        // following platfrom
        if (currentPlatform != null) {
            hitbox.y += currentPlatform.getDeltaY();
        }
        
        // apply gravity
        hitbox.y += velocityY;
        velocityY += gravity;
    }

    public Rectangle2D.Double getHitbox() {
        return hitbox;
    }

    public Rectangle2D.Double getPreviousHitbox() {
        return previousHitbox;
    }

    public double getVelocityY() {
        return velocityY;
    }

    //All of these set speed, movement happens in update

    public void moveRight() {
        velocityX = 5; 
    }

    public void moveLeft() { 
        velocityX = -5; 
    }

    public void stopMoving() { 
        velocityX = 0; 
    }

    // Updated jump method.
    /** Jump only exectues if:
    * Standing o an STILL platform.
    * Standing on a moving platform.
    * Using the coyote time grace period
    */
    public void jump() {
        if (onGround || currentPlatform != null || timeSinceGrounded < coyoteTime) {
            velocityY = -18; //negative velocity which is upward movement
            onGround = false;
            currentPlatform = null;
            timeSinceGrounded = coyoteTime; // prevent double jump in the grace period
            System.out.println("Not on ground!");
        }

    }

    /**
 * Sets the player's position and resets movement state.
 * This is used for level initialization or respawning.
 * @param newX The new x-coordinate for the player's top-left corner.
 * @param newY The new y-coordinate for the player's top-left corner.
 */
public void setPosition(int newX, int newY) {
    // 1. Update the position of the hitbox
    this.hitbox.x = newX;
    this.hitbox.y = newY;
    
    // 2. Reset velocities and grounded state for a fresh start
    this.velocityX = 0;
    this.velocityY = 0;
    this.onGround = false;
    this.currentPlatform = null;
    this.timeSinceGrounded = 0;
    
    // Also update previousHitbox immediately so it doesn't cause errors in the first update tick
    this.previousHitbox.setFrame(newX, newY, width, height);
}
    /*    public void update() {
        previousHitbox = (Rectangle2D.Double) hitbox.clone();
        // apply horizontal movement FIRST!!
        hitbox.x += velocityX;
        //following platfrom
        if (currentPlatform != null) {
            hitbox.y += currentPlatform.getDeltaY();
            // allow jumping even if platform moves down
            
        }
        
        // follow moving platform if standing on it

        // apply gravity
        hitbox.y += velocityY;
        velocityY += gravity;

       
    } */

     
    /** ATTENTION: only method that should set onGround true.
     * Otherwise messes up our jumping system.
     * 
     * @param platform landing
     */
    public void landOn(Platform platform) {
        hitbox.y = platform.getBounds().y - height;
        velocityY = 0;
        onGround = true;
        currentPlatform = platform;
    }

    public boolean checkCollision(Platform platform) {
        return hitbox.intersects(platform.getBounds());
    }

    /** Public setter, manage the player's grounded state.
     * It prevents the player from continuing to "follow" a platform after walking off it
     * @param value e
     */
    public void setOnGround(boolean value) {
        onGround = value;
        if (!value) {
            currentPlatform = null; 
        }
    }

    /** Side collision detected.
     * 
     * @param platform horizontal |
     */
    public void handleHorizontalCollision(Platform platform) {
        Rectangle2D.Double pb = platform.getBounds();

        // If the player was moving right and hit the platform's left side:
        if (velocityX > 0) {
            // Reset player's x to touch the platform's left edge
            hitbox.x = pb.x - width; 
            // If the player was moving left and hit the platform's right side:
        } else if (velocityX < 0) { 
            // Reset player's x to touch the platform's right edge
            hitbox.x = pb.getMaxX();
        }
        
        
        
        // Stop horizontal movement after hitting the wall
        velocityX = 0;
    }

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
}
