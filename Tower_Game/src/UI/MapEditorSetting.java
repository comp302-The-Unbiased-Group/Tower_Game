package UI;

import MapEdit.Map_edit;
import game.Map;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.Point;

public class MapEditorSetting extends BorderPane {
    private static final int TILE_SIZE = 32;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 15;

    private Map_edit logic = new Map_edit(GRID_WIDTH, GRID_HEIGHT);
    private Canvas canvas = new Canvas(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    public MapEditorSetting(Stage stage) {
        logic.initializeGrid();
        setupUI(stage);
        drawGrid();
    }

    private void setupUI(Stage stage) {
        VBox tileSelector = createTileSelector();
        setLeft(tileSelector);
        setCenter(canvas);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTilePlacement);
    }

    private VBox createTileSelector() {
        VBox tileSelector = new VBox(10);
        tileSelector.setPrefWidth(150);
        tileSelector.setStyle("-fx-background-color: #333333;");
        tileSelector.setAlignment(Pos.TOP_CENTER);

        for (Map_edit.TileType type : Map_edit.TileType.values()) {
            Button btn = new Button(type.name());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setOnAction(e -> logic.setSelectedTile(type));
            tileSelector.getChildren().add(btn);
        }

        Button saveBtn = new Button("Save Map");
        saveBtn.setOnAction(e -> {
            String error = logic.validateMap();
            if (error != null) {
                showError(error);
            } else if (!logic.saveMap("custom_map.txt")) {
                showError("Failed to save map.");
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Map saved successfully.");
                alert.showAndWait();
            }
        });

        tileSelector.getChildren().add(saveBtn);

        return tileSelector;
    }

    private void handleTilePlacement(MouseEvent e) {
        int x = (int) (e.getX() / TILE_SIZE);
        int y = (int) (e.getY() / TILE_SIZE);

        String error = logic.placeTile(x, y);
        if (error != null) showError(error);
        drawGrid();
    }

    private void drawGrid() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                switch (logic.getTile(x, y)) {
                    case PATH -> gc.setFill(Color.BROWN;
                    case START -> gc.setFill(Color.GREEN);
                    case END -> gc.setFill(Color.RED);
                    case TOWER_PREPLACED -> gc.setFill(Color.DARKBLUE);
                    case TOWER_EMPTY -> gc.setFill(Color.LIGHTBLUE);
                    case GRASS -> gc.setFill(Color.LIGHTGREEN);
                    case DECORATIVE -> gc.setFill(Color.GRAY);
                }
                gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


 

