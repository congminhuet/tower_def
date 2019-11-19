package sample.GameEntity.GameTile;

import sample.GameEntity.Config;

public class Target {
    private int x = Config.WIDTH -200;
    private int y = 300;

    public Target() {
        this.x = Config.WIDTH -200;;
        this.y = 300;
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
