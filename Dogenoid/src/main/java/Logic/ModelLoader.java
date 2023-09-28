package Logic;

import Graphics.GraphicalAgent;
import Graphics.Objects.Ball;
import Graphics.Objects.Bricks.*;
import Graphics.Objects.Paddle;
import Graphics.Objects.PowerUps.*;
import Graphics.Panel.GamePanel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ModelLoader {
    private static int lastId = 0;
    private final File playersDirectory;
    private final File rating;
    GraphicalAgent graphicalAgent;

    public ModelLoader(GraphicalAgent graphicalAgent) {
        this.graphicalAgent = graphicalAgent;
        this.playersDirectory = new File("src/main/resources/PlayerDirectory");
        this.rating = new File("src/main/resources/Rank");
    }

    public void loadPlayer(String name) { // enters game
        try {
            if (!playerExists(name)) {
                lastId++;
                String Filepath = playersDirectory.getPath() + "\\" + name;
                File newPlayer = new File(Filepath);
                newPlayer.mkdir();
                File playerInfo = new File(Filepath + "\\" + "info.txt");
                playerInfo.createNewFile();
                File playerSaves = new File(Filepath + "\\" + "Saves");
                playerSaves.mkdir();
                PrintStream printStream = new PrintStream(new FileOutputStream(playerInfo));
                printStream.println("Name: " + name);
                printStream.println("ID: " + lastId);
                printStream.println("Games: 0");
                printStream.println("HighScore: 0");
                printStream.flush();
                printStream.close();
                ScoreRating();
            }

        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void savePlayer(String name, int score) {
        try {
            File file = getPlayerInfo(name);
            Scanner scanner = new Scanner(file);
            scanner.next();
            scanner.next();
            scanner.next();
            int playerID = scanner.nextInt();
            scanner.next();
            int gamesCount = scanner.nextInt();
            scanner.next();
            int highScore = scanner.nextInt();
            gamesCount++;
            if (highScore < score) {
                highScore = score;
            }
            scanner.close();
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println("Name: " + name);
            printStream.println("ID: " + playerID);
            printStream.println("Games: " + gamesCount);
            printStream.println("HighScore: " + highScore);
            printStream.flush();
            printStream.close();
            ScoreRating();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }


    public void ScoreRating() throws FileNotFoundException {
        File[] playersFile = playersDirectory.listFiles();
        Map<String, Integer> Rating = new HashMap<>();
        for (File playerFile :
                playersFile) {
            File info = getPlayerInfo(playerFile.getName());
            Scanner scanner = new Scanner(info);
            scanner.next();
            String PlayersName = scanner.next();
            scanner.next();
            scanner.next();
            scanner.next();
            scanner.next();
            scanner.next();
            int PlayersPoints = scanner.nextInt();
            Rating.put(PlayersName, PlayersPoints);
            scanner.close();
        }
        ArrayList<Map.Entry<String, Integer>> NewRating = new ArrayList<>(Rating.entrySet());
        NewRating.sort(Map.Entry.comparingByValue());
        PrintStream printStream = new PrintStream(rating);
        for (int i = NewRating.size() - 1; i >= 0; i--) {
            printStream.print("Rank " + (NewRating.size() - i) + " Is ");
            printStream.print(NewRating.get(i).getKey());
            printStream.print(" With ");
            printStream.print(NewRating.get(i).getValue());
            printStream.println(" Point");
        }
        printStream.flush();
        printStream.close();
    }

    public String getLeaderBoard(String name) {
        String ranking = "";
        try {
            Scanner scanner = new Scanner(rating);
            int count = 0;
            while (scanner.hasNextLine() && count < 10) {
                ranking += scanner.nextLine() + '\n';
            }
            ranking += getHighScore(name);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find file");
            System.exit(-2);
        }
        return ranking;
    }

    public void saveGame(String playerName, String saveName, GamePanel gamePanel) {
        if (saveName.equals("null")){
            return;
        }
        try {
            String path = playersDirectory.getPath() + "\\" + playerName + "\\" + "Saves" + "\\" + saveName;
            File playerSave = new File(path);
            playerSave.createNewFile();
            PrintStream printStream = new PrintStream(playerSave);
            //Save Paddle
            Paddle paddle = gamePanel.getPaddle();
            printStream.println("Paddle:");
            printStream.println(paddle.getPaddleX() + " " + paddle.getPaddleY());
            printStream.println("Dizzy: " + paddle.isDizzy());
            //save balls
            ArrayList<Ball> balls = gamePanel.getBalls();
            printStream.println("Balls[ " + balls.size() + " ]");
            for (Ball ball :
                    balls) {
                printStream.println(ball.getBallX() + " " + ball.getBallY());
                printStream.println(ball.getBallRadX() + " " + ball.getBallRadY());
                printStream.println("FireBall: " + ball.isFireBall());
            }
            //save Bricks
            ArrayList<Brick> bricks = gamePanel.getBricks();
            printStream.println("Bricks[ " + bricks.size() + " ]");
            for (Brick brick :
                    bricks) {
                printStream.println(brick.toString());
                printStream.println(brick.getBrickX() + " " + brick.getBrickY());
                if (brick.toString().equals("Wooden")){
                    printStream.println(brick.getHealth());
                }
            }
            ArrayList<PowerUp> powerUps = gamePanel.getPowerUps();
            //save PowerUps
            printStream.println("PowerUps[ " + powerUps.size() + " ]");
            for (PowerUp powerUp :
                    powerUps) {
                printStream.println(powerUp.toString());
                printStream.println(powerUp.getPowerX() + " " + powerUp.getPowerY());
            }
            //save ActivePowerUps
            powerUps = gamePanel.getActivePowerUps();
            printStream.println("ActivePowerUps[ " + powerUps.size() + " ]");
            for (PowerUp powerUp :
                    powerUps) {
                printStream.println(powerUp.toString());
                printStream.println(powerUp.getPowerX() + " " + powerUp.getPowerY());
                printStream.println(powerUp.getTime());
            }
            printStream.println("Seconds " + gamePanel.getSecondSurvived());
            printStream.println("Minutes " + gamePanel.getMinuteSurvived());
            printStream.println("Score " + gamePanel.getScore());
            printStream.println("Health " + gamePanel.getHealth());
            printStream.flush();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("could not find file");
            System.exit(-2);
        }

    }

    public GamePanel loadGame(String playerName, String saveName) {
        try {
            File gameFile = getPlayerSave(playerName, saveName);
            Scanner scanner = new Scanner(gameFile);
            scanner.next();
            //paddle
            Paddle paddle = new Paddle(scanner.nextInt(), scanner.nextInt());
            scanner.next();
            paddle.setDizzy(scanner.nextBoolean());
            //ball
            scanner.next();
            int ballCount = scanner.nextInt();
            scanner.next();
            ArrayList<Ball> balls = new ArrayList<>();
            for (int i = 0; i < ballCount; i++) {
                Ball ball = new Ball(scanner.nextInt(), scanner.nextInt(), scanner.nextDouble(), scanner.nextDouble());
                scanner.next();
                ball.setFireBall(scanner.nextBoolean());
                balls.add(ball);
            }
            //bricks
            scanner.next();
            int brickCount = scanner.nextInt();
            scanner.next();
            ArrayList<Brick> bricks = new ArrayList<>();
            for (int i = 0; i < brickCount; i++) {
                String type = scanner.next();
                switch (type) {
                    case "Brick":
                        bricks.add(new Brick(scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Blink":
                        bricks.add(new BlinkingBrick(scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Invisible":
                        bricks.add(new InvisibleBrick(scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "PowerUp":
                        bricks.add(new PowerUpBrick(scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Wooden":
                        bricks.add(new WoodenBrick(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                }
            }
            scanner.next();
            int powerUpCount = scanner.nextInt();
            scanner.next();
            ArrayList<PowerUp> powerUps = new ArrayList<>();
            for (int i = 0; i < powerUpCount; i++) {
                String type = scanner.next();
                switch (type) {
                    case "Dizzy":
                        powerUps.add(new Dizzy(scanner.nextInt(), scanner.nextInt(), 10));
                        break;
                    case "Expand":
                        powerUps.add(new Expand(scanner.nextInt(), scanner.nextInt(), 10));
                        break;
                    case "Fast":
                        powerUps.add(new Fast(scanner.nextInt(), scanner.nextInt(), 10));
                        break;
                    case "FireBall":
                        powerUps.add(new FireBall(scanner.nextInt(), scanner.nextInt(), 10));
                        break;
                    case "Shrink":
                        powerUps.add(new Shrink(scanner.nextInt(), scanner.nextInt(), 10));
                        break;
                    case "Slow":
                        powerUps.add(new Slow(scanner.nextInt(), scanner.nextInt(), 10));
                        break;
                    case "ThreeBall":
                        powerUps.add(new ThreeBall(scanner.nextInt(), scanner.nextInt(), 0));
                        break;
                    case "Random":
                        powerUps.add(new RandomPrize(scanner.nextInt(), scanner.nextInt(), 10));
                }
            }
            scanner.next();
            int activePowerUpCount = scanner.nextInt();
            scanner.next();
            ArrayList<PowerUp> activePowerUps = new ArrayList<>();
            for (int i = 0; i < activePowerUpCount; i++) {
                String type = scanner.next();
                switch (type) {
                    case "Dizzy":
                        activePowerUps.add(new Dizzy(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Expand":
                        activePowerUps.add(new Expand(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Fast":
                        activePowerUps.add(new Fast(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "FireBall":
                        activePowerUps.add(new FireBall(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Shrink":
                        activePowerUps.add(new Shrink(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "Slow":
                        activePowerUps.add(new Slow(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                    case "ThreeBall":
                        activePowerUps.add(new ThreeBall(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
                        break;
                }
            }
            scanner.next();
            int seconds = scanner.nextInt();
            scanner.next();
            int minutes = scanner.nextInt();
            scanner.next();
            int score = scanner.nextInt();
            scanner.next();
            int health = scanner.nextInt();
            return new GamePanel(graphicalAgent, balls, paddle, bricks, powerUps, activePowerUps, seconds, minutes, score, health);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("could not find file");
            System.exit(-2);
            return null;
        }
    }

    public ArrayList<String> saveNames(String name){
        String path = playersDirectory.getPath() + "\\" + name + "\\" + "Saves";
        File saves = new File(path);
        ArrayList<String> saveNames = new ArrayList();
        for (File file :
                saves.listFiles()) {
            saveNames.add(file.getName());
        }
        return saveNames;
    }

    private String getHighScore(String name){
        String playerRank = "";
        try {
            Scanner scanner = new Scanner(rating);
            while (scanner.hasNextLine()){
                scanner.next();
                int rank = scanner.nextInt();
                scanner.next();
                String username = scanner.next();
                scanner.next();
                int point = scanner.nextInt();
                scanner.next();
                if (username.equals(name)){
                    playerRank = "You Are Rank " + rank + " With " + point + " Points";
                    return playerRank;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return playerRank;
    }

    private boolean playerExists(String name) {
        return (getPlayerInfo(name) != null);
    }

    private File getPlayerInfo(String name) {
        File[] playersFile = playersDirectory.listFiles();
        lastId = playersFile.length;
        for (File playersStat : playersFile) {
            if (playersStat.getName().equals(name)) {
                String path = playersStat.getPath();
                path += "\\" + "info.txt";
                return new File(path);
            }
        }
        return null;

    }

    public boolean saveExist(String playerName, String saveName) {
        return (getPlayerSave(playerName, saveName) != null);
    }

    private File getPlayerSave(String playerName, String saveName) {
        String path = playersDirectory.getPath() + "\\" + playerName + "\\" + "Saves" + "\\" + saveName;
        File playerSave = new File(path);
        if (playerSave.exists()) {
            return playerSave;
        } else return null;
    }

}
