import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

// Level.java
/**
 *  ra.
 */
public interface Level {
    void init(Player player); // Initialize the level with the player object

    void update(float delta, Player player, boolean jumpQueued, GameManager controller); // Core game logic
    
    void draw(Graphics2D g); // Drawing logic
    
    int getLevelID();
}
