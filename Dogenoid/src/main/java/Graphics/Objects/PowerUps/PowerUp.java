package Graphics.Objects.PowerUps;

import javax.swing.*;

public class PowerUp {
    protected PowerUps powerName;
    protected int powerX;
    protected int powerY;
    protected boolean passive;
    protected int time;
    protected ImageIcon powerIcon;

    public void decreaseTime(){
        time--;
    }

    public PowerUp(int powerX, int powerY, int time) {
        this.time = time;
        this.powerX = powerX;
        this.powerY = powerY;
    }

    public void setLocation(int powerX, int powerY){
        this.powerX = powerX;
        this.powerY = powerY + 25;
    }

    public PowerUps getPowerName() {
        return powerName;
    }

    public int getPowerX() {
        return powerX;
    }

    public int getPowerY() {
        return powerY;
    }

    public boolean isPassive() {
        return passive;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public ImageIcon getPowerIcon() {
        return powerIcon;
    }

    public void reset(){
        this.time = 10;
    }

    public void moveDown(){
        powerY += 20;
    }
    @Override
    public String toString() {
        return "PowerUp";
    }
}
