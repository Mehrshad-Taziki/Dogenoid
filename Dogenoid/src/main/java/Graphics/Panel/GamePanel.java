package Graphics.Panel;

import Graphics.GraphicalAgent;
import Graphics.Objects.Ball;
import Graphics.Objects.Bricks.BlinkingBrick;
import Graphics.Objects.Bricks.Brick;
import Graphics.Objects.Bricks.PowerUpBrick;
import Graphics.Objects.Paddle;
import Graphics.Objects.PowerUps.PowerUp;
import Graphics.Objects.PowerUps.PowerUps;
import Graphics.Objects.PowerUps.RandomPrize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    private final GraphicalAgent graphicalAgent;
    private final int unit = 25;
    private final Paddle paddle;
    private final ArrayList<Ball> balls;
    private final ArrayList<Brick> bricks;
    private final ArrayList<PowerUp> powerUps;
    private final ArrayList<PowerUp> activePowerUps;
    private final Timer gameTimer;
    private final int delay = 15;
    private final int GAME_WIDTH = 800;
    private final int GAME_HEIGHT = 1000;
    private final ImageIcon backGround = new ImageIcon("src/main/resources/Images/galaxy.jpg");
    boolean gameOver;
    int timePassed = 0;
    int secondSurvived;
    int minuteSurvived;
    int score;
    int health;
    boolean started = false;

    public GamePanel(GraphicalAgent graphicalAgent) {
        this.setVisible(true);
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.paddle = new Paddle(350, 850);
        balls = new ArrayList<>();
        bricks = new ArrayList<>();
        powerUps = new ArrayList<>();
        activePowerUps = new ArrayList<>();
        balls.add(new Ball());
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        gameTimer = new Timer(delay, this);
        Brick.loadBricks(bricks, 0);
        this.graphicalAgent = graphicalAgent;
        this.addMouseListener(this);
        this.gameOver = false;
        this.started = false;
        health = 3;
    }

    public GamePanel(GraphicalAgent graphicalAgent, ArrayList<Ball> balls, Paddle paddle, ArrayList<Brick> bricks, ArrayList<PowerUp> powerUps, ArrayList<PowerUp> activePowerUps, int secondSurvived, int minuteSurvived, int score, int health) {
        this.setVisible(true);
        this.setSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        this.setBackground(Color.BLACK);
        this.graphicalAgent = graphicalAgent;
        this.balls = balls;
        this.paddle = paddle;
        this.activePowerUps = activePowerUps;
        this.powerUps = powerUps;
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        this.addMouseListener(this);
        gameTimer = new Timer(delay, this);
        this.gameOver = false;
        this.bricks = bricks;
        this.secondSurvived = secondSurvived;
        this.minuteSurvived = minuteSurvived;
        this.score = score;
        this.started = false;
        this.health = health;
        useActivePowerUps();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backGround.getImage(), 0, 0, 800, 1000, null);
        drawBall(g);
        drawPaddle(g);
        drawBricks(g);
        drawPowerUps(g);
        drawState(g);
        requestFocus();
        g.dispose();
    }

    private void drawBall(Graphics g) {
        for (Ball ball :
                balls) {
            int ballX = ball.getBallX();
            int ballY = ball.getBallY();
            g.drawImage(ball.getBallIcon().getImage(), ballX, ballY, ball.WIDTH(), ball.HEIGHT(), null);
        }
    }

    private void drawPaddle(Graphics g) {
        int paddleX = paddle.getPaddleX();
        int paddleY = paddle.getPaddleY();
        g.setColor(Color.GREEN);
        g.drawImage(paddle.getPaddleIcon().getImage(), paddleX, paddleY, paddle.WIDTH(), paddle.HEIGHT(), null);

    }

    private void drawBricks(Graphics g) {
        for (Brick brick :
                bricks) {
            if (brick.isVisible()) {
                g.drawImage(brick.getBrickIcon().getImage(), brick.getBrickX(), brick.getBrickY(), unit * 3, unit, null);
            }
        }

    }

    private void drawPowerUps(Graphics g) {
        for (PowerUp powerUp :
                powerUps) {
            g.drawImage(powerUp.getPowerIcon().getImage(), powerUp.getPowerX(), powerUp.getPowerY(), 75, 15, null);
        }
    }

    private void drawState(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        g.drawString("Time " + getTime(), 100, 140);
        g.drawString("Score : " + score, 610, 140);
    }

    private void moveAllBricks() {
        for (Brick brick :
                bricks) {
            brick.moveDown();
        }
    }

    private void moveAllPowerUps() {
        for (PowerUp powerUp :
                powerUps) {
            powerUp.moveDown();
        }
    }

    private void collisionWithWall() {
        ListIterator<Ball> it = balls.listIterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (ball.getBallX() < 20) {
                if (ball.getBallRadX() < 0) {
                    ball.reverseBallRadX();
                }
            } else if (ball.getBallX() > GAME_WIDTH - 20) {
                if (ball.getBallRadX() > 0) {
                    ball.reverseBallRadX();
                }
            } else if (ball.getBallY() < 20) {
                if (ball.getBallRadY() < 0) {
                    ball.reverseBallRadY();
                }
            } else if (ball.getBallY() > GAME_HEIGHT - 20) {
                it.remove();
            }
        }
    }

    private void collisionWithPaddle() {
        for (Ball ball :
                balls) {
            if (new Rectangle(ball.getBallX(), ball.getBallY(), ball.WIDTH(), ball.HEIGHT()).intersects(new Rectangle(paddle.getPaddleX(), paddle.getPaddleY(), paddle.WIDTH(), paddle.HEIGHT()))) {
                if (ball.getBallY() < paddle.getPaddleY() && ball.getBallRadY() > 0) {
                    ball.fixVelocity(paddle.getPaddleCenter(), paddle.WIDTH());
                }
            }
        }
    }

    private void collisionWithPowerUp() {
        for (int i = 0; i < powerUps.size(); i++) {
            if (new Rectangle(powerUps.get(i).getPowerX(), powerUps.get(i).getPowerY(), 75, 15).intersects(new Rectangle(paddle.getPaddleX(), paddle.getPaddleY(), paddle.WIDTH(), paddle.HEIGHT()))) {
                usePowerUp(powerUps.get(i));
                powerUps.remove(i);
                return;
            }
        }
    }

    private void moveBalls() {
        for (Ball ball :
                balls) {
            ball.move();
        }
    }

    private void checkEvents() {
        if (timePassed % 66 == 0) {
            removePowerUps();
            nextSecond();
        }
        if (timePassed % 200 == 0) {
            blink();
        }
        if (timePassed % 400 == 0) {
            moveAllBricks();
            Brick.loadBricks(bricks, timePassed % 800);
        }
        if (timePassed % 30 == 0) {
            moveAllPowerUps();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && started) {
            timePassed++;
            checkEvents();
            collisionWithPaddle();
            collisionWithWall();
            collisionWithPowerUp();
            collisionWithBrick();
            checkForGameOver();
            moveBalls();
            repaint();
        }
    }

    private void playerDied() {
        health--;
        started = false;
        paddle.reset();
        balls.add(new Ball());
        if (health == 0) {
            gameOver = true;
            gameTimer.stop();
            graphicalAgent.gameOver(score, getTime());
        }
    }

    private void blink() {
        for (Brick brick :
                bricks) {
            if (brick.toString().equals("Blink")) {
                ((BlinkingBrick) brick).blink();
            }
        }
    }

    private void checkForGameOver() {
        if (balls.isEmpty()) {
            playerDied();
        }
        for (Brick brick :
                bricks) {
            if (brick.getBrickY() > 870) {
                gameOver = true;
                gameTimer.stop();
                graphicalAgent.gameOver(score, getTime());
            }
        }
    }

    private void collisionWithBrick() {
        int brickNumber = -1;
        Bricks:
        for (int i = bricks.size() - 1; i >= 0; i--) {
            if (bricks.get(i).isBreakable()) {
                for (Ball ball :
                        balls) {
                    if (new Rectangle(ball.getBallX(), ball.getBallY(), ball.WIDTH(), ball.HEIGHT()).intersects(new Rectangle(bricks.get(i).getBrickX(), bricks.get(i).getBrickY(), unit * 3, unit))) {
                        if (!ball.isFireBall()) {
                            ball.reverseBallRadY();
                        }
                        brickNumber = i;
                        break Bricks;
                    }
                }
            }
        }
        if (brickNumber >= 0) {
            bricks.get(brickNumber).collision();
            if (bricks.get(brickNumber).toString().equals("PowerUp")) {
                PowerUp powerUp = ((PowerUpBrick) (bricks.get(brickNumber))).getPowerUp();
                powerUp.setLocation(bricks.get(brickNumber).getBrickX(), bricks.get(brickNumber).getBrickY());
                powerUps.add(powerUp);
            }
            if (bricks.get(brickNumber).getHealth() == 0) {
                score++;
                bricks.remove(brickNumber);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && started) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                paddle.moveR();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                paddle.moveL();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                this.started = false;
                this.gameTimer.stop();
                graphicalAgent.pause(this);
            }
        }
    }

    private void useAllPowerUps(){
        ArrayList<PowerUp> savedPowerUps = new ArrayList<>(activePowerUps);
        activePowerUps.clear();
        for (PowerUp p :
                savedPowerUps) {
            usePowerUp(p);
        }
    }

    private void usePowerUp(PowerUp p) {
        for (PowerUp powerUp :
                activePowerUps) {
            if (powerUp.getPowerName() == p.getPowerName()) {
                powerUp.reset();
                return;
            }
        }
        if (p.getPowerName() == PowerUps.THREEBALL) {
            balls.add(new Ball(410, 600, 8.0, -9.0));
            balls.add(new Ball(410, 600, -6.0, -10.0));
        } else if (p.getPowerName() == PowerUps.RANDOM) {
            usePowerUp(((RandomPrize) p).getPowerUp());
        } else if (p.getPowerName() == PowerUps.EXPAND) {
            activePowerUps.add(p);
            paddle.expand();
        } else if (p.getPowerName() == PowerUps.SHRINK) {
            activePowerUps.add(p);
            paddle.shrink();
        } else if (p.getPowerName() == PowerUps.FIREBALL) {
            activePowerUps.add(p);
            for (Ball ball :
                    balls) {
                ball.setFireBall(true);
            }
        } else if (p.getPowerName() == PowerUps.DIZZY) {
            activePowerUps.add(p);
            paddle.setDizzy(true);
        } else if (p.getPowerName() == PowerUps.FAST) {
            activePowerUps.add(p);
            for (Ball ball :
                    balls) {
                ball.fast();
            }
        } else if (p.getPowerName() == PowerUps.SLOW) {
            activePowerUps.add(p);
            for (Ball ball :
                    balls) {
                ball.slow();
            }
        }
    }

    private void unUsePowerUp(PowerUp p) {
        if (p.getPowerName() == PowerUps.EXPAND || p.getPowerName() == PowerUps.SHRINK) {
            paddle.resetSize();
        } else if (p.getPowerName() == PowerUps.FIREBALL) {
            for (Ball ball :
                    balls) {
                ball.setFireBall(false);
            }
        } else if (p.getPowerName() == PowerUps.DIZZY) {
            paddle.setDizzy(false);
        } else if (p.getPowerName() == PowerUps.FAST || p.getPowerName() == PowerUps.SLOW) {
            for (Ball ball :
                    balls) {
                ball.normal();
            }
        }
    }

    private void removePowerUps() {
        ListIterator<PowerUp> it = activePowerUps.listIterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            if (!p.isPassive()) {
                p.decreaseTime();
                if (p.getTime() <= 0) {
                    unUsePowerUp(p);
                    it.remove();
                }
            }
        }
        it = powerUps.listIterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            if (p.getPowerY() > 1000) {
                it.remove();
            }
        }

    }

    private void nextSecond() {
        if (secondSurvived == 59) {
            secondSurvived = 0;
            minuteSurvived++;
        } else {
            secondSurvived++;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!started) {
            if (balls.get(0).getBallRadX() == 0 && balls.get(0).getBallRadY() == 0) {
                if (e.getX() < 200) {
                    balls.get(0).type1();
                } else if (e.getX() < 400) {
                    balls.get(0).type2();
                } else if (e.getX() < 600) {
                    balls.get(0).type3();
                } else {
                    balls.get(0).type4();
                }
            }
            useAllPowerUps();
            started = true;
            gameTimer.start();
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public ArrayList<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public int getSecondSurvived() {
        return secondSurvived;
    }

    public int getMinuteSurvived() {
        return minuteSurvived;
    }

    public int getScore() {
        return score;
    }

    public int getHealth() {
        return health;
    }

    private String getTime() {
        if (secondSurvived > 9) {
            return minuteSurvived + ":" + secondSurvived;
        } else {
            return minuteSurvived + ":0" + secondSurvived;
        }
    }

    private void useActivePowerUps() {
        for (PowerUp powerUp :
                activePowerUps) {
            usePowerUp(powerUp);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
