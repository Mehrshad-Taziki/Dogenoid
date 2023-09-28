package Graphics.Objects.PowerUps;

import javax.swing.*;

public class Expand extends PowerUp{
    public Expand(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.EXPAND;
        passive = false;
        String imagePath = "src/main/resources/Images/Expand.png";
        powerIcon = new ImageIcon(imagePath);
    }
    @Override
    public String toString() {
        return "Expand";
    }
}
