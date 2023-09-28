package Graphics.Panel;

import Graphics.GraphicalAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel implements ActionListener {
    GraphicalAgent graphicalAgent;
    int GAME_WIDTH = 820;
    int GAME_HEIGHT = 1000;
    JButton playButton;
    JButton leaderboardButton;
    JButton exitButton;
    JButton loadGameButton;

    public MainPanel(GraphicalAgent graphicalAgent) {
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        playButton = new JButton("play");
        playButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        playButton.setBounds(310, 330, 200, 100);
        playButton.addActionListener(this);
        playButton.setFocusable(false);
        leaderboardButton = new JButton("LeaderBoard");
        leaderboardButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        leaderboardButton.setBounds(310, 450, 200, 100);
        leaderboardButton.addActionListener(this);
        leaderboardButton.setFocusable(false);
        loadGameButton = new JButton("Load Game");
        loadGameButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        loadGameButton.setBounds(310, 570, 200, 100);
        loadGameButton.addActionListener(this);
        loadGameButton.setFocusable(false);
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        exitButton.setBounds(310, 690, 200, 100);
        exitButton.addActionListener(this);
        exitButton.setFocusable(false);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.add(playButton);
        this.add(leaderboardButton);
        this.add(loadGameButton);
        this.add(exitButton);
        this.graphicalAgent = graphicalAgent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == leaderboardButton) {
            graphicalAgent.leaderBoard();
        } else if (e.getSource() == playButton) {
            graphicalAgent.startGame();
        } else if (e.getSource() == loadGameButton) {
            System.out.println("1");
            graphicalAgent.loadPanel();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Ink Free", Font.PLAIN, 100));
        g.setColor(Color.white);
        g.drawString("DogeNoid" , 230, 290);
        requestFocus();
    }
}

