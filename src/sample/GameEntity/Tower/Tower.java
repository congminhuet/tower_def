package sample.GameEntity.Tower;

import javafx.animation.Animation.Status;
import javafx.animation.PathTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import sample.GameEntity.Bullet;
import sample.GameEntity.Settings;
import sample.GameEntity.SpriteBase;

import java.util.List;

public class Tower extends SpriteBase {
    Image bulletImage = new Image(getClass().getResource("/sample/image/bul.png").toExternalForm());
    long lastAttackTime = 0;
    public int cost = 5;
    public SpriteBase target; // TODO: use weakreference
    double turnRate = 0.6;
    double damage = 20;
    public double speed = 1;

    double targetRange = 200; // distance within tower can lock to enemy

    ColorAdjust colorAdjust;

    Bullet bullet;
    double rotationLimitDeg=0.0;
    double rotationLimitRad =  Math.toDegrees( this.rotationLimitDeg);
    double roatationEasing = 10;
    double targetAngle = 0;
    double currentAngle = 0;

    boolean withinFiringRange = false;

    public Tower(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health, double speed) {

        super(layer, image, x, y, r, dx, dy, dr, health);

        this.speed = speed;

        this.setDamage(20);
        init();
    }

    public Tower() {
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public long getLastAttackTime() {
        return lastAttackTime;
    }

    public void setLastAttackTime(long lastAttackTime) {
        this.lastAttackTime = lastAttackTime;
    }

    private void init() {

        // red colorization (simulate "angry")
        colorAdjust = new ColorAdjust();
        colorAdjust.setContrast(0.0);
        colorAdjust.setHue(0);

    }

    @Override
    public void move() {

        SpriteBase follower = this;

        // reset within firing range
        withinFiringRange = false;

        // rotate towards target
        if( target != null)
        {
            // parts of code used from shane mccartney (http://lostinactionscript.com/page/3/)
            double xDist = target.getCenterX() - follower.getCenterX();
            double yDist = target.getCenterY() - follower.getCenterY();

            this.targetAngle = Math.atan2(yDist, xDist) - Math.PI / 2;

            this.currentAngle = Math.abs(this.currentAngle) > Math.PI * 2 ? (this.currentAngle < 0 ? (this.currentAngle % Math.PI * 2 + Math.PI * 2) : (this.currentAngle % Math.PI * 2)) : (this.currentAngle);
            this.targetAngle = this.targetAngle + (Math.abs(this.targetAngle - this.currentAngle) < Math.PI ? (0) : (this.targetAngle - this.currentAngle > 0 ? ((-Math.PI) * 2) : (Math.PI * 2)));
            this.currentAngle = this.currentAngle + (this.targetAngle - this.currentAngle) / roatationEasing;  // give easing when rotation comes closer to the target point

            // check if the rotation limit has to be kept
            if( (this.targetAngle-this.currentAngle) > this.rotationLimitRad) {
                this.currentAngle+=this.rotationLimitRad;
            } else if( (this.targetAngle-this.currentAngle) < -this.rotationLimitRad) {
                this.currentAngle-=this.rotationLimitRad;
            }

            follower.r = Math.toDegrees(currentAngle);

            // determine if the player ship is within firing range; currently if the player ship is within 10 degrees (-10..+10)
            withinFiringRange = Math.abs( Math.toDegrees( this.targetAngle-this.currentAngle)) < 20;

        }

        super.move();

    }

    public void checkTarget() {
        if( target == null) {
            //bullet.removeFromLayer();
            return;
        }
        if( !target.isAlive() || target.isRemovable()) {
            //bullet.setFire(false);
            //bullet.removeFromLayer();
            setTarget( null);
            return;
        }
        //get distance between follower and target
        double distanceX = target.getCenterX() - getCenterX();
        double distanceY = target.getCenterY() - getCenterY();
        //get total distance as one number
        double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        if( Double.compare( distanceTotal, targetRange) > 0) {
            setTarget( null);
        }
    }

    public void findTarget( List<? extends SpriteBase> targetList) {


        // we already have a target
        if( getTarget() != null) {
            return;
        }

        //bullet = new Bullet(layer, bulletImage, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 0, Settings.PLAYER_SHIP_SPEED, false);
        SpriteBase closestTarget = null;
        double closestDistance = 0.0;

        for (SpriteBase target: targetList) {

            if (!target.isAlive())
                continue;

            //get distance between follower and target
            double distanceX = target.getCenterX() - getCenterX();
            double distanceY = target.getCenterY() - getCenterY();

            //get total distance as one number
            double distanceTotal = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            // check if enemy is within range
            if( Double.compare( distanceTotal, targetRange) > 0) {
                continue;
            }

            if (closestTarget == null) {

                closestTarget = target;
                closestDistance = distanceTotal;

            } else if (Double.compare(distanceTotal, closestDistance) < 0) {

                closestTarget = target;
                closestDistance = distanceTotal;

            }
        }

        setTarget(closestTarget);

    }

    public SpriteBase getTarget() {
        return target;
    }

    public void setTarget(SpriteBase target) {
        this.target = target;
    }



    @Override
    public void checkRemovability() {

        if( Double.compare( health, 0) < 0) {
            setTarget(null);
            setRemovable(true);
        }

    }

    public boolean hitsTarget( SpriteBase enemy) {

        return target == enemy && withinFiringRange;

    }

    public void updateUI() {

        if( withinFiringRange) {
            imageView.setEffect(colorAdjust);
        } else {
            imageView.setEffect(null);
        }

        super.updateUI();
    }

    public boolean canAttack(){
        if( (System.currentTimeMillis()-lastAttackTime)/1000.0 >= speed){
            return true;
        }
        else
            return false;
    }

    public void shootEnemy(SpriteBase target){
            Bullet bullet = new Bullet(layer, bulletImage, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, Settings.PLAYER_SHIP_SPEED, false);
            double tx = getImageView().getX() + 25;
            double ty = getImageView().getY() +25;
            MoveTo spawn = new MoveTo(tx , ty);
            double ex = target.getCenterX();
            double ey = target.getCenterY();
            Path path = new Path();
            LineTo line1 = new LineTo(ex - (int) getCenterX()/100* 100, ey - (int) getCenterY()/100*100 );
            path.getElements().addAll(spawn, line1);
            PathTransition pt = new PathTransition(Duration.millis(150), path, bullet.getImageView());
            pt.statusProperty().addListener(new ChangeListener<Status>() {
                    @Override
                    public void changed(ObservableValue<? extends Status>
                                                observableValue, Status oldValue, Status newValue) {
                        if (newValue == Status.STOPPED) {
                            layer.getChildren().remove(bullet.getImageView());
                        }
                    }
                });
            pt.play();

    }
}
