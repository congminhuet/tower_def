package sample.GameEntity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import sample.GameEntity.GameTile.Road;

public abstract class SpriteBase {

    public Image image;
    public ImageView imageView;

    protected Pane layer;

    protected double x;
    protected double y;
    public double r;

    protected double dx;
    protected double dy;
    protected double dr;

    protected double health;
    protected double damage;
    double speed;

    boolean removable = false;

    double w;
    double h;

    boolean canMove = true;

    public Road road;

    public SpriteBase(Pane layer, Image image, double x, double y, double r, double dx, double dy, double dr, double health) {

        this.layer = layer;
        this.image = image;
        this.x = x;
        this.y = y;
        this.r = r;
        this.dx = dx;
        this.dy = dy;
        this.dr = dr;

        this.health = health;

        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        this.imageView.setRotate(r);

        this.w = image.getWidth(); // imageView.getBoundsInParent().getWidth();
        this.h = image.getHeight(); // imageView.getBoundsInParent().getHeight();

        this.road = new Road();
        addToLayer();

    }

    protected SpriteBase() {
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public double getDr() {
        return dr;
    }

    public void setDr(double dr) {
        this.dr = dr;
    }

    public double getHealth() {
        return health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public void move() {

        if( !canMove)
            return;

        x += dx;
        y += dy;
        r += dr;

    }

    public boolean isAlive() {
        return Double.compare(health, 0) > 0;
    }

    public ImageView getView() {
        return imageView;
    }

    public void updateUI() {

        imageView.relocate(x, y);
        imageView.setRotate(r);

    }

    public double getWidth() {
        return w;
    }

    public double getHeight() {
        return h;
    }

    public double getCenterX() {
        return x + w * 0.5;
    }

    public double getCenterY() {
        return y + h * 0.5;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    // TODO: per-pixel-collision
    public boolean collidesWith( SpriteBase otherSprite) {

        return ( otherSprite.x + otherSprite.w >= x && otherSprite.y + otherSprite.h >= y && otherSprite.x <= x + w && otherSprite.y <= y + h);

    }

    /**
     * Reduce health by the amount of damage that the given sprite can inflict
     * @param sprite
     */

    /**Set health to*/
    public void kill() {
        setHealth( 0);
    }

    /**Set flag that the sprite can be removed from the UI.*/
    public void remove() {
        setRemovable(true);
    }

    /** Set flag that the sprite can't move anymore.*/
    public void stopMovement() {
        this.canMove = false;
    }

    public abstract void checkRemovability();



}
