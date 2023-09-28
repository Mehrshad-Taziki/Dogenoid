package Graphics.Objects.Bricks;

public class BlinkingBrick extends Brick{

    public BlinkingBrick(int brickX, int brickY) {
        super(brickX, brickY);
    }

    public void blink(){
        visible = !visible;
        breakable = !breakable;
    }

    @Override
    public String toString() {
        return "Blink";
    }

}
