package Graphics.Objects.Bricks;

import javax.swing.*;

public class WoodenBrick extends Brick{
    public WoodenBrick(int brickX, int brickY, int health) {
        super(brickX, brickY);
        this.health = health;
        String imagePath;
        if (health > 1) {
            imagePath = "src/main/resources/Images/Wooden.png";
        }
        else {
            imagePath = "src/main/resources/Images/WoodenBroke.png";
        }
        brickIcon = new ImageIcon(imagePath);
    }
    @Override
    public void collision(){
        String imagePath = "src/main/resources/Images/WoodenBroke.png";
        brickIcon = new ImageIcon(imagePath);
        health--;
    }

    public String toString() {
        return "Wooden";
    }

}
