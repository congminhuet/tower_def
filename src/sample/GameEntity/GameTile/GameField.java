package sample.GameEntity.GameTile;


import javafx.animation.PauseTransition;
import javafx.util.Duration;
import sample.GameEntity.Config;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class GameField {
    private final int maxTile = Config.WIDTH * Config.HEIGHT;
    public boolean Map[][] = new boolean[Config.HEIGHT/100][Config.WIDTH/100];
    Map<Double, Double> map = new HashMap<Double, Double>();
    Queue<Map<Double, Double>> queueMap = new LinkedList<>();
    public PauseTransition pause = new PauseTransition(Duration.millis(5000));


    public GameField() {
        Map[3][11] = true;
        Map[0][2] = true;   Map[0][8] = true;
        Map[0][3] = true;   Map[0][7] = true;
        Map[0][4] = true;   Map[0][6] = true;
        Map[1][2] = true;   Map[1][8] = true;
        Map[1][4] = true;   Map[1][6] = true;
        Map[2][2] = true;   Map[2][8] = true;
        Map[2][4] = true;   Map[2][6] = true;
        Map[3][0] = true;   Map[3][10] = true;
        Map[3][1] = true;   Map[3][9] = true;
        Map[3][2] = true;   Map[3][8] = true;
        Map[3][4] = true;   Map[3][6] = true;
        Map[4][3] = true;   Map[4][7] = true;
        Map[4][4] = true;   Map[4][6] = true;
        Map[5][3] = true;   Map[5][7] = true;
        Map[6][3] = true;   Map[6][7] = true;
        Map[6][4] = true;   Map[6][6] = true;
        Map[6][5] = true;
    }

    public void setMap(double x, double y){
        Map[(int) y][(int) x] = false;
    }

    public boolean getMap(double x, double y){
        return Map[(int)y][(int)x];
    }

}
