package UI;
import application.*;
import enemy.Enemy;
import game.Map;
import game.Manage_game;
import tile.Tile;
import tower.Tower;
import tower.tower_Archer;
import tower.tower_Artillery;
import tower.tower_Mage;
import tile.BuildableTile;
import tile.DecorativeTile;
import tile.PathTile;
import enemy.enemy_Goblin;
import enemy.enemy_Knight;
import utils.OptionsHandler;
import game.TrackEnemyWave;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.awt.geom.Point2D;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class GameScreen extends Pane {
 
       
    private static final int TILE_COLS = 20;
    private static final int TILE_ROWS = 15;
    private double tileWidth;
    private double tileHeight;
    private double screenWidth;
    private double screenHeight;

    // core components of the game
    private Manage_game manage_game;
    private List<Enemy> enemies;
    private double speedMultiplier = 1.0;
    private Button speedButton;
    private List<Tower> towers;
    private Map map;
    private Canvas canvas;
    private List<tower.projectile> projectiles = new ArrayList<>();
    private Pane towerMenu = null;
    
    public void addProjectile(tower.projectile p) {
        projectiles.add(p);
    }
    
    //For pause
    Button pause;
    ImageView pauseIcon;
    ImageView resumeIcon;
    ImageView slotIcon;
    boolean isPaused = false;
    StackPane createPause;
    
    // For HUD(gold life shield)
    ImageView coinIcon;
    ImageView heartIcon;
    ImageView shieldIcon;
    Text goldText;
    Text livesText;
    Text WaveCounterText;
    StackPane hudContainer;

    public GameScreen(Stage stage) {

        // set size of game screen and create canvas
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    	this.screenWidth = screenBounds.getWidth();
    	this.screenHeight = screenBounds.getHeight();

    	tileWidth = (screenWidth / TILE_COLS);
        tileHeight = (screenHeight / TILE_ROWS);

        setPrefSize(screenWidth, screenHeight);
        canvas = new Canvas(screenWidth, screenHeight);
        getChildren().add(canvas);
        pauseUI();
        gold_life_shieldUI();

        towers = new ArrayList<>();

        map = new Map(TILE_COLS, TILE_ROWS);
       
        // horizontal: from (0,7) to (3,7)
        for (int x = 0; x <= 3; x++) {
            map.set_tile(x, 7, new PathTile(x, 7, PathTile.PathType.HORIZONTAL));
            map.add_path_point(new Point((int)(x * tileWidth), (int)(7 * tileHeight)));
        }

        //corner: (4,7) turning down
        map.set_tile(4, 7, new PathTile(4, 7, PathTile.PathType.CORNER_BOTTOMLEFT));
        map.add_path_point(new Point((int)(4 * tileWidth), (int)(7 * tileHeight)));

        // vertical: from (4,8) to (4,8)
        map.set_tile(4, 8, new PathTile(4, 8, PathTile.PathType.VERTICAL));
        map.add_path_point(new Point((int)(4 * tileWidth), (int)(8 * tileHeight)));

        // corner: (4,9) turning right
        map.set_tile(4, 9, new PathTile(4, 9, PathTile.PathType.CORNER_TOPRIGHT));
        map.add_path_point(new Point((int)(4 * tileWidth), (int)(9 * tileHeight)));

        // horizontal: from (5,9) to (8,9)
        for (int x = 5; x <= 8; x++) {
            map.set_tile(x, 9, new PathTile(x, 9, PathTile.PathType.HORIZONTAL));
            map.add_path_point(new Point((int)(x * tileWidth), (int)(9 * tileHeight)));
        }

        // corner: (9,9) turning up
        map.set_tile(9, 9, new PathTile(9, 9, PathTile.PathType.CORNER_TOPLEFT));
        map.add_path_point(new Point((int)(9 * tileWidth), (int)(9 * tileHeight)));

        // vertical: (9,8) and (9,7)
        for (int y = 8; y >= 7; y--) {
            map.set_tile(9, y, new PathTile(9, y, PathTile.PathType.VERTICAL));
            map.add_path_point(new Point((int)(9 * tileWidth), (int)(y * tileHeight)));
        }

        // corner: (9,6) turning right
        map.set_tile(9, 6, new PathTile(9, 6, PathTile.PathType.CORNER_BOTTOMRIGHT));
        map.add_path_point(new Point((int)(9 * tileWidth), (int)(6 * tileHeight)));

        // horizontal: from (10,6) to (14,6)
        for (int x = 10; x <= 19; x++) {
            map.set_tile(x, 6, new PathTile(x, 6, PathTile.PathType.HORIZONTAL));
            map.add_path_point(new Point((int)(x * tileWidth), (int)(6 * tileHeight)));
        }

        // set starting and ending tile points.
        map.set_start_point(new Point(0, 7));
        map.set_end_point(new Point(15, 7));

        // buildable tiles where towers can be placed
        map.set_tile(6, 8, new BuildableTile(6, 8));
        map.set_tile(7, 8, new BuildableTile(7, 8));
        
        map.set_tile(11, 5, new BuildableTile(11, 5));
        map.set_tile(12, 5, new BuildableTile(12, 5));
        
        map.set_tile(8, 6, new DecorativeTile(8,6));
        map.set_tile(13, 10, new DecorativeTile(13,10));
        map.set_tile(12, 13, new DecorativeTile(12,13));

        // start game logic
        manage_game = new Manage_game();

        manage_game.startGame(map,OptionsHandler.getInstance().getStartGold(), OptionsHandler.getInstance().getPlayerHP());

        manage_game.set_go_Listener(() -> {
            Platform.runLater(() -> {
                GameOverScreen GameOverNow = new GameOverScreen(stage);
                Scene GAME_OVER = GameOverNow.newScene();
                stage.setScene(GAME_OVER);
            });
        });

        manage_game.set_gs_handler(enemy_total -> {
            Platform.runLater(() -> {
                EndGameStats WinScreen = new EndGameStats(stage, enemy_total);
                stage.setScene(WinScreen.newScene());
            });
        });

        enemies = manage_game.enemies;
        
        // mouse clicking for placing towers
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
        	int x = (int)(e.getX() / tileWidth);
            int y = (int)(e.getY() / tileHeight);

            Tile tile = map.get_tile(x, y);
            
            if (tile instanceof BuildableTile) {
               

                // check if tower is already placed at that tile
            	boolean taken = towers.stream().anyMatch(t -> t.getX() == x && t.getY() == y);
                // if tile is free and player has enough gold place a tower
                if (!taken && manage_game.player.can_afford(50)) {
                	
                	showTowerRadialMenu(x, y);
                }
            }
              
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (towerMenu != null && !towerMenu.contains(e.getX(), e.getY())) {
                getChildren().remove(towerMenu);
                towerMenu = null;
            }
        });

        // main game loop which updates game logic and redraws screen every frame
        AnimationTimer timer = new AnimationTimer() {
        	 
        	@Override
            public void handle(long now) {
                if (!isPaused) {
                    manage_game.updateGame(speedMultiplier);
                    
                    for (Tower tower : towers) {
                        tower.attack(enemies);
                    }
                    
                    projectiles.removeIf(p -> p.hasHit());
                    for (tower.projectile p : projectiles) {
                        p.update();
                    }
                }
                draw(); 
            }
        };
         timer.start();
     }
    
    private void showTowerRadialMenu(int x, int y) {
        if (towerMenu != null) {
            getChildren().remove(towerMenu);
            towerMenu = null;
        }

        double clicked_tile_x = x * tileWidth + tileWidth / 2;
        double clicked_tile_y = y * tileHeight + tileHeight / 2;
        double radius = tileWidth * 0.8; 

        String[] images_tower = { "file:Tower_Game/Towers/archer.png", "file:Tower_Game/Towers/mage.png","file:Tower_Game/Towers/artillery.png"};
        

        Pane radialMenu = new Pane();
        radialMenu.setPickOnBounds(false);

        for (int i = 0; i < 3; i++) {
            double angle = Math.toRadians(90 + i * 120);
            double button_x = clicked_tile_x + radius * Math.cos(angle) - 32; // button center
            double button_y = clicked_tile_y + radius * Math.sin(angle) - 32;

            Button btn = new Button();
            btn.setGraphic(new ImageView(new Image(images_tower[i], 64, 64, true, true)));
            btn.setStyle("-fx-background-color: transparent;");
           

            btn.setLayoutX(button_x);
            btn.setLayoutY(button_y);

            final int tower_type = i;
            btn.setOnAction(e -> {
                if (tower_type == 0) {
                    tower_Archer archerTower = new tower_Archer((int)(x * tileWidth), (int)(y * tileHeight), (int)tileWidth);
                    archerTower.setGameScreen(this); 
                    towers.add(archerTower);
                } else if (tower_type == 1) {
                    tower_Mage mageTower = new tower_Mage((int)(x * tileWidth), (int)(y * tileHeight), (int)tileWidth);
                    mageTower.setGameScreen(this); 
                    towers.add(mageTower);
                } else if (tower_type == 2) {
                    tower_Artillery artilleryTower = new tower_Artillery((int)(x * tileWidth), (int)(y * tileHeight), (int)tileWidth);
                    artilleryTower.setGameScreen(this); 
                    towers.add(artilleryTower);
                }
                manage_game.player.add_gold(-50);
                getChildren().remove(radialMenu);
                towerMenu = null;
                draw();
            });

            radialMenu.getChildren().add(btn);
        }

        getChildren().add(radialMenu);
        towerMenu = radialMenu;
    }
    
    //PAUSE GAME STARTS
    private void pauseUI() {
        getChildren().clear();
        getChildren().add(canvas);

        if (pause == null) {
            Image pauseImage = new Image("file:Tower_Game/UI_assets/pause_button.png");
            pauseIcon = new ImageView(pauseImage);
            Image resumeImage = new Image("file:Tower_Game/UI_assets/resume_button.png");
            resumeIcon = new ImageView(resumeImage);
            
            pauseIcon.setFitWidth(30);
            pauseIcon.setFitHeight(30);
            resumeIcon.setFitWidth(30);
            resumeIcon.setFitHeight(30);
            
            pause = new Button();
            pause.setGraphic(pauseIcon);
            pause.getStyleClass().add("pause-button");
            pause.setLayoutX(screenWidth - 50);
            pause.setLayoutY(10);
            
            pause.setPadding(Insets.EMPTY);
            pause.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            pause.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        }
        
        if (speedButton == null) {

            Image normal_speed = new Image("file:Tower_Game/UI_assets/buttons (1_1).png");
            Image fast = new Image("file:Tower_Game/UI_assets/buttons_(1_0).png");

            ImageView speedView = new ImageView(normal_speed);
            speedView.setFitWidth(30);
            speedView.setFitHeight(30);

            speedButton = new Button();
            speedButton.setGraphic(speedView);
            speedButton.setPadding(Insets.EMPTY);
            speedButton.getStyleClass().add("speed-button");
            
            Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            double screenWidth = screenBounds.getWidth();
            speedButton.setLayoutX(screenWidth - 150);
            speedButton.setLayoutY(10);

            speedButton.setOnAction(e -> {

                if (speedMultiplier == 1.0) {

                    speedMultiplier = 2.0;
                    speedView.setImage(fast);
                } else {

                    speedMultiplier = 1.0;
                    speedView.setImage(normal_speed);
                }
            });
        }

        if (createPause == null) {
            Rectangle overlay = new Rectangle(screenWidth, screenHeight, Color.rgb(0, 0, 0, 0.5));
            createPause = new StackPane(overlay);
            createPause.setVisible(false);
            createPause.getStyleClass().add("pause-overlay");
            createPause.setMouseTransparent(true);
        }

        getChildren().addAll(pause,speedButton,createPause);
        
        pause.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent arg0) {
        		 isPaused = !isPaused;
        	     createPause.setVisible(isPaused);
        	     pause.setGraphic(isPaused ? resumeIcon : pauseIcon);
        	}
        });
    } // PAUSE GAME ENDS
    
    
    //GOLD LIFE SHIELD UI STARTS
    private void gold_life_shieldUI() {
    	Image coin = new Image("file:Tower_Game/UI_assets/coin.png");
        Image heart = new Image("file:Tower_Game/UI_assets/health.png");
        Image shield = new Image("file:Tower_Game/UI_assets/shield.png");
        Image slot = new Image("file:Tower_Game/UI_assets/Button_Blue_3Slides.png");
        
        coinIcon = new ImageView(coin);
        heartIcon = new ImageView(heart);
        shieldIcon = new ImageView(shield);
        slotIcon = new ImageView(slot);
        
        coinIcon.setFitWidth(35);
        coinIcon.setFitHeight(35);
        heartIcon.setFitHeight(35);
        heartIcon.setFitWidth(35);
        shieldIcon.setFitWidth(35);
        shieldIcon.setFitHeight(35);

        
        goldText = new Text("0");
        livesText = new Text("0");
        WaveCounterText = new Text("0");
//        shieldsText = new Text(); will need after we write the shield thing
        
        goldText.setFill(Color.WHITE);
        livesText.setFill(Color.WHITE);
        WaveCounterText.setFill(Color.WHITE);
        
        goldText.setFont(Font.font(17));
        livesText.setFont(Font.font(17));
        WaveCounterText.setFont(Font.font(17));
        
        double slotWidth = 110;
        double slotHeight = 45;
        
        ImageView coinSlot = new ImageView(slot);
        ImageView heartSlot = new ImageView(slot);
        ImageView shieldSlot = new ImageView(slot);
        
        coinSlot.setFitWidth(slotWidth);
        coinSlot.setFitHeight(slotHeight);
        heartSlot.setFitWidth(slotWidth);
        heartSlot.setFitHeight(slotHeight);
        shieldSlot.setFitWidth(slotWidth);
        shieldSlot.setFitHeight(slotHeight);
        
        StackPane coinStack = new StackPane(coinSlot, goldText);
        StackPane heartStack = new StackPane(heartSlot, livesText);
        StackPane shieldStack = new StackPane(shieldSlot, WaveCounterText);
        
        StackPane.setAlignment(goldText, Pos.TOP_CENTER);
        StackPane.setMargin(goldText, new Insets(5, 0, 0, 5));
        StackPane.setAlignment(livesText, Pos.TOP_CENTER);
        StackPane.setMargin(livesText, new Insets(5, 0, 0, 5));
        StackPane.setAlignment(WaveCounterText, Pos.TOP_CENTER);
        StackPane.setMargin(WaveCounterText, new Insets(5, 0, 0, 5));
        
        HBox coinRow = new HBox(5, coinIcon, coinStack);
        HBox heartRow = new HBox(5, heartIcon, heartStack);
        HBox shieldRow = new HBox(5, shieldIcon, shieldStack);
        
        VBox icons = new VBox(10, coinRow, heartRow, shieldRow);
        
        hudContainer = new StackPane(icons);
        hudContainer.setAlignment(Pos.TOP_LEFT);
        hudContainer.setLayoutX(20);
        hudContainer.setLayoutY(20);
        
        getChildren().add(hudContainer);
        
    }
    
    private void updateGoldLife() {
        goldText.setText(String.valueOf(manage_game.player.get_gold()));
        livesText.setText(String.valueOf(manage_game.player.get_lives())+ "/" + manage_game.get_maxlives());
        WaveCounterText.setText(String.valueOf(TrackEnemyWave.get_wavecount()) + "/" + OptionsHandler.getInstance().getWaveCount());

    }//HUD UI
    
    // drawing map, towers, enemies, health bars, HUD
    private void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
      

        // drawing each tile on grid
        for (int y = 0; y < TILE_ROWS; y++) {
            for (int x = 0; x < TILE_COLS; x++) {
                Tile tile = map.get_tile(x, y);
                Image tileImage = null;

                // Color based on tile type
                if (tile instanceof PathTile) {
                	tileImage = tile.getImage(); // path
                } else if (tile instanceof BuildableTile) {
                	tileImage = tile.getImage(); // buildable
                } else if (tile instanceof DecorativeTile) {
                    tileImage = tile.getImage(); }
                    
                else {
                    tileImage = new Image("file:Tower_Game/Tiles/grass_tile2.png");
                }
                
                double px = x * tileWidth;
                double py = y * tileHeight;
                
                if (tileImage != null) {
                	g.drawImage(tileImage, px, py, tileWidth, tileHeight);
                } else {
                    g.setFill(Color.LIGHTGREEN); // fallback
                    g.fillRect(px, py, tileWidth, tileHeight);
                }

                // draw tile
                
                g.setStroke(Color.BLACK); // tile border
                g.strokeRect(px, py, tileWidth, tileHeight);
            }
        }
        
        
        
        // draw towers 
        for (Tower tower : towers) {
            Image towerImage = tower.getImage(); 
            double tower_x = tower.getX();
            double tower_y = tower.getY();
            if (towerImage != null) {
            	g.drawImage(towerImage, tower_x, tower_y, tileWidth, tileHeight);
            } else {
                g.setFill(Color.GRAY); //fallback
                g.fillOval(tower_x, tower_y, tileWidth, tileHeight);
            }
        }


        // draw enemies and health bars
        for (Enemy enemy : enemies) {
            Point2D.Double p = enemy.getPosition();
            double enemy_x = p.x;
            double enemy_y = p.y;

            // enemy body
            if (enemy instanceof enemy_Goblin) {
                enemy_Goblin goblin = (enemy_Goblin) enemy;

                goblin.updateAnimation(System.nanoTime());

                g.drawImage(goblin.getCurrentImage(), enemy_x - tileWidth * 0.25, enemy_y - tileHeight * 0.25, tileWidth * 1.25, tileHeight * 1.25);
            }
           
            if (enemy instanceof enemy_Knight) {
                enemy_Knight knight = (enemy_Knight) enemy;

                knight.updateAnimation(System.nanoTime());

                g.drawImage(knight.getCurrentImage(), enemy_x - tileWidth * 0.25, enemy_y - tileHeight * 0.25, tileWidth * 1.5, tileHeight * 1.5);
            }

            // health bar
            int bar_width = (int)(tileWidth / 2);
            int bar_height = (int)(tileHeight / 8);
            int hp = enemy.getHp();
            int maxHp = enemy.getMaxHp();
            int filled = (int) ((hp / (double) maxHp) * bar_width);

            double barX = enemy_x + (tileWidth - bar_width) / 2.5;
            double barY = enemy_y + tileHeight + 1.8;

            g.setFill(Color.GRAY);
            g.fillRect(barX, barY, bar_width, bar_height);
            g.setFill(Color.RED);
            g.fillRect(barX, barY, filled, bar_height);
            g.setStroke(Color.WHITE);
            g.strokeRect(barX, barY, bar_width, bar_height);
        }

        updateGoldLife();
        
        for (tower.projectile p : projectiles) {
            p.draw(g);
        }
    }
}