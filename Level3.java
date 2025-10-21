import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.*;


public class Level3 implements Level {
    // ---------------------------------------------------------------------
    // Level Specific Data (Platforms, Spikes, Door)
    // ---------------------------------------------------------------------
    private ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Spikes> spikes = new ArrayList<>(); 
    private Door door = new Door(0, 100, 50, 80, "door1.PNG"); 
    private boolean messageToUser = true;
    
    @Override
    public int getLevelID() { 
        return 3; 
    }
    
    // ---------------------------------------------------------------------
    // Level Setup and Reset Logic
    // ---------------------------------------------------------------------
    @Override
    public void init(Player player) {
        //Clear any old level objects before setting up the new level (Crucial for restarts)
        platforms.clear();
        spikes.clear();

        //player starts pos
        player.setPosition(150, 300); 

        int spikeWidth = 50;
        for (int x = 0; x < 1000; x += spikeWidth) {
            spikes.add(new Spikes(x, 508, spikeWidth, 55, "singleSpike.png"));
        }

        //ground
        platforms.add(new Platform(0, 400, 600, 400)); //ground 1
        platforms.add(new Platform(600, 400, 100, 400)); //ground 1 move
        platforms.add(new Platform(700, 400, 300, 400)); //ground 2


        // platforms.add(new Platform(200, 200, 200, 30)); // floating platform 1
        platforms.add(new Platform(400, 250, 150, 30)); // Floating platform 2
        platforms.add(new Platform(0, 0, 50, 600));      // left wall
        platforms.add(new Platform(940, 0, 50, 600));    // right wall
        
        
        
        //win
        door.setPosition(700, 325); 

        messageToUser = true;

        Timer timer = new Timer(5000, e -> messageToUser = false);
        timer.setRepeats(false);
        timer.start();
    }
    
    // ---------------------------------------------------------------------
    // Core Game Loop Logic (Called repeatedly by GamePanel's Timer)
    // ---------------------------------------------------------------------
    @Override
    public void update(float delta, Player box, boolean jumpQueued, GameManager gameController) {
        
        // Update platforms (Platform specific movement logic)
        for (Platform p : platforms) { 
            // Add your old platform movement logic here:
            if ( box.getHitbox().x == 330) {
                spikes.add(new Spikes(400,195,50,55, "singleSpike.png"));
                spikes.add(new Spikes(450,195,50,55, "singleSpike.png"));
                spikes.add(new Spikes(500,195,50,55, "singleSpike.png"));


            } 
            if (box.getHitbox().x >= 580 && p.getBounds().x == 600) {
                p.activateDownfast();
            }
            
            /*
            if (box.getHitbox().x > 520) {
                spikes.add(new Spikes(620,365,40,45, "singleSpike.png"));
            } */
            
            p.update(); // Update platform position/state
        } 

        

        // 2. Update player movement and physics
        box.update(delta);

        for (Spikes s : spikes) {
            if (s.checkCollision(box.getHitbox())) {
                // Level 1 logic: Die!
                gameController.levelFailed(); 
                //System.out.println("boo-hoo");
            }
        }
        
        // 3. Handle collisions 
        boolean landed = false;
        for (Platform p : platforms) {
            
            if (box.checkCollision(p)) {
                Rectangle2D.Double previous = box.getPreviousHitbox();
                
                // Tolerance check for landing from above
                boolean wasAbove = previous.getMaxY() <= p.getBounds().getY() + 1;
            
                // Landing/Vertical Collision:
                if ((wasAbove && box.getVelocityY() >= 0) || (box.getCurrentPlatform() == p)) {
                    box.landOn(p); 
                    landed = true;
                } else { // Horizontal Collision (hitting the side)
                    box.handleHorizontalCollision(p);
                }
            }
        }
        
        // Final check for falling off a platform
        if (!landed && box.getVelocityY() > 0) { 
            box.setOnGround(false); 
        }

        // Handle jump queue (only jumps if box is on the ground, checked internally by box.jump())
        if (jumpQueued) { 
            box.jump(); 
            // Note: GamePanel resets jumpQueued to false in the next iteration.
        }

        //  Check Win/Lose Conditions and notify the GameManager
        if (door.checkCollision(box.getHitbox())) {
            // Level 1 logic: Win!
            gameController.levelCompleted(getLevelID()); 
        }
        
    }
    
    // ---------------------------------------------------------------------
    // Drawing Logic
    // ---------------------------------------------------------------------
    @Override
    public void draw(Graphics2D g) {
        // Draw level elements (platforms, door, spikes)
        for (Spikes s : spikes) { s.draw(g); } //spikes have to stay hidden
        for (Platform p : platforms) { p.draw(g); }
        door.draw(g);

        if (messageToUser) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesNewRoman", Font.BOLD, 25));
            String message = "Be careful! Floor can disappear!";
            
            // Center the text
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(message);
            int x = (1000 - textWidth) / 2; 
            int y = 100; 
            
            g.drawString(message, x, y);
        }
        
    }
}
