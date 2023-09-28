package Graphics.Objects.Bricks;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Brick {
    protected int brickX;
    protected int brickY;
    protected int health;
    protected boolean visible;
    protected boolean breakable;
    protected ImageIcon brickIcon;


    public Brick(int brickX, int brickY) {
        this.brickX = brickX;
        this.brickY = brickY;
        Random rand = new Random();
        String imagePath = "src/main/resources/Images/Brick" + (rand.nextInt(8) + 1) + ".png";
        brickIcon = new ImageIcon(imagePath);
        health = 1;
        visible = true;
        breakable = true;
    }

    private static Brick makeBrick(int brickX) {
        Random rand = new Random();
        int random = rand.nextInt(10);
        switch (random) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new Brick(brickX, 25);
            case 4:
            case 5:
                return new WoodenBrick(brickX, 25, 2);
            case 6:
            case 7:
                return new BlinkingBrick(brickX, 25);
            case 8:
                return new PowerUpBrick(brickX, 25);
            default:
                return new InvisibleBrick(brickX, 25);
        }
    }

    public static void loadBricks(ArrayList<Brick> bricks, int type) {
        if (type == 400) {
            bricks.add(makeBrick(13));
            bricks.add(makeBrick(113));
            bricks.add(makeBrick(213));
            bricks.add(makeBrick(313));
            bricks.add(makeBrick(413));
            bricks.add(makeBrick(513));
            bricks.add(makeBrick(613));
            bricks.add(makeBrick(713));
        } else if (type == 0) {
            bricks.add(makeBrick(63));
            bricks.add(makeBrick(163));
            bricks.add(makeBrick(263));
            bricks.add(makeBrick(363));
            bricks.add(makeBrick(463));
            bricks.add(makeBrick(563));
            bricks.add(makeBrick(663));
        }
    }

    public void moveDown() {
        brickY += 50;
    }

    public void collision() {
        health--;
    }

    public int getHealth() {
        return health;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public int getBrickX() {
        return brickX;
    }

    public int getBrickY() {
        return brickY;
    }

    public ImageIcon getBrickIcon() {
        return brickIcon;
    }

    public String toString() {
        return "Brick";
    }

}
