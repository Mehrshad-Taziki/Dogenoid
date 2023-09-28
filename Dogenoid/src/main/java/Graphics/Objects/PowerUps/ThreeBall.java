package Graphics.Objects.PowerUps;

import javax.swing.*;

public class ThreeBall extends PowerUp{

    public ThreeBall(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        this.time = 0;
        powerName = PowerUps.THREEBALL;
        passive = true;
        String imagePath = "src/main/resources/Images/3Balls.png";
        powerIcon = new ImageIcon(imagePath);
    }
    @Override
    public String toString() {
        return "ThreeBall";
    }
}
