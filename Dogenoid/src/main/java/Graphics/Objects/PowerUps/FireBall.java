package Graphics.Objects.PowerUps;

import javax.swing.*;

public class FireBall extends PowerUp{
    public FireBall(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.FIREBALL;
        passive = false;
        String imagePath = "src/main/resources/Images/FireBall.png";
        powerIcon = new ImageIcon(imagePath);
    }
    @Override
    public String toString() {
        return "FireBall";
    }
}
