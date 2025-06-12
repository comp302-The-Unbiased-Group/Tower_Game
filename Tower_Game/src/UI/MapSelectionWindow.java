
package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MapSelectionWindow extends VBox {

    public MapSelectionWindow(Stage stage, Consumer<File> callback) {
        setSpacing(15);
        setPadding(new Insets(30));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #3498db);");

        Label title = new Label("Select a Saved Map");
        title.setStyle("-fx-font-size: 26px; -fx-text-fill: white;");

        File mapDir = new File("saved_maps");
        File[] maps = mapDir.listFiles((dir, name) -> name.endsWith(".txt"));
        if (maps == null || maps.length == 0) {
            Label noMaps = new Label("No saved maps found.");
            noMaps.setStyle("-fx-text-fill: white;");
            getChildren().addAll(title, noMaps);
            return;
        }

        Arrays.sort(maps, Comparator.comparing(File::getName));

        TextField filterField = new TextField();
        filterField.setPromptText("Filter maps by name...");

        ListView<File> mapListView = new ListView<>();
        mapListView.setPrefHeight(300);
        mapListView.getItems().addAll(maps);
        mapListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                setText(empty || file == null ? null : file.getName());
            }
        });

        filterField.textProperty().addListener((obs, oldText, newText) -> {
            mapListView.getItems().setAll(Arrays.stream(maps)
                    .filter(f -> f.getName().toLowerCase().contains(newText.toLowerCase()))
                    .collect(Collectors.toList()));
        });

        Button playButton = new Button("Start Game");
        playButton.setPrefWidth(200);
        playButton.setStyle("-fx-font-size: 16px;");
        playButton.setOnAction(e -> {
            File selected = mapListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                callback.accept(selected);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Map Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select a map from the list.");
                alert.showAndWait();
            }
        });

        Button backButton = new Button("Back to Menu");
        backButton.setPrefWidth(200);
        backButton.setStyle("-fx-font-size: 14px;");
        backButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(stage);
            Scene menuScene = new Scene(mainMenu, stage.getWidth(), stage.getHeight());
            stage.setScene(menuScene);
        });

        HBox buttonBox = new HBox(20, playButton, backButton);
        buttonBox.setAlignment(Pos.CENTER);

        getChildren().addAll(title, filterField, mapListView, buttonBox);
    }
}
