package sample.GameEntity.GameTile;

public class Spawner {
    private int x;
    private int y;
    public int[][] enemyWave = {
            {2,1,0,0},
            {5,2,0,0},
            {6,2,1,0},
            {6,3,2,2},
            {6,3,3,3},
            {8,4,4,4},
            {9,5,5,5},
            {10,6,6,6},
            {12,6,6,7},
            {15,6,8,8},
            {16,6,9,9},
            {20,8,10,10}

    };

    public Spawner() {
        this.x = 50;
        this.y = 350;

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
