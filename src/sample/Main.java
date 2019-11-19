package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.GameEntity.Config;
import sample.GameEntity.Enemy.Boss;
import sample.GameEntity.Enemy.Enemy;
import sample.GameEntity.Enemy.SmallerEnemy;
import sample.GameEntity.Enemy.TankerEnemy;
import sample.GameEntity.GameTile.GameField;
import sample.GameEntity.GameTile.Spawner;
import sample.GameEntity.Settings;
import sample.GameEntity.SpriteBase;
import sample.GameEntity.Tower.MachineGunTower;
import sample.GameEntity.Tower.SniperTower;
import sample.GameEntity.Tower.Tower;

import java.util.*;

//import javax.print.attribute.standard.Media;

public class Main extends Application {
    Queue<Integer> num = new LinkedList<>();
    GameField gameField = new GameField();
    Group root = new Group();
    Group root1 = new Group();
    boolean clicked = false;
    boolean nextWave = false;
    Random rnd = new Random();
    int stt = 0;
    int numOfEnemy = 0;
    long botWave = System.currentTimeMillis();
    Spawner spawner = new Spawner();
    Pane playfieldLayer;
    Pane pathLayer = new Pane();

    Image playerImage;
    Image enemyImage;
    Image bulletImage;
    //ImageView pauseIV = new ImageView(new Image("/sample/image/pause.png"));
    ImageView lifeIV = new ImageView(new Image("/sample/image/Life.png"));
    ImageView coinIV = new ImageView(new Image("/sample/image/coin.png"));
    List<Tower> towers = new ArrayList<>();;
    List<Enemy> enemies = new ArrayList<>();;

    Text lifeText = new Text();
    Text Money = new Text();
    Text waveText = new Text();
    Text gameOver = new Text();

    int life = 20;
    int money = 50;
    int wave = 1;

    Scene scene;

    int moneyPay = 0;

    //ImageTower
    Image IVMachine;
    Image IVSniper;
    Image Tower;
    ImageView Machine = new ImageView("/sample/image/MGTower.png");
    ImageView Sniper = new ImageView("/sample/image/STower.png");
    ImageView IVTower = new ImageView("/sample/image/launcher.png");

    ImageView MachineC = new ImageView("/sample/image/MGTowerC.png");
    ImageView SniperC = new ImageView("/sample/image/STowerC.png");
    ImageView IVTowerC = new ImageView("/sample/image/lanncherC.png");
    //ImageEnemy
    Image SEImage;
    Image TEImage;
    Image BEImage;

    PauseTransition pause = new PauseTransition(Duration.millis(5000));
    //@Override
    public void start(Stage primaryStage) {

        //Media menuTheme = new Media(getClass().getResource("/sample/LaLung.wav").toString());
        //MediaPlayer mplayer = new MediaPlayer(menuTheme);
        // mplayer.setVolume(0.7);
        //mplayer.setAutoPlay(true);

        num.add(3);
        num.add(5);
        num.add(10);
        num.add(20);
        num.add(30);
        num.add(50);

        /*String Map1SoundPath2 = "C:\\Users\\ADMIN\\Desktop\\TowerDefense\\src\\sample\\LaLung.mp3";
        Media Map1Sound2 = new Media(new File(Map1SoundPath2).toURI().toString());
        MediaPlayer PlayMap1Sound2 = new MediaPlayer(Map1Sound2);
        MediaView ViewMap1Sound2 = new MediaView(PlayMap1Sound2);
        root.getChildren().add(ViewMap1Sound2);
        PlayMap1Sound2.setCycleCount(1);
        PlayMap1Sound2.play();*/
        MachineC.setX(Config.WIDTH - 100);
        MachineC.setY(100);
        SniperC.setX(Config.WIDTH - 100);
        SniperC.setY(200);
        IVTowerC.setX(Config.WIDTH - 100);
        IVTowerC.setY(0);
        lifeIV.setX(Config.WIDTH - 100);
        lifeIV.setY(300);
        coinIV.setX(Config.WIDTH - 100);
        coinIV.setY(400);
        //pauseIV.setX(Config.WIDTH - 100);
        // pauseIV.setY(600);
        root.getChildren().addAll(IVTowerC, MachineC, SniperC, lifeIV, coinIV);
        // create layers
        playfieldLayer = new Pane();
        //lifeRemain = new Pane();
        pathLayer.setMinSize(Config.WIDTH, Config.HEIGHT);
        playfieldLayer.getChildren().add(pathLayer);

        root.getChildren().add(new ImageView(new Image(this.getClass().getResource("/sample/image/BG1.png").toExternalForm())));
        root.getChildren().add( playfieldLayer);

        scene = new Scene( root, Config.WIDTH, Config.HEIGHT);

        playfieldLayer.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            int x = (int) e.getX()/100*100+50;
            int y = (int) e.getY()/100*100+50;
            if(e.getX() > Config.WIDTH - 100 && !clicked){
                if(e.getY()<=300 && e.getY() > 200 && money >= 20){
                    stt = 3;
                    scene.setCursor(new ImageCursor(IVSniper));
                    clicked = true;
                    moneyPay=20;
                }else if(e.getY() > 100 && e.getY()<=200 && money >= 10){
                    stt = 2;
                    scene.setCursor(new ImageCursor(IVMachine));
                    clicked = true;
                    moneyPay=10;
                }else if(e.getY() <= 100 && money >=5){
                    stt = 1;
                    scene.setCursor(new ImageCursor(playerImage));
                    clicked = true;
                    moneyPay=5;
                }
            }

            if(gameField.getMap(e.getX()/100, e.getY()/100) && e.getX() <= Config.WIDTH - 100 && !clicked) {
                for(Tower i : towers){
                    if(i.getX() + 100 > e.getX() && i.getX() < e.getX() && i.getY() + 100 > e.getY() && i.getY() < e.getY()){
                        money += (int) i.cost/2;
                        gameField.setMap(e.getX()/100, e.getY()/100, false);
                        i.removeFromLayer();
                        towers.remove(i);
                    }
                }
            }

            if(!gameField.getMap(e.getX()/100, e.getY()/100) && e.getX() <= Config.WIDTH - 100 && clicked) {
                scene.setCursor(null);
                createTower((double) x, (double) y, stt);
                clicked = false;
                money -= moneyPay;
                moneyPay = 0;
                gameField.setMap(e.getX()/100, e.getY()/100, true);
            }
        });

        createScoreLayer();
        primaryStage.setScene( scene);
        primaryStage.show();

        loadGame();

        updateScore();
        createTowers();

        AnimationTimer gameLoop = new AnimationTimer() {

            @Override
            public void handle(long now) {

                if((int) num.peek() > numOfEnemy){
                    spawnEnemies(true);
                }

                if(!nextWave && (int) num.peek() == numOfEnemy && enemies.isEmpty()){
                    botWave = System.currentTimeMillis();
                    nextWave = true;
                }

                if (numOfEnemy == (int) num.peek() && nextWave && System.currentTimeMillis() - botWave >= 3000){
                    num.poll();
                    ++wave;
                    nextWave = false;
                    numOfEnemy = 0;
                }

                if(num.size() == 0) num.add(100);

                towers.forEach( tower -> tower.checkTarget());

                // tower movement: find target
                for( Tower tower: towers) {
                    tower.findTarget( enemies);
                }

                // movement
                towers.forEach(sprite -> sprite.move());
                enemies.forEach(enemy -> enemy.run());
                enemies.forEach(sprite -> sprite.move());

                // check collisions
                checkCollisions();
                towers.forEach(sprite -> sprite.updateUI());
                enemies.forEach(sprite -> sprite.updateUI());
                // check if sprite can be removed
                for(Enemy i : enemies){
                    if(i.hitTarget()) life -= i.getDamage();
                }

                enemies.forEach(sprite -> sprite.checkRemovability());

                // remove removables from list, layer, etc
                removeSprites( enemies);

                // update score, health, etc
                updateScore();
            }

        };
        gameLoop.start();

    }

    private void loadGame() {
        playerImage = new Image( getClass().getResource("/sample/image/launcher.png").toExternalForm());
        enemyImage = new Image( getClass().getResource("/sample/image/Enemy.png").toExternalForm());
        bulletImage = new Image(getClass().getResource("/sample/image/bul.png").toExternalForm());
        IVMachine = new Image(getClass().getResource("/sample/image/MGTower.png").toExternalForm());
        IVSniper = new Image(getClass().getResource("/sample/image/STower.png").toExternalForm());
        SEImage = new Image(getClass().getResource("/sample/image/SmallerEnemy.png").toExternalForm());
        TEImage = new Image(getClass().getResource("/sample/image/TankerEnemy.png").toExternalForm());
        BEImage = new Image(getClass().getResource("/sample/image/Boss.png").toExternalForm());
    }

    private void createScoreLayer() {
        lifeText.setFont( Font.font( null, FontWeight.BOLD, 50));
        lifeText.setStroke(Color.BLACK);
        lifeText.setFill(Color.RED);

        Money.setFont( Font.font( null, FontWeight.BOLD, 50));
        Money.setStroke(Color.BLACK);
        Money.setFill(Color.RED);

        waveText.setFont( Font.font( null, FontWeight.BOLD, 50));
        waveText.setStroke(Color.YELLOW);
        waveText.setFill(Color.GREEN);



        boolean add = playfieldLayer.getChildren().add((Node) lifeText);
        playfieldLayer.getChildren().add((Node) Money);
        playfieldLayer.getChildren().add((Node) waveText);


        Money.setText(String.valueOf(money));
        lifeText.setText( String.valueOf( life));
        waveText.setText(String.valueOf(wave));



        double x = Config.WIDTH - 75;
        double y = 315;
        ((Node) lifeText).relocate(x - 5, y);
        ((Node) Money).relocate(x, y + 100);
        ((Node) waveText).relocate(x, y + 200);


        lifeText.setBoundsType(TextBoundsType.VISUAL);
        Money.setBoundsType(TextBoundsType.VISUAL);
        waveText.setBoundsType(TextBoundsType.VISUAL);
        gameOver.setBoundsType(TextBoundsType.VISUAL);


        gameOver.setFont(Font.font(null,FontWeight.BOLD, 100));
        gameOver.setStroke(Color.RED);
        gameOver.setFill(Color.RED);
        playfieldLayer.getChildren().add((Node) gameOver);
        ((Node) gameOver).relocate(400,315);

    }
    private void createTowers() {

        // position initial towers
        List<Point2D> towerPositionList = new ArrayList<>();
        //towerPositionList.add(new Point2D( 100, 200));
        //towerPositionList.add(new Point2D( 100, 400));
        //towerPositionList.add(new Point2D( 800, 200));
        //towerPositionList.add(new Point2D( 800, 600));

        for( Point2D pos: towerPositionList) {

            createTower( pos.getX(), pos.getY(), stt);

        }

    }

    private void createTower( double x, double y, int stt) {

        Image image = playerImage;

        // center image at position
        x -= image.getWidth() / 2;
        y -= image.getHeight() / 2;

        // create player
        if(stt == 1){
            Tower player = new Tower(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 2);
            towers.add(player);
        }else if(stt == 2){
            image = IVMachine;
            MachineGunTower player = new MachineGunTower(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 1);
            towers.add(player);
        }else{
            image = IVSniper;
            SniperTower player = new SniperTower(playfieldLayer, image, x, y, 0, 0, 0, 0, Settings.PLAYER_SHIP_HEALTH, 4);
            towers.add(player);
        }
    }

    private void spawnEnemies( boolean random) {

        if( random && rnd.nextInt(Settings.ENEMY_SPAWN_RANDOMNESS) != 0) {
            System.out.println(numOfEnemy);
            return;
        }
        // image
        Image image = enemyImage;
        // random speed
        //double speed = rnd.nextDouble() * 1.0 + 2.0;
        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = 0;
        double y = 300;
        int j = rnd.nextInt(80) + 1;
        if(j % 32 == 0){
            image = BEImage;
            Boss enemy = new Boss( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
            enemies.add(enemy);
            numOfEnemy = numOfEnemy + 1;
        }else if(j % 16 == 3) {
            image = TEImage;
            TankerEnemy enemy = new TankerEnemy(playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
            enemies.add(enemy);
            numOfEnemy = numOfEnemy + 1;
        }else if(j % 8 == 2){
            image = SEImage;
            SmallerEnemy enemy = new SmallerEnemy( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
            enemies.add(enemy);
            numOfEnemy = numOfEnemy + 1;
        }else if(j % 4 == 1){
            Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
            enemies.add(enemy);
            numOfEnemy = numOfEnemy + 1;
        }
        // manage sprite
    }

    /*public void spawnEnemy(int wave){
        Image image = enemyImage;
        // random speed
        //double speed = rnd.nextDouble() * 1.0 + 2.0;
        // x position range: enemy is always fully inside the screen, no part of it is outside
        // y position: right on top of the view, so that it becomes visible with the next game iteration
        double x = 0;
        double y = 300;
        for(int j = 0; j < 4; j++){
            for(int k = 0; k < spawner.enemyWave[wave][j]; k++){
                if(j == 0){
                    Enemy enemy = new Enemy( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
                    enemies.add(enemy);
                }else if(j == 1){
                    image = SEImage;
                    SmallerEnemy enemy = new SmallerEnemy( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
                    enemies.add(enemy);
                }else if(j == 2){
                    image = TEImage;
                    TankerEnemy enemy = new TankerEnemy( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
                    enemies.add(enemy);
                }else{
                    image = BEImage;
                    Boss enemy = new Boss( playfieldLayer, image, x, y, 0, 0, 0, 0, 1);
                    enemies.add(enemy);
                }
            }
        }
    }*/

    private void removeSprites(  List<? extends SpriteBase> spriteList) {
        Iterator<? extends SpriteBase> iter = spriteList.iterator();
        while( iter.hasNext()) {
            SpriteBase sprite = iter.next();
            if( sprite.isRemovable()) {
                // remove from layer
                sprite.removeFromLayer();
                // remove from list
                iter.remove();
            }
        }
    }

    private void checkCollisions() {

        for( Tower tower: towers) {
            for( Enemy enemy: enemies) {
                if( tower.hitsTarget( enemy)) {
                    //tower.shootEnemy(enemy);
                    tower.checkRemovability();
                    if(tower.canAttack()){
                        if(tower.target != null){
                            tower.shootEnemy(enemy);
                        }
                        enemy.getDamagedBy( tower);
                        tower.setLastAttackTime(System.currentTimeMillis());
                    }
                    // TODO: explosion
                    if( !enemy.isAlive()) {
                        enemy.setRemovable(true);
                        money += enemy.getReward();
                        enemy.removeFromLayer();
                    }
                }
            }
        }
    }


    private void updateScore() {
        long ltime = 0;
        if (life >= 0){
            lifeText.setText( String.valueOf( life));
            Money.setText(String.valueOf(money));
            waveText.setText(String.valueOf(wave));
            ltime = System.currentTimeMillis()/1000;
        }
        if(life <= 0){
            lifeText.setText( String.valueOf( 0));
            gameOver.setText("GAME OVER");
            if (System.currentTimeMillis()/1000 - ltime >= 3)Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Path createPath(){
        Path ppath = new Path();
        ppath.getElements().add(new MoveTo(50, 350));
        //ppath.getElements().add(new LineTo(550, 50));
        ppath.getElements().add(new LineTo(250, 350));
        ppath.getElements().add(new LineTo(250, 50));
        ppath.getElements().add(new LineTo(450, 50));
        ppath.getElements().add(new LineTo(450, 450));
        ppath.getElements().add(new LineTo(350, 450));
        ppath.getElements().add(new LineTo(350, 650));
        ppath.getElements().add(new LineTo(750, 650));
        ppath.getElements().add(new LineTo(750, 450));
        ppath.getElements().add(new LineTo(650, 450));
        ppath.getElements().add(new LineTo(650, 50));
        ppath.getElements().add(new LineTo(850, 50));
        ppath.getElements().add(new LineTo(850, 350));
        ppath.getElements().add(new LineTo(1150, 350));
        return ppath;
    }

}
