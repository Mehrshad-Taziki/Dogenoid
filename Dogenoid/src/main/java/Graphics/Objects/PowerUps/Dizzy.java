package Graphics.Objects.PowerUps;

import javax.swing.*;

public class Dizzy extends PowerUp {
    public Dizzy(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.DIZZY;
        passive = false;
        String imagePath = "src/main/resources/Images/Dizzy.png";
        powerIcon = new ImageIcon(imagePath);
    }

    @Override
    public String toString() {
        return "Dizzy";
    }
}
