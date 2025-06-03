package UI;

import MapEdit.Map_edit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MapEditorSetting extends BorderPane {
    private static int TILE_SIZE = 32;
    private static final int GRID_WIDTH = 20;
    private static final int GRID_HEIGHT = 15;

    private final Map_edit logic = new Map_edit(GRID_WIDTH, GRID_HEIGHT);
    private final Canvas canvas = new Canvas(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final Map<Map_edit.TileType, Image> tileImages = new HashMap<>();

    public MapEditorSetting(Stage stage) {
        double width = Screen.getPrimary().getBounds().getWidth() * 0.75;
        double height = Screen.getPrimary().getBounds().getHeight() * 0.75;

        TILE_SIZE = (int) Math.min(width / GRID_WIDTH, height / GRID_HEIGHT);
        canvas.setWidth(GRID_WIDTH * TILE_SIZE);
        canvas.setHeight(GRID_HEIGHT * TILE_SIZE);

        logic.initializeGrid();
        loadTileImages();
        setupUI(stage);
        drawGrid();
    }

    private void loadTileImages() {
        try {
            tileImages.put(Map_edit.TileType.PATH_HORIZONTAL, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/horizontal_path.png"));
            tileImages.put(Map_edit.TileType.PATH_VERTICAL, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/vertical_path.png"));
            tileImages.put(Map_edit.TileType.PATH_TOP_LEFT, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/bottom_right.png"));
            tileImages.put(Map_edit.TileType.PATH_TOP_RIGHT, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/bottom_left.png"));
            tileImages.put(Map_edit.TileType.PATH_BOTTOM_LEFT, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/top_right.png"));
            tileImages.put(Map_edit.TileType.PATH_BOTTOM_RIGHT, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/top_left.png"));
            tileImages.put(Map_edit.TileType.TOWER_EMPTY, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/tower_tile.png"));
            tileImages.put(Map_edit.TileType.GRASS, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/grass_tile2.png"));
            tileImages.put(Map_edit.TileType.DECORATIVE, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/tree1.png"));
            tileImages.put(Map_edit.TileType.ARCHER_TOWER, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/tile_128_6_2.png"));
            tileImages.put(Map_edit.TileType.MAGE_TOWER, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/tile_128_5_1.png"));
            tileImages.put(Map_edit.TileType.ARTILLERY_TOWER, new Image("file:/Users/berk/IdeaProjects/Tower_Game/Tower_Game/Tiles/tile_128_5_0.png"));

        } catch (Exception e) {
            showError("Failed to load tile images. Check if /Tiles/ folder is marked as Resources Root.");
        }
    }

    private void setupUI(Stage stage) {

        Button resetBtn = new Button("🔄 Reset Map");
        resetBtn.setStyle("""
    -fx-background-color: linear-gradient(to bottom, #f44336, #d32f2f);
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-font-size: 16px;
    -fx-padding: 10 20;
    -fx-background-radius: 25;
    -fx-border-radius: 25;
    -fx-cursor: hand;
""");

        resetBtn.setOnAction(e -> {
            boolean confirm = confirmReset();
            if (confirm) {
                logic.initializeGrid();
                drawGrid();
            }
        });
        Button saveBtn = new Button("💾 Save Map");
        saveBtn.setStyle("""
        -fx-background-color: linear-gradient(to bottom, #4CAF50, #388E3C);
        -fx-text-fill: white;
        -fx-font-weight: bold;
        -fx-font-size: 16px;
        -fx-padding: 10 20;
        -fx-background-radius: 25;
        -fx-border-radius: 25;
        -fx-cursor: hand;
        """);
        saveBtn.setOnAction(e -> {
            try {
                String error = logic.validateMap();
                if (error != null) {
                    logic.undoLastPlacement();  // Undo last bad placement
                    drawGrid();
                    showError(error);
                } else if (!logic.saveMap("custom_map.txt")) {
                    showError("Failed to save map.");
                }
            } catch (Exception ex) {
                showError("Unexpected error during save: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        Label title = new Label("Map Editor");
        title.setStyle("""
        -fx-font-size: 30px;
        -fx-font-family: 'Comic Sans MS', 'Arial';
        -fx-text-fill: #3e3e3e;
        -fx-effect: dropshadow(gaussian, lightgray, 2, 0, 1, 1);
        """);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(leftSpacer, title, rightSpacer, saveBtn, resetBtn);

        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(20, 30, 10, 30));
        topBar.setSpacing(10);
        topBar.setStyle("-fx-background-color: #ffffff;");

        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setStyle("-fx-background-color: #8FC645;");
        canvasWrapper.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        canvasWrapper.setPrefSize(canvas.getWidth(), canvas.getHeight());
        canvasWrapper.setAlignment(Pos.CENTER);

        ScrollPane canvasScroll = new ScrollPane(canvasWrapper);
        canvasScroll.setFitToWidth(true);
        canvasScroll.setFitToHeight(true);
        canvasScroll.setPannable(true);
        canvasScroll.setStyle("-fx-background: #8FC645;");

        ScrollPane tilePalette = createTileSelector();
        tilePalette.setPrefWidth(200);
        tilePalette.setStyle("""
        -fx-background-color: #fff9dc;
        -fx-border-color: #cfcfcf;
        -fx-border-width: 1;
        """);

        HBox layout = new HBox(canvasScroll, tilePalette);
        layout.setSpacing(10);
        HBox.setHgrow(canvasScroll, Priority.ALWAYS);

        VBox fullLayout = new VBox(topBar, layout);
        fullLayout.setStyle("-fx-background-color: #fefefe;");
        fullLayout.setSpacing(5);
        fullLayout.setAlignment(Pos.TOP_CENTER);

        setCenter(fullLayout);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleTilePlacement);
        canvas.setOnDragOver(e -> {
            if (e.getGestureSource() != canvas && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            e.consume();
        });

        canvas.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                try {
                    Map_edit.TileType type = Map_edit.TileType.valueOf(db.getString());
                    int x = (int) (e.getX() / TILE_SIZE);
                    int y = (int) (e.getY() / TILE_SIZE);
                    logic.setSelectedTile(type);
                    String error = logic.placeTile(x, y);
                    if (error != null) showError(error);
                    drawGrid();
                    success = true;
                } catch (IllegalArgumentException ex) {
                    showError("Unknown tile: " + db.getString());
                }
            }
            e.setDropCompleted(success);
            e.consume();
        });
    }
    private boolean confirmReset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Reset");
        alert.setHeaderText("Do you really want to reset the map?");
        alert.setContentText("This will erase all tiles and revert to grass. Continue?");

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yes, no);
        return alert.showAndWait().orElse(no) == yes;
    }

    private ScrollPane createTileSelector() {
        VBox content = new VBox(20);
        content.setAlignment(Pos.TOP_CENTER);
        content.setStyle("-fx-padding: 10;");
        content.getChildren().add(makeHeader("Grass"));

        content.getChildren().add(createTileButton(Map_edit.TileType.GRASS, "Delete tile (reset to grass)"));

        content.getChildren().add(makeHeader("Terrain"));
        content.getChildren().addAll(
                createTileButton(Map_edit.TileType.PATH_HORIZONTAL, "Horizontal path"),
                createTileButton(Map_edit.TileType.PATH_VERTICAL, "Vertical path"),
                createTileButton(Map_edit.TileType.PATH_TOP_LEFT, "Curve: Top-Left"),
                createTileButton(Map_edit.TileType.PATH_TOP_RIGHT, "Curve: Top-Right"),
                createTileButton(Map_edit.TileType.PATH_BOTTOM_LEFT, "Curve: Bottom-Left"),
                createTileButton(Map_edit.TileType.PATH_BOTTOM_RIGHT, "Curve: Bottom-Right")
        );

        content.getChildren().add(makeHeader("Structures"));
        content.getChildren().addAll(
                createTileButton(Map_edit.TileType.TOWER_EMPTY, "Tower build spot"),
                createTileButton(Map_edit.TileType.ARCHER_TOWER, "Pre-placed Archer Tower"),
                createTileButton(Map_edit.TileType.MAGE_TOWER, "Pre-placed Mage Tower"),
                createTileButton(Map_edit.TileType.ARTILLERY_TOWER, "Pre-placed Artillery Tower")
        );

        content.getChildren().add(makeHeader("Decoration"));
        content.getChildren().addAll(
                createTileButton(Map_edit.TileType.DECORATIVE, "Tree or decor item")
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(200);
        scrollPane.setStyle("-fx-background: transparent;");

        return scrollPane;
    }

    private Label makeHeader(String text) {
        Label header = new Label(text);
        header.setStyle("-fx-font-size: 14px; -fx-text-fill: #3a3a3a; -fx-font-weight: bold;");
        return header;
    }

    private Button createTileButton(Map_edit.TileType type, String tooltipText) {
        Image img = tileImages.get(type);
        if (img == null) return new Button("Missing");

        ImageView iv = new ImageView(img);
        iv.setFitWidth(32);
        iv.setFitHeight(32);

        Label label = new Label(getFriendlyName(type));
        label.setStyle("-fx-font-size: 11px;");

        VBox tileBox = new VBox(5, iv, label);
        tileBox.setAlignment(Pos.CENTER);

        Button btn = new Button();
        btn.setGraphic(tileBox);
        btn.setPrefWidth(160);
        btn.setTooltip(new Tooltip(tooltipText));
        btn.setStyle("""
        -fx-background-color: #ffffff;
        -fx-border-color: #aaaaaa;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-effect: dropshadow(gaussian, #bbbbbb, 2, 0, 0, 1);
        """);

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #f7f7f7; -fx-border-color: #888888;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #aaaaaa;"));

        btn.setOnDragDetected(e -> {
            Dragboard db = btn.startDragAndDrop(TransferMode.COPY);
            ClipboardContent content = new ClipboardContent();
            content.putString(type.name());
            db.setContent(content);
            Image dragImage = tileImages.get(type);
            if (dragImage != null) {
                db.setDragView(dragImage, dragImage.getWidth() / 2, dragImage.getHeight() / 2);
            }
            e.consume();
        });

        btn.setOnAction(e -> logic.setSelectedTile(type));

        return btn;
    }

    private String getFriendlyName(Map_edit.TileType type) {
        return switch (type) {
            case PATH_HORIZONTAL -> "Horizontal Path";
            case PATH_VERTICAL -> "Vertical Path";
            case PATH_TOP_LEFT -> "Top-Left Curve";
            case PATH_TOP_RIGHT -> "Top-Right Curve";
            case PATH_BOTTOM_LEFT -> "Bottom-Left Curve";
            case PATH_BOTTOM_RIGHT -> "Bottom-Right Curve";
            case GRASS -> "Grass";
            case TOWER_EMPTY -> "Tower Slot";
            case ARCHER_TOWER -> "Archer Tower";
            case MAGE_TOWER -> "Mage Tower";
            case ARTILLERY_TOWER -> "Artillery Tower";
            case DECORATIVE -> "Tree";
        };
    }

    private void handleTilePlacement(MouseEvent e) {
        int x = (int) (e.getX() / TILE_SIZE);
        int y = (int) (e.getY() / TILE_SIZE);

        String error = logic.placeTile(x, y);
        if (error != null) {
            showError(error);
        } else {
            drawGrid();
        }
    }

    private void drawGrid() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                Image grass = tileImages.get(Map_edit.TileType.GRASS);
                if (grass != null) {
                    gc.drawImage(grass, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }

                Map_edit.TileType current = logic.getTile(x, y);
                if (current == Map_edit.TileType.GRASS) continue;

                Image img = tileImages.get(current);
                if (img != null) {
                    gc.drawImage(img, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }

                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1.2);
                gc.strokeRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}




 

