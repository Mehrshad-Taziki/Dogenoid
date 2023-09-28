package Graphics.Objects;

import javax.swing.*;

enum Speed {
    FAST,
    NORMAL,
    SLOW
}

public class Ball {
    private final int BALL_WIDTH = 25;
    private int velocity = 12;
    private int ballX;
    private int ballY;
    private double ballRadX;
    private double ballRadY;
    private boolean fireBall;
    private static ImageIcon ballIcon;
    private Speed speed;


    public Ball() {
        ballX = 410;
        ballY = 825;
        ballRadX = 0;
        ballRadY = 0;
        fireBall = false;
        ballIcon = new ImageIcon("src/main/resources/Images/Doge");
        speed = Speed.NORMAL;
    }

    public Ball(int X, int Y, double radX, double radY) {
        ballX = X;
        ballY = Y;
        ballRadX = radX;
        ballRadY = radY;
        fireBall = false;
        ballIcon = new ImageIcon("src/main/resources/Images/Doge");
        speed = Speed.NORMAL;
    }

    public int getBallX() {
        return ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public double getBallRadX() {
        return ballRadX;
    }

    public void reverseBallRadX() {
        ballRadX = -ballRadX;
    }

    public double getBallRadY() {
        return ballRadY;
    }

    public void reverseBallRadY() {
        ballRadY = -ballRadY;
    }

    public int WIDTH() {
        return BALL_WIDTH;
    }

    public int HEIGHT() {
        int BALL_HEIGHT = 25;
        return BALL_HEIGHT;
    }

    public void move() {
        if (speed == Speed.FAST) {
            ballX += (ballRadX * 1.5);
            ballY += (ballRadY * 1.5);
        } else if (speed == Speed.NORMAL) {
            ballX += ballRadX;
            ballY += ballRadY;
        } else {
            ballX += ballRadX / 2;
            ballY += ballRadY / 2;
        }
    }

    public void type1() {
        ballRadY = -7.0;
        ballRadX = -10.0;
    }

    public void type2() {
        ballRadX = -7.0;
        ballRadY = -10.0;
    }

    public void type3() {
        ballRadX = 7.0;
        ballRadY = -10.0;
    }

    public void type4() {
        ballRadY = -7.0;
        ballRadX = 10.0;
    }

    private double calculateRadY(double radX) {
        return Math.sqrt(velocity * velocity - radX * radX);
    }

    public void fixVelocity(int paddleCenter, int paddleSize) {
        double rate = (Math.abs(ballRadX) * 4.0) / paddleSize;
        if (center() > paddleCenter) {
            this.ballRadX += rate * (center() - paddleCenter);
        } else {
            this.ballRadX -= rate * (paddleCenter - center());
        }
        if (0 < ballRadX && ballRadX < 2) {
            ballRadX = 2;
        } else if (0 >= ballRadX && ballRadX > -2) {
            ballRadX = -2;
        } else if (ballRadX > velocity - 2) {
            ballRadX = velocity - 2;
        } else if (ballRadX < 2 - velocity) {
            ballRadX = 2 - velocity;
        }
        this.ballRadY = -calculateRadY(ballRadX);
    }


    public void fast() {
        speed = Speed.FAST;
    }

    public void slow() {
        speed = Speed.SLOW;
    }

    public void normal() {
        speed = Speed.NORMAL;
    }

    public ImageIcon getBallIcon() {
        return ballIcon;
    }

    public boolean isFireBall() {
        return fireBall;
    }

    public void setFireBall(boolean fireBall) {
        this.fireBall = fireBall;
        if (this.fireBall) {
            ballIcon = new ImageIcon("src/main/resources/Images/5-removebg-preview.png");
        } else {
            ballIcon = new ImageIcon("src/main/resources/Images/Doge");
        }
    }

    private double center() {
        return ballX + BALL_WIDTH / 2.0;
    }
}