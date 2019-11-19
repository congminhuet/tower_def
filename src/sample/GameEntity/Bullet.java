package sample.GameEntity;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Bullet extends SpriteBase {
    boolean isFire = false;
    double speed;
    public Bullet(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double speed, boolean isFire) {
        super(layer, image, x, y, r, dx, dy, dr, health);
        this.isFire = isFire;
        this.speed = speed;
    }



    public boolean isFire() {
        return isFire;
    }

    public void setFire(boolean fire) {
        isFire = fire;
    }

    @Override
    public void checkRemovability(){
        if(!isFire()) {
            setRemovable(true);
        }
    }

    /*public void run(Target target){
        double x = this.getCenterX() - target.getCenterX();
        double y = this.getCenterY() - enemy.getCenterY();
        if(this.getCenterX() > enemy.getCenterX()){
            if(this.getCenterY() > enemy.getCenterY()){

            }
        }else if (coor/1000 > this.getCenterX() && coor%1000 == this.getCenterY()){
            this.setDx(1);
            this.setDy(0);
        }else if(coor/1000 < this.getCenterX() && coor%1000 == this.getCenterY()){
            this.setDx(-1);
            this.setDy(0);
        }else if(coor/1000 == this.getCenterX() && coor%1000 > this.getCenterY()){
            this.setDy(1);
            this.setDx(0);
        }else if(coor/1000 == this.getCenterX() && coor%1000 < this.getCenterY()){
            this.setDy(-1);
            this.setDx(0);
        }
    }*/
}
