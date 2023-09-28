package Graphics.Objects.Bricks;

import Graphics.Objects.PowerUps.*;

import javax.swing.*;
import java.util.Random;

public class PowerUpBrick extends Brick {
    private final PowerUp powerUp;

    public PowerUpBrick(int brickX, int brickY) {
        super(brickX, brickY);
        String imagePath = "src/main/resources/Images/PowerUp.png";
        brickIcon = new ImageIcon(imagePath);
        powerUp = makePowerUp();
    }

    private PowerUp makePowerUp() {
        Random rand = new Random();
        int random = rand.nextInt(14);
        switch (random) {
            case 0:
                return new Shrink(0, 0, 10);
            case 1:
            case 2:
                return new Expand(0, 0, 10);
            case 3:
            case 4:
                return new ThreeBall(0, 0, 0);
            case 5:
            case 6:
                return new FireBall(0, 0, 10);
            case 7:
            case 8:
                return new Fast(0, 0, 10);
            case 9:
            case 10:
                return new Slow(0, 0, 10);
            case 11:
                return new Dizzy(0, 0, 10);
            case 12:
            case 13:
                return new RandomPrize(0, 0, 10);
        }
        return new RandomPrize(0, 0, 10);
    }

    public String toString() {
        return "PowerUp";
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
