package Graphics.Panel;

import Graphics.GraphicalAgent;
import Graphics.Objects.Ball;
import Graphics.Objects.Bricks.Brick;
import Graphics.Objects.Paddle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PausePanel extends JPanel implements ActionListener {
    GraphicalAgent graphicalAgent;
    int GAME_WIDTH = 820;
    int GAME_HEIGHT = 1000;
    GamePanel gamePanel;
    JButton resumeButton;
    JButton saveButton;
    JButton mainMenuButton;
    JButton restartButton;


    public PausePanel(GraphicalAgent graphicalAgent, GamePanel gamePanel) {
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.graphicalAgent = graphicalAgent;
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.gamePanel = gamePanel;
        resumeButton = new JButton("Resume");
        resumeButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        resumeButton.setBounds(310, 330, 200, 100);
        resumeButton.addActionListener(this);
        resumeButton.setFocusable(false);
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        restartButton.setBounds(310, 450, 200, 100);
        restartButton.addActionListener(this);
        restartButton.setFocusable(false);
        saveButton = new JButton("Save");
        saveButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        saveButton.setBounds(310, 570, 200, 100);
        saveButton.addActionListener(this);
        saveButton.setFocusable(false);
        mainMenuButton = new JButton("MainMenu");
        mainMenuButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        mainMenuButton.setBounds(310, 690, 200, 100);
        mainMenuButton.addActionListener(this);
        mainMenuButton.setFocusable(false);
        this.add(resumeButton);
        this.add(restartButton);
        this.add(saveButton);
        this.add(mainMenuButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resumeButton) {
            graphicalAgent.loadGame(this.gamePanel);
        } else if (e.getSource() == saveButton) {
            graphicalAgent.save(this.gamePanel);
        } else if (e.getSource() == mainMenuButton) {
            graphicalAgent.mainMenu();
        }else if (e.getSource() == restartButton) {
            graphicalAgent.startGame();
        }
    }

}
