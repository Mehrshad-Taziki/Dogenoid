package Graphics.Panel;

import Graphics.GraphicalAgent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LoadPanel extends JPanel implements ActionListener, ChangeListener {
    private final GraphicalAgent graphicalAgent;
    JSlider slider;
    ArrayList<String> saves;
    int GAME_WIDTH = 820;
    int GAME_HEIGHT = 1000;
    JButton exitButton;
    JLabel[] saveLabels;

    public LoadPanel(GraphicalAgent graphicalAgent, ArrayList<String> saves) {
        this.graphicalAgent = graphicalAgent;
        this.saves = saves;
        this.slider = new JSlider();
        saveLabels = new JLabel[10];
        loadLabel(saveLabels);
        slider.setOrientation(SwingConstants.VERTICAL);
        slider.setInverted(true);
        slider.setFocusable(false);
        slider.setBackground(Color.DARK_GRAY);
        slider.addChangeListener(this);
        slider.setValue(0);
        slider.setBounds(50, 100 , 50 , 500);
        slider.setVisible(true);
        slide();
        if (saves.size() < 10) {
            slider.setEnabled(false);
        } else {
            slider.setMinimum(0);
            slider.setMaximum(saves.size() - 10);
            slider.setEnabled(true);
        }
        this.setLayout(null);
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        exitButton = new JButton("Back");
        exitButton.setFont(new Font("Ink Free", Font.PLAIN, 30));
        exitButton.setBounds(310, 690, 200, 100);
        exitButton.addActionListener(this);
        exitButton.setFocusable(false);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.add(exitButton);
        this.add(slider);
        this.setVisible(true);
    }

    private void loadLabel(JLabel[] saveLabels) {
        for (int i = 0; i < 10; i++) {
            saveLabels[i] = new JLabel();
            saveLabels[i].setBounds(100, 100 + 50 * i, 600, 50);
            saveLabels[i].setFont(new Font("", Font.BOLD, 25));
            saveLabels[i].setOpaque(true);
            saveLabels[i].setVisible(true);
            if (i%2 == 0){
                saveLabels[i].setBackground(Color.GRAY);
            } else {
                saveLabels[i].setBackground(Color.white);
            }
            saveLabels[i].setLayout(new BorderLayout());
            this.add(saveLabels[i]);
        }
        for(int i =0; i < 10; i++) {
            final int p = i;
            saveLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!saveLabels[p].getText().equals("")) {
                        graphicalAgent.loadGame(saveLabels[p].getText());
                    }
                }
            });
        }
    }

    private void slide() {
        int index = slider.getValue();
        for (int i = index; i < index + 10; i++) {
            if (saves.size() > i) {
                saveLabels[i - index].setText(saves.get(i));
                saveLabels[i-index].setVerticalTextPosition(JLabel.CENTER);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton){
            graphicalAgent.mainMenu();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        slide();
    }

}
