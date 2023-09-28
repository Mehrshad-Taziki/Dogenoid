package Graphics.Objects.PowerUps;

import javax.swing.*;

public class Fast extends PowerUp{
    public Fast(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.FAST;
        passive = false;
        String imagePath = "src/main/resources/Images/Fast.png";
        powerIcon = new ImageIcon(imagePath);
    }
    @Override
    public String toString() {
        return "Fast";
    }
}
