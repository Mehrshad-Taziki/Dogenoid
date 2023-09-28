package Graphics.Objects;

import javax.swing.*;

public class Paddle {
    private static final ImageIcon PADDLE_ICON = new ImageIcon("src/main/resources/Images/Paddle.png");
    private boolean dizzy;
    private int paddleX;
    private int paddleY;
    private int PADDLE_WIDTH = 150; //150
    private int PADDLE_HEIGHT = 25;

    public Paddle(int X, int Y) {
        this.paddleX = X;
        this.paddleY = Y;
        dizzy = false;
    }

    public int getPaddleX() {
        return paddleX;
    }

    public int getPaddleY() {
        return paddleY;
    }

    public int getPaddleCenter(){
        return paddleX + PADDLE_WIDTH/2;
    }

    public void moveR() {
        if (!isDizzy()) {
            paddleX += 50; //50
            if (paddleX > 800 - PADDLE_WIDTH) {
                paddleX = 800 - PADDLE_WIDTH;
            }
        } else {
            paddleX -= 50;
            if (paddleX < 0) {
                paddleX = 0;
            }
        }
    }

    public void moveL() {
        if (!isDizzy()) {
            paddleX -= 50;
            if (paddleX < 0) {
                paddleX = 0;
            }
        } else {
            paddleX += 50;
            if (paddleX > 800 - PADDLE_WIDTH) {
                paddleX = 800 - PADDLE_WIDTH;
            }
        }
    }

    public void reset() {
        paddleX = 350;
        paddleY = 850;
        PADDLE_WIDTH = 150;
        PADDLE_HEIGHT = 25;
        dizzy = false;
    }

    public void expand() {
        PADDLE_WIDTH = 200;
    }

    public void shrink() {
        PADDLE_WIDTH = 100;
    }

    public void resetSize() {
        PADDLE_WIDTH = 150;
    }

    public boolean isDizzy() {
        return dizzy;
    }

    public void setDizzy(boolean dizzy) {
        this.dizzy = dizzy;
    }

    public int WIDTH() {
        return PADDLE_WIDTH;
    }

    public int HEIGHT() {
        return PADDLE_HEIGHT;
    }

    public ImageIcon getPaddleIcon() {
        return PADDLE_ICON;
    }
}
