package sample.GameEntity.Tower;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class MachineGunTower extends Tower {
    //public Image mgimage = new Image(getClass().getResource("/sample/image/MGTower.png").toExternalForm());
    public MachineGunTower(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double speed) {
        super(layer, image, x, y, r, dx, dy, dr, health, speed);
        this.targetRange = 150;
        this.setDamage(21);
        this.cost = 10;
        this.speed = 0.1;
    }

    /*public MachineGunTower(Pane layer, double x, double y, double r, double dx, double dy, double dr, double health, double damage, double speed) {
        this.layer = layer;
        this.image = mgimage;
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;
        this.health = health;
        this.damage = damage;
        this.speed = speed;
        this.targetRange = 200;
    }*/
}
