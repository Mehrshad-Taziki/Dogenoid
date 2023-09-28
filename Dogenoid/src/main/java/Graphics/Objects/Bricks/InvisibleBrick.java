package Graphics.Objects.Bricks;

public class InvisibleBrick extends Brick {
    public InvisibleBrick(int brickX, int brickY) {
        super(brickX, brickY);
        visible = false;
    }
    public String toString() {
        return "Invisible";
    }

}
