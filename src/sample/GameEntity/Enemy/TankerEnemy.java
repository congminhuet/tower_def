package sample.GameEntity.Enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class TankerEnemy extends Enemy {
    public TankerEnemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health) {
        super(layer, image, x, y, r, dx, dy, dr, health);
        this.damage = 3;
        this.speed = 2;
        this.armor = 15;
        this.reward = 5;
    }
}
