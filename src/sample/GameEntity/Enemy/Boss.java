package sample.GameEntity.Enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Boss extends Enemy {

    public Boss(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health) {
        super(layer, image, x, y, r, dx, dy, dr, health);
        this.armor = 20;
        this.speed = 1;
        this.reward = 20;
        this.damage = 5;
    }
}
