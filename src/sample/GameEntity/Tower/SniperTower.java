package sample.GameEntity.Tower;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SniperTower extends Tower {

    public SniperTower(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double speed) {
        super(layer, image, x, y, r, dx, dy, dr, health, speed);
        this.targetRange = 500;
        this.cost = 20;
        this.setDamage(50);
        this.speed = 2;
    }

}
