package Graphics.Objects.PowerUps;

import javax.swing.*;

public class Slow extends PowerUp{
    public Slow(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.SLOW;
        passive = false;
        String imagePath = "src/main/resources/Images/Slow.png";
        powerIcon = new ImageIcon(imagePath);
    }
    @Override
    public String toString() {
        return "Slow";
    }
}
