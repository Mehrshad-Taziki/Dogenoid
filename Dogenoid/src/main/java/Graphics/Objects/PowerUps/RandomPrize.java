package Graphics.Objects.PowerUps;

import javax.swing.*;
import java.util.Random;

public class RandomPrize extends PowerUp{
    public RandomPrize(int powerX, int powerY, int time) {
        super(powerX, powerY, time);
        powerName = PowerUps.RANDOM;
        passive = false;
        String imagePath = "src/main/resources/Images/Random.png";
        powerIcon = new ImageIcon(imagePath);
    }

    public PowerUp getPowerUp(){
        Random rand = new Random();
        int random = rand.nextInt(10);
        switch (random) {
            case 0:
                return new Shrink(0, 0, 10);
            case 1:
            case 2:
                return new Expand(0, 0, 10);
            case 3:
            case 4:
                return new FireBall(0, 0, 10);
            case 5:
            case 6:
                return new Fast(0, 0, 10);
            case 7:
            case 8:
                return new Slow(0, 0, 10);
            case 9:
                return new Dizzy(0, 0, 10);
        }
        return new ThreeBall(0, 0, 10);
    }
    @Override
    public String toString() {
            return "Random";
    }
}
