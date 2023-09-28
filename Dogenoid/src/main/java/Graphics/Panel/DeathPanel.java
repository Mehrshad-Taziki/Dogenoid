package Graphics.Panel;

import Graphics.GraphicalAgent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DeathPanel extends JPanel implements ActionListener {
    private final GraphicalAgent graphicalAgent;
    private final JButton restartButton;
    private final JButton mainMenuButton;
    private final int score;
    private final String time;

    public DeathPanel(GraphicalAgent graphicalAgent, int score, String time) {
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(new Dimension(820, 1000));
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        restartButton.setBounds(310, 450, 200, 100);
        restartButton.addActionListener(this);
        restartButton.setFocusable(false);
        mainMenuButton = new JButton("MainMenu");
        mainMenuButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        mainMenuButton.setBounds(310, 570, 200, 100);
        mainMenuButton.addActionListener(this);
        mainMenuButton.setFocusable(false);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.add(restartButton);
        this.add(mainMenuButton);
        this.graphicalAgent = graphicalAgent;
        this.score = score;
        this.time = time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            graphicalAgent.startGame();
        }
        else if (e.getSource() == mainMenuButton){
            graphicalAgent.mainMenu();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 85));
        g.setColor(Color.white);
        g.drawString("GameOver", 240, 300);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        g.drawString("Time " + time, 340, 410);
        g.drawString("Score : " + score, 345, 360);
        requestFocus();
        //todo if high score
    }
}
