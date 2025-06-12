package UI;
import MapEdit.Map_edit;
import UI.EndGameStats;
import UI.GameOverScreen;
import UI.MainMenu;
import UI.OptionScreen;
import application.*;
import enemy.*;
import game.Map;
import game.Manage_game;
import tile.*;
import tower.*;


import enemy.enemy_Goblin;
import enemy.enemy_Knight;
import enemy.enemy_TNT;

import utils.OptionsHandler;
import game.TrackEnemyWave;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.geometry.Rectangle2D;
import javafx.scene.ImageCursor;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.Cursor;
import javafx.scene.Group;

import Gold.Gold_Drop;

import static tile.PathTile.PathType.*;

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

    private double speedMultiplier = OptionsHandler.getInstance().getspeedmultiplier();

    private Button speedButton;
    private Button optionsButton;
    private List<Tower> towers;
    private Map map;
    private Canvas canvas;
    private List<projectile> projectiles = new ArrayList<>();
    private List<Gold_Drop> goldDropList = new ArrayList<>();
    private Group towerMenu;
    private BuildableTile hoveredTile;
    
    public void addProjectile(projectile p) {
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

    // for special effects on enemies
    Image thunderIcon;
    Image snowIcon;
    Image healIcon;

    public GameScreen(Stage stage, File file) {

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
        
        if (file == null) {
            // Default map setup for new game
       
        // horizontal: from (0,7) to (3,7)
        for (int x = 0; x <= 3; x++) {
            map.set_tile(x, 7, new PathTile(x, 7, PathTile.PathType.HORIZONTAL));
            map.add_path_point(new Point((int)(x * tileWidth), (int)(7 * tileHeight)));
        }

        //corner: (4,7) turning down
        map.set_tile(4, 7, new PathTile(4, 7, PathTile.PathType.CORNER_TOPRIGHT));
        map.add_path_point(new Point((int)(4 * tileWidth), (int)(7 * tileHeight)));

        // vertical: from (4,8) to (4,11)
        for (int y = 8; y <= 11; y++) {
            map.set_tile(4, y, new PathTile(4, y, PathTile.PathType.VERTICAL));
        	map.add_path_point(new Point((int)(4 * tileWidth), (int)(y * tileHeight)));
        }
        // corner: (4,12) turning right
        map.set_tile(4, 12, new PathTile(4, 12, PathTile.PathType.CORNER_BOTTOMLEFT));
        map.add_path_point(new Point((int)(4 * tileWidth), (int)(12 * tileHeight)));

        // horizontal: from (5,12) to (8,12)
        for (int x = 5; x <= 8; x++) {
            map.set_tile(x, 12, new PathTile(x, 12, PathTile.PathType.HORIZONTAL));
            map.add_path_point(new Point((int)(x * tileWidth), (int)(12 * tileHeight)));
        }

        // corner: (9,12) turning up
        map.set_tile(9, 12, new PathTile(9, 12, PathTile.PathType.CORNER_BOTTOMRIGHT));
        map.add_path_point(new Point((int)(9 * tileWidth), (int)(12 * tileHeight)));

       // vertical: (9,11) and (9,7)
        for (int y = 11; y >= 7; y--) {
            map.set_tile(9, y, new PathTile(9, y, PathTile.PathType.VERTICAL));
            map.add_path_point(new Point((int)(9 * tileWidth), (int)(y * tileHeight)));
        }

        // corner: (9,6) turning right
        map.set_tile(9, 6, new PathTile(9, 6, PathTile.PathType.CORNER_TOPLEFT));
        map.add_path_point(new Point((int)(9 * tileWidth), (int)(6 * tileHeight)));

        // horizontal: from (10,6) to (14,6)
        for (int x = 10; x <= 14; x++) {
            map.set_tile(x, 6, new PathTile(x, 6, PathTile.PathType.HORIZONTAL));
            map.add_path_point(new Point((int)(x * tileWidth), (int)(6 * tileHeight)));
        }
        //corner: (15,6)
        map.set_tile(15, 6, new PathTile(15, 6, PathTile.PathType.CORNER_BOTTOMRIGHT));
        map.add_path_point(new Point((int)(15 * tileWidth), (int)(6 * tileHeight)));
        
     // vertical: (15,5) and (15,0)
        for (int y = 5; y >= 0; y--) {
            map.set_tile(15, y, new PathTile(15, y, PathTile.PathType.VERTICAL));
            map.add_path_point(new Point((int)(15 * tileWidth), (int)(y * tileHeight)));
        }


        // set starting and ending tile points.
        map.set_start_point(new Point(0, 7));
        map.set_end_point(new Point(11, 0));

        // buildable tiles where towers can be placed
        map.set_tile(6, 11, new BuildableTile(6, 11));
        map.set_tile(7, 11, new BuildableTile(7, 11));
        
        map.set_tile(11, 5, new BuildableTile(11, 5));
        map.set_tile(12, 5, new BuildableTile(12, 5));
        
        map.set_tile(8, 6, new DecorativeTile(8,6,"mushroom"));
        map.set_tile(13, 10, new DecorativeTile(13,10,"gold_mine"));
        map.set_tile(12, 13, new DecorativeTile(12,13,"gold_mine"));
        map.set_tile(3, 4, new DecorativeTile(3,4,"castle"));
        map.set_tile(4, 4, new DecorativeTile(4,4,"tree"));
        map.set_tile(7, 3, new DecorativeTile(7,3,"tree"));
        map.set_tile(8, 3, new DecorativeTile(8,3,"tree"));
        map.set_tile(14, 14, new DecorativeTile(14,14,"skelaton"));
        map.set_tile(5, 4, new DecorativeTile(5,4,"tree"));
        map.set_tile(13, 13, new DecorativeTile(13,13,"tree"));
        map.set_tile(2, 13, new DecorativeTile(2,13,"skelaton"));
        map.set_tile(16, 12, new DecorativeTile(16,12,"skelaton"));
        map.set_tile(17, 12, new DecorativeTile(17,12,"skelaton"));
        map.set_tile(18, 12, new DecorativeTile(18,12,"skelaton"));
        map.set_tile(19, 12, new DecorativeTile(19,12,"skelaton"));
        
        }else {
            // Load map from file
            try {
                loadFromFile(file.getAbsolutePath(), map);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        
        // start game logic
        manage_game = new Manage_game();

        manage_game.startGame(map,OptionsHandler.getInstance().getStartGold(), OptionsHandler.getInstance().getPlayerHP());
        
     // Load towers if file was provided
        if (file != null) {
            for (Map.TowerPlacement spawn : map.getTowerPlacements()) {
                

                Tower tower = switch (spawn.type) {
                    case "ARCHER_TOWER" -> new tower_Archer(spawn.x, spawn.y, (int)tileWidth, this);
                    case "MAGE_TOWER" -> new tower_Mage(spawn.x, spawn.y, (int)tileWidth, this);
                    case "ARTILLERY_TOWER" -> new tower_Artillery(spawn.x, spawn.y, (int)tileWidth, this);
                    default -> null;
                };

                if (tower != null) {
                    manage_game.addTower(tower);
                    towers.add(tower);
                }
            }
        }
        manage_game.set_go_Listener(() -> {
            Platform.runLater(() -> {
                GameOverScreen GameOverNow = new GameOverScreen(stage);
                Scene GAME_OVER = GameOverNow.newScene();
                stage.setScene(GAME_OVER);
            });
        });

        manage_game.set_gs_handler(enemy_total -> {
            Platform.runLater(() -> {
                EndGameStats winScreen = new EndGameStats(stage, enemy_total);
                stage.setScene(winScreen.newScene());
                stage.setFullScreen(true);
                stage.toFront();
                stage.requestFocus();
            });
        });


        manage_game.set_gold_handler((amount, x, y) -> {

            Gold_Drop drop = new Gold_Drop(amount, x, y,this, manage_game.getPlayer());
            goldDropList.add(drop); // if you're tracking them
        });
        enemies = manage_game.enemies;
        
        // mouse clicking for placing towers
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
        	int x = (int)(e.getX() / tileWidth);
            int y = (int)(e.getY() / tileHeight);

            Tile tile = map.get_tile(x, y);
            
            if (towerMenu != null) {
                getChildren().remove(towerMenu);
                towerMenu = null;
                hoveredTile = null;
            }
            
            if (tile instanceof BuildableTile) {
               

                // check if tower is already placed at that tile
            	boolean taken = towers.stream().anyMatch(t -> t.getX() == x && t.getY() == y);
                // if tile is free and player has enough gold place a tower
                if (!taken && manage_game.player.can_afford(50)) {
                	
                	showTower(x, y);
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
                    manage_game.updateGame(speedMultiplier,tileWidth);
                    
                    for (Tower tower : towers) {
                        tower.attack(enemies);
                    }
                    
                    projectiles.removeIf(p -> p.hasHit());
                    for (projectile p : projectiles) {
                        p.update();
                    }
                }
                draw(); 
            }
        };
         timer.start();
         
         Image cursorImage = new Image("file:Tower_Game/UI_assets/01.png");
         String operating_system = System.getProperty("os.name").toLowerCase();
         if (operating_system.contains("mac")) {
             this.setCursor(Cursor.CROSSHAIR);
         } else {
        	 canvas.setCursor(new ImageCursor(cursorImage, cursorImage.getWidth() / 4, cursorImage.getHeight() / 4));
         }
         
         
     }
    
    public void loadFromFile(String path, game.Map map) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        if (lines.size() < 3) throw new IOException("Invalid map file");

        String[] startParts = lines.get(0).replace("START:", "").trim().split(",");
        String[] endParts = lines.get(1).replace("END:", "").trim().split(",");

        Point start = new Point(Integer.parseInt(startParts[0]), Integer.parseInt(startParts[1]));
        Point end = new Point(Integer.parseInt(endParts[0]), Integer.parseInt(endParts[1]));
        map.set_start_point(start);
        map.set_end_point(end);

        // Step 1: Build tile grid
        boolean[][] isPath = new boolean[TILE_COLS][TILE_ROWS];

        for (int y = 0; y < TILE_ROWS; y++) {
            String[] tokens = lines.get(y + 2).split(" ");
            for (int x = 0; x < TILE_COLS && x < tokens.length; x++) {
                String token = tokens[x].trim();

                switch (token) {
                    case "PATH_HORIZONTAL" -> {
                        map.set_tile(x, y, new PathTile(x, y, HORIZONTAL));
                        isPath[x][y] = true;
                    }
                    case "PATH_VERTICAL" -> {
                        map.set_tile(x, y, new PathTile(x, y, VERTICAL));
                        isPath[x][y] = true;
                    }
                    case "PATH_TOP_LEFT" -> {
                        map.set_tile(x, y, new PathTile(x, y, CORNER_TOPLEFT));
                        isPath[x][y] = true;
                    }
                    case "PATH_TOP_RIGHT" -> {
                        map.set_tile(x, y, new PathTile(x, y, CORNER_TOPRIGHT));
                        isPath[x][y] = true;
                    }
                    case "PATH_BOTTOM_LEFT" -> {
                        map.set_tile(x, y, new PathTile(x, y, CORNER_BOTTOMLEFT));
                        isPath[x][y] = true;
                    }
                    case "PATH_BOTTOM_RIGHT" -> {
                        map.set_tile(x, y, new PathTile(x, y, CORNER_BOTTOMRIGHT));
                        isPath[x][y] = true;
                    }
                    case "ARCHER_TOWER", "MAGE_TOWER", "ARTILLERY_TOWER" -> {
                        map.getTowerPlacements().add(new Map.TowerPlacement(x, y, token));
                    }
                    case "TOWER_EMPTY" -> map.set_tile(x, y, new BuildableTile(x, y));
                    case "DECORATIVE" -> map.set_tile(x, y, new DecorativeTile(x, y, "tree"));
                    default -> map.set_tile(x, y, new DecorativeTile(x, y, "grass"));
                }
            }
        }

        // Step 2: Rebuild path from start to end using BFS
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();
        java.util.Map<Point, Point> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Point curr = queue.poll();
            if (curr.equals(end)) break;

            int[][] dirs = {{0,1},{1,0},{0,-1},{-1,0}};
            for (int[] d : dirs) {
                int nx = curr.x + d[0];
                int ny = curr.y + d[1];
                if (nx >= 0 && ny >= 0 && nx < TILE_COLS && ny < TILE_ROWS && isPath[nx][ny]) {
                    Point np = new Point(nx, ny);
                    if (!visited.contains(np)) {
                        visited.add(np);
                        parent.put(np, curr);
                        queue.add(np);
                    }
                }
            }
        }

        // Step 3: Backtrack path from end to start
        List<Point> pathTiles = new ArrayList<>();
        Point p = end;
        while (p != null && parent.containsKey(p)) {
            pathTiles.add(p);
            p = parent.get(p);
        }
        pathTiles.add(start);
        Collections.reverse(pathTiles);

        for (Point tile : pathTiles) {
            int pixelX = (int)((tile.x ) * tileWidth);
            int pixelY = (int)((tile.y ) * tileHeight);
            map.add_path_point(new Point(pixelX, pixelY));
        }

        System.out.println("✔ Map loaded: Start=" + start + ", End=" + end + ", Path=" + map.get_path().size() + " points");
    }

    
    	
    
    
    
    private void showTower(int x, int y) {
        if (towerMenu != null) {
            getChildren().remove(towerMenu);
            towerMenu = null;
        }

        double clicked_tile_x = (x + 0.5) * tileWidth;
        double clicked_tile_y = (y + 0.5) * tileHeight;
         
        
        
        Tower existingTower = towers.stream().filter(t -> 
        		t.getX() == x * (int)tileWidth && 
        		t.getY() == y * (int)tileHeight).findFirst().orElse(null);

		if (existingTower == null) {
		showTowerMenu(x, y, clicked_tile_x, clicked_tile_y);
		} else {
		showUpgradeSellMenu(x, y, clicked_tile_x, clicked_tile_y, existingTower);
		}
    }
    
    private void showTowerMenu(int x, int y, double centerX, double centerY) {
    	double radius = 60;
    	
        towerMenu = new Group();
        
        Circle circle = new Circle(radius);
        circle.setCenterX(centerX);
        circle.setCenterY(centerY);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(1.5);
        towerMenu.getChildren().add(circle);
        
        for (int i = 0; i < 4; i++) {
        	double angle;
        	if (i == 0) {
        	    angle = 90;
        	} else if (i == 1) {
        	    angle = 210; 
        	} else {
        	    angle = 330;
        	}
        	
        	double angleRad = Math.toRadians(angle);
            double button_x = centerX + radius * Math.cos(angleRad) - 20;
            double button_y = centerY + radius * Math.sin(angleRad) - 20;

            ImageView btn = new ImageView();
            btn.setFitWidth(40);
            btn.setFitHeight(40);
            btn.setX(button_x);
            btn.setY(button_y);
            
            if (i == 0) {
                btn.setImage(new Image("file:Tower_Game/UI_assets/buttons (2_0).png"));
                btn.setOnMouseClicked(event -> {
                	Tower newTower = new tower_Archer(x * (int)tileWidth, y * (int)tileHeight, (int)tileWidth, this);
                    int cost = newTower.getCost();
                	towers.add(newTower);
                    manage_game.player.add_gold(-cost);
                    draw();
                    getChildren().remove(towerMenu);
                    towerMenu = null;
                    event.consume();
                });
            } 
            else if (i == 1) {
                btn.setImage(new Image("file:Tower_Game/UI_assets/buttons (2_2).png"));
                btn.setOnMouseClicked(event -> {
                	Tower newTower = new tower_Mage(x * (int)tileWidth, y * (int)tileHeight, (int)tileWidth, this);
                    int cost = newTower.getCost();
                	towers.add(newTower);
                    manage_game.player.add_gold(-cost);
                    draw();
                    getChildren().remove(towerMenu);
                    towerMenu = null;
                    event.consume();
                });
            } 
            else {
                btn.setImage(new Image("file:Tower_Game/UI_assets/buttons (2_3).png"));
                btn.setOnMouseClicked(event -> {
                	Tower newTower = new tower_Artillery(x * (int)tileWidth, y * (int)tileHeight, (int)tileWidth, this);
                    int cost = newTower.getCost();
                	towers.add(newTower);
                    manage_game.player.add_gold(-cost);
                    draw();
                    getChildren().remove(towerMenu);
                    towerMenu = null;
                    event.consume();
                });
            }
            
            towerMenu.getChildren().add(btn);
        }
        
        getChildren().add(towerMenu);
    }
    
    private void showUpgradeSellMenu(int x, int y, double centerX, double centerY, Tower existingTower) {
    	if (towerMenu != null) {
            getChildren().remove(towerMenu);
            towerMenu = null;
        }
    	
    	double radius = 60;

    	double clicked_tile_x = (x + 0.5) * tileWidth;
        double clicked_tile_y = (y + 0.5) * tileHeight;
       
        towerMenu = new Group();
        
        Circle circle = new Circle(radius);
        circle.setCenterX(clicked_tile_x);
        circle.setCenterY(clicked_tile_y);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(1.5);
        towerMenu.getChildren().add(circle);
       

        ImageView upgrade_button = new ImageView();
        upgrade_button.setFitWidth(40);
        upgrade_button.setFitHeight(40);
        upgrade_button.setX(centerX - 20);
        upgrade_button.setY(centerY - radius - 20);
        
        ImageView sell_button = new ImageView();
        sell_button.setFitWidth(40);
        sell_button.setFitHeight(40);
        sell_button.setX(centerX - 20);
        sell_button.setY(centerY + radius - 20);
        
        if (existingTower instanceof tower_Archer) {
        	upgrade_button.setImage(new Image("file:Tower_Game/UI_assets/buttons (2_1).png"));
        	upgrade_button.setOnMouseClicked(event -> {
        		Tower upgraded = new tower_ArcherUp(x * (int)tileWidth, y * (int)tileHeight, (int)tileWidth, this);
        		int tower_cost = upgraded.getCost();
                if (manage_game.player.get_gold()>= tower_cost) {
                    towers.remove(existingTower);
                    manage_game.player.add_gold(-tower_cost);
                    towers.add(new tower_ArcherUp(x * (int) tileWidth, y * (int) tileHeight, (int) tileWidth, this));
                    closeTowerMenu();
                }
                closeTowerMenu();
            });
        } 
        else if (existingTower instanceof tower_Mage) {
        	upgrade_button.setImage(new Image("file:Tower_Game/UI_assets/buttons (2_1).png"));
        	upgrade_button.setOnMouseClicked(event -> {
        		Tower upgraded = new tower_MageUp(x * (int)tileWidth, y * (int)tileHeight, (int)tileWidth, this);
        		int tower_cost = upgraded.getCost();
                if (manage_game.player.get_gold()>= tower_cost) {
                    towers.remove(existingTower);
                    manage_game.player.add_gold(-tower_cost);
                    towers.add(new tower_MageUp(x * (int) tileWidth, y * (int) tileHeight, (int) tileWidth, this));
                    closeTowerMenu();
                }
                closeTowerMenu();
            });
        }
        else if (existingTower instanceof tower_Artillery) {
        	upgrade_button.setImage(new Image("file:Tower_Game/UI_assets/buttons (2_1).png"));
        	upgrade_button.setOnMouseClicked(event -> {
        		Tower upgraded = new tower_ArtilleryUp(x * (int)tileWidth, y * (int)tileHeight, (int)tileWidth, this);
        		int tower_cost = upgraded.getCost();
                if (manage_game.player.get_gold()>= tower_cost) {
                    towers.remove(existingTower);
                    manage_game.player.add_gold(-tower_cost);
                    towers.add(new tower_ArtilleryUp(x * (int) tileWidth, y * (int) tileHeight, (int) tileWidth, this));
                    closeTowerMenu();
                }
                closeTowerMenu();
            });
        }
        
        sell_button.setImage(new Image("file:Tower_Game/UI_assets/sell.png"));
        sell_button.setOnMouseClicked(event -> {
            towers.remove(existingTower);
            manage_game.player.add_gold(25);
            closeTowerMenu();
        });
        
        if (manage_game.player.get_gold() < 100) {

        	upgrade_button.setOpacity(0.5);
        } else {

        	upgrade_button.setOpacity(1.0);
        }
        
        towerMenu.getChildren().addAll(upgrade_button, sell_button);
        getChildren().add(towerMenu);
    }

    private void closeTowerMenu() {
    	 if (towerMenu != null) {
    	        getChildren().remove(towerMenu);
    	        towerMenu = null;
    	    }
    	    draw();
    	}
    
    //PAUSE GAME STARTS
    private void pauseUI() {
        getChildren().clear();
        getChildren().add(canvas);
        
        HBox threeButtons = new HBox(10);
        threeButtons.setAlignment(Pos.TOP_RIGHT);
        threeButtons.setLayoutX(screenWidth - 160);
        threeButtons.setLayoutY(10);

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
            pause.setPadding(Insets.EMPTY);
        }
        
        if (speedButton == null) {

            Image normal_speed = new Image("file:Tower_Game/UI_assets/speed.png");
            Image fast = new Image("file:Tower_Game/UI_assets/resume_button.png");

            ImageView speedView = new ImageView(normal_speed);
            speedView.setFitWidth(30);
            speedView.setFitHeight(30);

            speedButton = new Button();
            speedButton.setGraphic(speedView);
            speedButton.getStyleClass().add("speed-button");
            speedButton.setPadding(Insets.EMPTY);
            
            speedButton.setOnAction(e -> {

                if (speedMultiplier == OptionsHandler.getInstance().getspeedmultiplier()) {

                    speedMultiplier = speedMultiplier * 2.0;
                    speedView.setImage(fast);
                } else {

                    speedMultiplier = OptionsHandler.getInstance().getspeedmultiplier();
                    speedView.setImage(normal_speed);
                }
            });
        }

        Image options = new Image("file:Tower_Game/UI_assets/options.png");
        ImageView optionsView = new ImageView(options);
        optionsView.setFitWidth(30);
        optionsView.setFitHeight(30);
        
        optionsButton = new Button();
        optionsButton.setGraphic(optionsView);
        optionsButton.getStyleClass().add("options-button");
        optionsButton.setPadding(Insets.EMPTY);
        
        optionsButton.setOnAction(e -> {
            isPaused = true;
            pause.setGraphic(resumeIcon);
            
            Stage optionsStage = new Stage();
            OptionScreen optionScreen = new OptionScreen();
            Scene optionsScene = optionScreen.newScene(optionsStage);
            
            optionScreen.setSaveHandler(() -> {
                optionsStage.close();
                isPaused = false;
                pause.setGraphic(pauseIcon);
                speedMultiplier = OptionsHandler.getInstance().getspeedmultiplier();
                if (speedMultiplier == OptionsHandler.getInstance().getspeedmultiplier()) {
                    ((ImageView)speedButton.getGraphic()).setImage(
                        new Image("file:Tower_Game/UI_assets/speed.png"));
                }
            });
            
            optionsStage.setScene(optionsScene);
            optionsStage.initOwner(getScene().getWindow());
            optionsStage.showAndWait();
        });
        
        threeButtons.getChildren().addAll(speedButton, pause, optionsButton );
        
        if (createPause == null) {
            Rectangle overlay = new Rectangle(screenWidth, screenHeight, Color.rgb(0, 0, 0, 0.5));
            overlay.setMouseTransparent(true);

            VBox pauseMenuButtons = new VBox(20);
            pauseMenuButtons.setAlignment(Pos.CENTER);
            pauseMenuButtons.setLayoutX(screenWidth / 2 - 100);
            pauseMenuButtons.setLayoutY(screenHeight / 2 - 50);
            
            Button mainMenuButton = new Button("Main Menu");
            mainMenuButton.getStyleClass().add("pause-menu-button");
            mainMenuButton.setOnAction(e -> {
                Stage currentStage = (Stage) getScene().getWindow();
                MainMenu mainMenu = new MainMenu(currentStage);
                Scene mainMenuScene = mainMenu.newScene();
                currentStage.setScene(mainMenuScene);
                currentStage.setFullScreen(false);
            });
            
            Button quitButton = new Button("Quit Game");
            quitButton.getStyleClass().add("pause-menu-button");
            quitButton.setOnAction(e -> {
                Platform.exit();
            });
            
            pauseMenuButtons.getChildren().addAll(mainMenuButton, quitButton);
            
            StackPane overlayContainer = new StackPane();
            overlayContainer.getChildren().addAll(overlay, pauseMenuButtons);
            overlayContainer.setVisible(false);
            
            createPause = overlayContainer;
            createPause.getStyleClass().add("pause-overlay");  
        }

        getChildren().addAll(createPause, threeButtons);
        
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

        long now = System.nanoTime();
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
      

     // drawing each tile on grid
        for (int y = 0; y < TILE_ROWS; y++) {
            for (int x = 0; x < TILE_COLS; x++) {
                Tile tile = map.get_tile(x, y);
                Image tileImage = null;
                double px = x * tileWidth;
                double py = y * tileHeight;

                if (tile instanceof DecorativeTile) {
                    // Draw grass background
                    Image grass = new Image("file:Tower_Game/Tiles/grass_tile2.png");
                    g.drawImage(grass, px, py, tileWidth, tileHeight);

                   
                    Image decorative_img = tile.getImage();
                    double deco_w = tileWidth * 0.9;  
                    double deco_h = tileHeight * 0.9;
                    double deco_x = px + (tileWidth - deco_w) / 2;
                    double deco_y = py + (tileHeight - deco_h) / 2;
                    g.drawImage(decorative_img, deco_x, deco_y, deco_w, deco_h);
                } else {
                    
                    if (tile instanceof PathTile || tile instanceof BuildableTile) {
                        tileImage = tile.getImage();
                    } else {
                        tileImage = new Image("file:Tower_Game/Tiles/grass_tile2.png");
                    }
                    if (tileImage != null) {
                        g.drawImage(tileImage, px, py, tileWidth, tileHeight);
                    } else {
                        g.setFill(Color.LIGHTGREEN); // fallback
                        g.fillRect(px, py, tileWidth, tileHeight);
                    }
                }

                // Draw tile border for all tiles
                g.setStroke(Color.BLACK);
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
            
            if (enemy instanceof enemy_TNT) {
                enemy_TNT tnt = (enemy_TNT) enemy;
                tnt.updateAnimation(System.nanoTime());

                g.drawImage(tnt.getCurrentImage(), enemy_x - tileWidth * 0.25, enemy_y - tileHeight * 0.25, tileWidth * 1.5, tileHeight * 1.5);
            }
            if (enemy instanceof Enemy_Healer) {
                Enemy_Healer heal = (Enemy_Healer) enemy;
                heal.updateAnimation(System.nanoTime());

                g.drawImage(heal.getCurrentImage(), enemy_x - tileWidth * 0.25, enemy_y - tileHeight * 0.25, tileWidth * 1.5, tileHeight * 1.5);
            }


            // thunder icon
            if (enemy instanceof enemy_Knight && ((enemy_Knight) enemy).hasSynergy()) {

                thunderIcon = new Image("file:Tower_Game/Enemy_effects/thunder.png");
                double offsetY = tileHeight * 0.5;
                double thunderwidth = tileWidth / 2.8;
                double thunderheight = tileHeight / 2.8;
                g.drawImage(thunderIcon, enemy_x+ tileWidth/2.8, enemy_y - offsetY, thunderwidth, thunderheight);

            }
            if (enemy instanceof Enemy_Healer && ((Enemy_Healer) enemy).isHealing_on()) {

                healIcon = new Image("file:Tower_Game/Enemy_effects/health_icon.png");
                double offsetY = tileHeight * 0.5;
                double healwidth = tileWidth / 2.8;
                double healheight = tileHeight / 2.8;
                g.drawImage(healIcon, enemy_x+ tileWidth/2.8, enemy_y - offsetY, healwidth, healheight);

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
            
            if  (enemy.isSlowed()) {
                Image snowflake = new Image("file:Tower_Game/Enemy_effects/snow flake icon.png");
                g.drawImage(snowflake, barX + bar_width + 3, barY - 9, 22, 22);
            }
        }

        updateGoldLife();
        
        for (projectile p : projectiles) {
            p.draw(g);
        }
        for (Gold_Drop drop : goldDropList) {

            drop.update_goldAnimation(now,this);

        }
    }
}