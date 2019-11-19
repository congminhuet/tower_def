package sample.GameEntity.Enemy;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import sample.GameEntity.GameTile.Target;
import sample.GameEntity.HealthBar;
import sample.GameEntity.Settings;
import sample.GameEntity.SpriteBase;
import sample.GameEntity.Tower.Tower;

public class Enemy extends SpriteBase {

    HealthBar healthBar;
    protected double damage = 1;
    protected double speed = 4;
    protected double armor = 5;
    protected int reward = 1;
    double healthMax;

    public Enemy(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health) {

        super(layer, image, x, y, r, dx, dy, dr, health);

        healthMax = Settings.ENEMY_HEALTH;

        setHealth(healthMax);

    }

    @Override
    public double getDamage() {
        return damage;
    }

    @Override
    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    @Override
    public void checkRemovability() {
        if(getX() == new Target().getX() && getY() == new Target().getY()) {
            setRemovable(true);
        }
    }

    public void getDamagedBy( Tower sprite) {
        health -= sprite.getDamage();
        health += armor;
    }

    public boolean hitTarget(){
        return (getX() == new Target().getX() && getY() == new Target().getY());
    }

    public void addToLayer() {

        super.addToLayer();

        // create health bar; has to be created here because addToLayer is called in super constructor
        // and it wouldn't exist yet if we'd create it as class member
        healthBar = new HealthBar();

        this.layer.getChildren().add(this.healthBar);

    }

    public void removeFromLayer() {

        super.removeFromLayer();

        this.layer.getChildren().remove(this.healthBar);

    }

    /**
     * Health as a value from 0 to 1.
     * @return
     */
    public double getRelativeHealth() {
        return getHealth() / healthMax;
    }


    public void updateUI() {

        super.updateUI();

        // update health bar
        healthBar.setValue( getRelativeHealth());

        // locate healthbar above enemy, centered horizontally
        healthBar.relocate(x + (imageView.getBoundsInLocal().getWidth() - healthBar.getBoundsInLocal().getWidth()) / 2, y - healthBar.getBoundsInLocal().getHeight() - 4);
    }

    public void run(){
        int coor = this.road.road.peek();
        if(coor/1000 == this.getCenterX() && coor%1000 == this.getCenterY()){
            this.road.road.poll();
            this.setDx(0);
            this.setDy(0);
            setDr(0);
        }else if (coor/1000 > this.getCenterX() && coor%1000 == this.getCenterY()){
            this.setDx(speed);
            this.setDy(0);
            this.setDr(0);
        }else if(coor/1000 < this.getCenterX() && coor%1000 == this.getCenterY()){
            this.setDx(-speed);
            this.setDy(0);
            this.setDr(0);
        }else if(coor/1000 == this.getCenterX() && coor%1000 > this.getCenterY()){
            this.setDy(speed);
            this.setDx(0);
            this.setDr(0);
        }else if(coor/1000 == this.getCenterX() && coor%1000 < this.getCenterY()){
            this.setDy(-speed);
            this.setDx(0);
            this.setDr(0);
        }
    }
}
