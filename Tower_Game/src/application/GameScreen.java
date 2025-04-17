package application;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Pane {
    private static final int TILE_SIZE = 64;
    private Manage_game manage_game;
    private List<Enemy> enemies;
    private List<Tower> towers;
    private Map map;
    private Canvas canvas;
    
    public GameScreen(Stage stage) {
    	
    
        setPrefSize(1280, 720);
        canvas = new Canvas(1280, 720);
        getChildren().add(canvas);

        towers = new ArrayList<>();
        map = new Map(20, 15);
        Player player = new Player(100, 20);
    }
}

  
