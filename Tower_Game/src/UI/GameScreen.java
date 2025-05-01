package UI;
import application.*;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Rectangle;

import javafx.scene.image.Image;

public class GameScreen extends Pane {

    // each tile on the grid is 64x64 pixels
    private static final int TILE_SIZE = 64; 

    // core components of the game
    private Manage_game manage_game;
    private List<Enemy> enemies;
    private List<Tower> towers;
    private Map map;
    private Canvas canvas;
    
    Button pause;
    ImageView pauseIcon;
    ImageView resumeIcon;
    boolean isPaused = false;
    StackPane createPause;

    public GameScreen(Stage stage) {

        // set size of game screen and create canvas
        setPrefSize(1280, 720);
        canvas = new Canvas(1280, 720);
        getChildren().add(canvas);
        setupUI();

        towers = new ArrayList<>();
        map = new Map(20, 15); // 20 tiles wide, 15 tiles tall
        Player player = new Player(500, 100); // player starts with 500 gold and 100 lives

        // define straight horizontal path at row 7, from tile (0,7) to (14,7)
        for (int x = 0; x < 15; x++) {
            map.set_tile(x, 7, new PathTile(x, 7)); // mark tile as path
            map.add_path_point(new Point(x * TILE_SIZE, 7 * TILE_SIZE)); // add to enemy path
        }

        // set where enemies spawn and where they should reach
        map.set_start_point(new Point(0 * TILE_SIZE, 7 * TILE_SIZE));
        map.set_end_point(new Point(15 * TILE_SIZE, 7 * TILE_SIZE));

        // buildable tiles where towers can be placed
        map.set_tile(6, 6, new BuildableTile(6, 6));
        map.set_tile(7, 6, new BuildableTile(7, 6));

        // start game logic 
        manage_game = new Manage_game();
        manage_game.startGame(map, player);
        enemies = manage_game.enemies;
        manage_game.spawnWave();

        // handle clicks for placing towers
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            int x = (int) e.getX() / TILE_SIZE;
            int y = (int) e.getY() / TILE_SIZE;

            Tile tile = map.get_tile(x, y);
            
            if (tile instanceof BuildableTile) {
                int px = x * TILE_SIZE;
                int py = y * TILE_SIZE;

                // check if tower is already placed at that tile
                boolean taken = towers.stream().anyMatch(t -> t.x == px && t.y == py);

                // if tile is free and player has enough gold place a tower
                if (!taken && manage_game.player.can_afford(50)) {
                    towers.add(new tower_Archer(px, py));
                    manage_game.player.add_gold(-50);
                }
            }
        });

        // main game loop which updates game logic and redraws screen every frame
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!isPaused) {
                    manage_game.updateGame();
                }
                draw(); 
            }
        };
        timer.start();
    }
    

    
    //PAUSE GAME STARTS
    private void setupUI() {
        getChildren().clear();
        getChildren().add(canvas);

        if (pause == null) {
            Image pauseImage = new Image("file:Tower_Game/UI/pause_button.png");
            pauseIcon = new ImageView(pauseImage);
            Image resumeImage = new Image("file:Tower_Game/UI/resume_button.png");
            resumeIcon = new ImageView(resumeImage);
            
            pauseIcon.setFitWidth(30);
            pauseIcon.setFitHeight(30);
            resumeIcon.setFitWidth(30);
            resumeIcon.setFitHeight(30);
            
            pause = new Button();
            pause.setGraphic(pauseIcon);
            pause.getStyleClass().add("pause-button");
            pause.setLayoutX(1280 - 50);
            pause.setLayoutY(10);
            
            pause.setPadding(Insets.EMPTY);
            pause.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
            pause.setMaxSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        }

        if (createPause == null) {
            Rectangle overlay = new Rectangle(1280, 720, Color.rgb(0, 0, 0, 0.5));
            createPause = new StackPane(overlay);
            createPause.setVisible(false);
            createPause.getStyleClass().add("pause-overlay");
            createPause.setMouseTransparent(true);
        }

        getChildren().addAll(pause, createPause);
        
        pause.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent arg0) {
        		 isPaused = !isPaused;
        	     createPause.setVisible(isPaused);
        	     pause.setGraphic(isPaused ? resumeIcon : pauseIcon);
        	}
        });
    } // PAUSE GAME ENDS
    
    
    
    
    
    // drawing map, towers, enemies, health bars, HUD
    private void draw() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // drawing each tile on grid
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 20; x++) {
                Tile tile = map.get_tile(x, y);

                // Color based on tile type
                if (tile instanceof PathTile) {
                    g.setFill(Color.DARKBLUE); // path
                } else if (tile instanceof BuildableTile) {
                    g.setFill(Color.LIGHTGRAY); // buildable
                } else {
                    g.setFill(Color.YELLOW); // grass
                }

                // draw tile
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setStroke(Color.BLACK); // tile border
                g.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }

        // draw towers as blue circles
        g.setFill(Color.GRAY);
        for (Tower tower : towers) {
            g.fillOval(tower.x, tower.y, TILE_SIZE, TILE_SIZE);
        }

        // draw enemies and health bars
        for (Enemy enemy : enemies) {
            Point p = enemy.getPosition();

            // enemy body
            g.setFill(Color.RED);
            g.fillOval(p.x, p.y, TILE_SIZE / 2, TILE_SIZE / 2);

            // health bar
            int bar_width = TILE_SIZE / 2;
            int bar_height = 4;
            int hp = enemy.getHp();
            int maxHp = enemy.getMaxHp();
            int filled = (int) ((hp / (double) maxHp) * bar_width);

            // drawing HP bar of enemy
            g.setFill(Color.GRAY); // background
            g.fillRect(p.x, p.y - 6, bar_width, bar_height);
            g.setFill(Color.GREEN); // current HP
            g.fillRect(p.x, p.y - 6, filled, bar_height);
            g.setStroke(Color.BLACK); // outline
            g.strokeRect(p.x, p.y - 6, bar_width, bar_height);
        }

        // display player gold and lives
        g.setFill(Color.BLACK);
        
        String goldText = "Gold: " + manage_game.player.get_gold();
        String livesText = "Lives: " + manage_game.player.get_lives();

        
        double textX = 20; // 120 is padding to the left from the right edge

        g.fillText(goldText, textX, 30);   // y=30 for first line
        g.fillText(livesText, textX, 50);  // y=50 for second line
    }
}

