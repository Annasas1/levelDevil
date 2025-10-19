import java.awt.CardLayout;
import javax.swing.*;

public class GameManager { // This is the central manager!
    private JFrame frame;
    private CardLayout cardLayout; // for swapping JPanels
    private JPanel mainContainer; // Holds all JPanels
    private int highestLevelCompleted = 0;
    
    // Use a single reference for the GamePanel instance that will run Level 1
    private Gamepanel level1Panel; 

    private LevelCompleted winScreen; 
    private LevelFailed loseScreen;
    
    // --- Constructor ---
    public GameManager() {
        frame = new JFrame("Level Devil");
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        
        // 1. Create ALL screens and add them to the main container
        
        // A. Create and add GamePanel (Level 1)
        level1Panel = new Gamepanel(this);
        level1Panel.setName("LEVEL_1");
        mainContainer.add(level1Panel, "LEVEL_1");
        
        // B. Create and add Win/Lose screens
        winScreen = new LevelCompleted(this); 
        loseScreen = new LevelFailed(this); 
        mainContainer.add(winScreen, "WIN_SCREEN");
        mainContainer.add(loseScreen, "LOSE_SCREEN");
        
        // C. Create and add Main Menu
        MainMenu mainMenuPanel = new MainMenu(this); 
        mainContainer.add(mainMenuPanel, "MAIN_MENU");
        
        // D. Create and add START MENU (New addition, as this is the game's start screen)
        StartMenu startMenuPanel = new StartMenu(this);
        startMenuPanel.setName("START_MENU");
        mainContainer.add(startMenuPanel, "START_MENU");

        // 2. Initial Setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.add(mainContainer);
        
        // NOTE: We load Level 1 data once here, so it's ready when requested.
        level1Panel.load(new Level1());
        
        // 3. Start on the START MENU!
        // FIX: Switch the initial screen from "MAIN_MENU" to "START_MENU".
        switchToScreen("START_MENU");
        
        frame.setVisible(true); 
    }

    // --- Flow Control Methods (No changes needed here) ---
    
    public void switchToScreen(String screenName) {
        cardLayout.show(mainContainer, screenName);
        
        // Logic to ensure the GamePanel receives keyboard focus
        if (screenName.startsWith("LEVEL")) {
            for (java.awt.Component comp : mainContainer.getComponents()) {
                if (screenName.equals(comp.getName())) { 
                    comp.requestFocusInWindow();
                    break;
                }
            }
        }

          if (screenName.equals("MAIN_MENU")) {
            for (java.awt.Component comp : mainContainer.getComponents()) {
                if (comp instanceof MainMenu) { 
                    ((MainMenu) comp).refreshLevelButtons();
                    break;
                }
            }
        }
    }
    
    public void startLevel(int levelNumber) {
        // We only have level1Panel right now, so this handles only level 1.
        
        // 1. Stop any currently running loop before switching
        level1Panel.stopLevelLoop(); 
        
        String levelKey = "LEVEL_" + levelNumber;
        
        // 2. Load the specific level data into the GamePanel instance
        // Since we already loaded it in the constructor, we just ensure player reset here:
        if (levelNumber == 1) {
             // Calling load again runs the Level1.init(player) which resets the player position.
            level1Panel.load(new Level1()); 
        }
        
        // 3. Switch the display
        switchToScreen(levelKey);
        
        // 4. Start the game loop
        level1Panel.startLevelLoop(); 
    }
    
    public void levelCompleted(int levelNumber) {
        // ... (No changes) ...
        level1Panel.stopLevelLoop(); 
        if (levelNumber > highestLevelCompleted) {
            highestLevelCompleted = levelNumber;
        }
        switchToScreen("WIN_SCREEN");
    }

    public void levelFailed() {
        // ... (No changes) ...
        level1Panel.stopLevelLoop(); 
        switchToScreen("LOSE_SCREEN");
    }

    public int getHighestLevelCompleted() {
        return highestLevelCompleted;
    }
    
    // --- Main Method ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameManager());
    }
}
