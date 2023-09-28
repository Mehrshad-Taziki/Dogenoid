package Graphics;

import Graphics.Panel.*;
import Logic.ModelLoader;

import javax.swing.*;

public class GraphicalAgent {
    ModelLoader modelLoader;
    GameFrame gameFrame;
    GamePanel gamePanel;
    String username;

    public GraphicalAgent() {
        modelLoader = new ModelLoader(this);
        username = getPlayerNames();
        gameFrame = new GameFrame();
        gameFrame.add(new MainPanel(this));
        gameFrame.setVisible(true);
    }

    public void startGame() {
//        gameFrame.setVisible(false);
        gameFrame.getContentPane().removeAll();
        this.gamePanel = new GamePanel(this);
        gameFrame.setContentPane(this.gamePanel);
        gameFrame.getContentPane().repaint();
//        gameFrame.setVisible(true);
    }

    public void gameOver(int score, String time) {
        gameFrame.getContentPane().removeAll();
        gameFrame.setContentPane(new DeathPanel(this, score, time));
        gameFrame.getContentPane().repaint();
        modelLoader.savePlayer(username, score);
    }

    public void leaderBoard() {
        String ranking = modelLoader.getLeaderBoard(username);
        JOptionPane.showMessageDialog(gameFrame, ranking, "LeaderBoard", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mainMenu() {
        gameFrame.getContentPane().removeAll();
        gameFrame.setContentPane(new MainPanel(this));
        gameFrame.getContentPane().repaint();
    }

    public void loadPanel() {
        gameFrame.getContentPane().removeAll();
        gameFrame.setContentPane(new LoadPanel(this, modelLoader.saveNames(username)));
        gameFrame.getContentPane().repaint();
    }

    public void loadGame(GamePanel gamePanel) {
        gameFrame.getContentPane().removeAll();
        this.gamePanel = gamePanel;
        gameFrame.setContentPane(this.gamePanel);
        gameFrame.getContentPane().repaint();
    }

    public void loadGame(String saveName) {
        gameFrame.getContentPane().removeAll();
        this.gamePanel = modelLoader.loadGame(username, saveName);
        gameFrame.setContentPane(gamePanel);
        gameFrame.getContentPane().repaint();
    }

    public void pause(GamePanel gamePanel) {
        gameFrame.getContentPane().removeAll();
        gameFrame.setContentPane(new PausePanel(this, gamePanel));
        gameFrame.getContentPane().repaint();
    }

    public void save(GamePanel gamePanel) {
        String saveName = getSaveName();
        modelLoader.saveGame(username, saveName, gamePanel);
    }

    public String getPlayerNames() {
        String name = "";
        while (name == null || name.length() == 0) {
            name = JOptionPane.showInputDialog(null, "Enter Your Name ");
        }
        modelLoader.loadPlayer(name);
        return name;
    }

    public String getSaveName() {
        String name = JOptionPane.showInputDialog(null, "Enter Save Name ");
        if (name != null) {
            if (!name.equals("")) {
                if (modelLoader.saveExist(username, name)) {
                    int reply = JOptionPane.showConfirmDialog(null, "Do you Want to OverRide This Save?", "Save", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.NO_OPTION) {
                        return null;
                    }
                }
                return name;
            }
        }
        return null;
    }

    public String getGameName() {
        String name = JOptionPane.showInputDialog(null, "Enter Save Name ");
        if (name != null) {
            if (!name.equals("")) {
                if (modelLoader.saveExist(username, name)) {
                    return name;
                }
            }
        }
        return null;
    }
}
