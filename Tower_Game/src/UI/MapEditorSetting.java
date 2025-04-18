package UI;
import application.*;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MapEditorSetting extends BorderPane {
	private static final int TILE_SIZE = 64;
    private final Map map = new Map(20, 15);
    private final Canvas canvas = new Canvas(1280, 720);
    private TileType selected_tile_type = TileType.PATH;

    enum TileType { PATH, BUILDABLE, DECORATIVE }

    public MapEditorSetting(Stage stage) {
       
    	
    }
}
 

