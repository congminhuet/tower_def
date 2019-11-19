package sample.GameEntity.Enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SmallerEnemy extends Enemy {
    public SmallerEnemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health) {
        super(layer, image, x, y, r, dx, dy, dr, health);
        this.damage = 2;
        this.speed = 5;
        this.armor = 1;
        this.reward = 2;
    }
}
