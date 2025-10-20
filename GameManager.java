
import java.awt.CardLayout;
import javax.swing.*;

public class GameManager { // This is the central manager!
    private JFrame frame;
    private CardLayout cardLayout; // for swapping JPanels
    private JPanel mainContainer; // Holds all JPanels
    private int highestLevelCompleted = 0;
    
    // all levels here
    private GamePanel level1Panel; 
    private GamePanel level2Panel;
    private GamePanel level3Panel;

    private LevelCompleted winScreen; 
    private LevelFailed loseScreen;
    
    // --- Constructor ---
    public GameManager() {
        frame = new JFrame("Level Devil");
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        
        // 1. Create ALL screens and add them to the main container
        
        // Create and add GamePanel (Level 1)
        level1Panel = new GamePanel(this);
        level1Panel.setName("LEVEL_1");
        mainContainer.add(level1Panel, "LEVEL_1");

        //level numero 2
        level2Panel = new GamePanel(this);
        level2Panel.setName("LEVEL_2");
        mainContainer.add(level2Panel, "LEVEL_2");
        
        //level numero 3
        level3Panel = new GamePanel(this);
        level3Panel.setName("LEVEL_3");
        mainContainer.add(level3Panel, "LEVEL_3");

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
        frame.setResizable(false);
        
        // NOTE: We load data once here, so it's ready when requested.
        level1Panel.load(new Level1());
        level2Panel.load(new Level2());
        level3Panel.load(new Level3());
        
        // 3. Start on the START MENU!
        // FIX: Switch the initial screen from "MAIN_MENU" to "START_MENU".
        switchToScreen("START_MENU");
        
        frame.setVisible(true); 
    }

    // Flow Control Methods
    
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
        // levels
        
        level1Panel.stopLevelLoop(); 
        level2Panel.stopLevelLoop();
        level3Panel.stopLevelLoop();
        
        String levelKey = "LEVEL_" + levelNumber;
        GamePanel panelToStartLevel = null;

        //  Load the specific level data into the GamePanel instance
        // Since we already loaded it in the constructor, we just player reset here:
        if (levelNumber == 1) {
            level1Panel.load(new Level1()); 
            panelToStartLevel = level1Panel;
        } else if (levelNumber == 2) {
            level2Panel.load(new Level2());
            panelToStartLevel = level2Panel;
        } else if (levelNumber == 3) {
            level3Panel.load(new Level3());
            panelToStartLevel = level3Panel;
        }
        
        switchToScreen(levelKey);
        
        if (panelToStartLevel != null) {
            panelToStartLevel.startLevelLoop();
        }
    }
    
    public void levelCompleted(int levelNumber) {
        // stopping otherwise it gives win or lose wrong
        level1Panel.stopLevelLoop(); 
        level2Panel.stopLevelLoop(); 
        level3Panel.stopLevelLoop(); 
        if (levelNumber > highestLevelCompleted) {
            highestLevelCompleted = levelNumber;
        }
        switchToScreen("WIN_SCREEN");
    }

    public void levelFailed() {
        // tada
        level1Panel.stopLevelLoop(); 
        level2Panel.stopLevelLoop(); 
        level3Panel.stopLevelLoop(); 
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
