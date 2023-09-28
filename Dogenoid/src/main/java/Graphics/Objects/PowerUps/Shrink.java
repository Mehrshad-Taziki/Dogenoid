package Graphics.Objects.PowerUps;

import javax.swing.*;

public class Shrink extends PowerUp{
    public Shrink(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.SHRINK;
        passive = false;
        String imagePath = "src/main/resources/Images/Small.png";
        powerIcon = new ImageIcon(imagePath);
    }
    @Override
    public String toString() {
        return "Shrink";
    }
}
