package UI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;

import application.GameScreen;
import application.MapEditorSetting;
import application.OptionScreen;

public class MainMenu extends VBox {

    public MainMenu(Stage primaryStage) {
        Button startNewGame = new Button("NEW GAME");
        Button editMap = new Button("MAP EDITOR");
        Button options = new Button("OPTIONS");
        Button quit = new Button("QUIT");

        startNewGame.setId("startNewGame");
        editMap.setId("editMap");
        options.setId("setting");
        quit.setId("quit");

        startNewGame.setOnAction(e -> {
            GameScreen game = new GameScreen(primaryStage);
            primaryStage.setScene(new Scene(game, 1280, 720));
        });

        editMap.setOnAction(e -> {
            MapEditorSetting editor = new MapEditorSetting(primaryStage);
            primaryStage.setScene(new Scene(editor, 1280, 720));
        });

        options.setOnAction(e -> {
            OptionScreen opt = new OptionScreen(primaryStage);
            primaryStage.setScene(new Scene(opt, 1280, 720));
        });

        quit.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(startNewGame, editMap, options, quit);

        Image mainScreenImage = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage mImage = new BackgroundImage(
                mainScreenImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        StackPane root = new StackPane();
        root.setBackground(new Background(mImage));
        root.getChildren().add(layout);

        this.getChildren().add(root);
    }
}
