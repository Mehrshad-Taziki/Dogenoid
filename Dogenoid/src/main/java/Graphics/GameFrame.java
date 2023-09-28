package Graphics;


import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        super();
        this.setTitle("DogeNoid");
        this.setSize(820, 1000);
        ImageIcon dogeIcon = new ImageIcon("src/main/resources/Images/Doge");
        this.setIconImage(dogeIcon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
}
