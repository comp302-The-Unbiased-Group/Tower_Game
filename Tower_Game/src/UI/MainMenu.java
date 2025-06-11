package UI;
import MapEdit.Map_edit;
import application.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;

public class MainMenu extends StackPane {

    Button startNewGame;
    Button loadSavedMapGame;
    Button editMap;
    Button options;
    Button quit;

    public MainMenu(Stage primaryStage) {
        startNewGame = new Button("NEW GAME");
        loadSavedMapGame = new Button("LOAD SAVED MAP");
        editMap = new Button("MAP EDITOR");
        options = new Button("OPTIONS");
        quit = new Button("QUIT");

        startNewGame.setId("startNewGame");
        loadSavedMapGame.setId("loadSavedMapGame");
        editMap.setId("editMap");
        options.setId("setting");
        quit.setId("quit");

       
        startNewGame.setOnAction(e -> {
            

            GameScreen game = new GameScreen(primaryStage,null);
            Scene gameScene = new Scene(game);
            primaryStage.setScene(gameScene);
            primaryStage.setFullScreen(true);
        });

        loadSavedMapGame.setOnAction(e -> {
            File savedMap = new File("custom_map.txt");  // relative path
            if (savedMap.exists()) {
                GameScreen game = new GameScreen(primaryStage, savedMap);
                Scene gameScene = new Scene(game);
                primaryStage.setScene(gameScene);
                primaryStage.setFullScreen(true);
            } else {
                System.err.println("Map file not found: " + savedMap.getAbsolutePath());
            }
        });
        
        quit.setOnAction(new EventHandler <ActionEvent>() {
        	public void handle(ActionEvent arg0) {
        		Platform.exit();
        	}
        });

         options.setOnMouseClicked(event -> {
            OptionScreen optionScreen = new OptionScreen();
            Scene optionScene = optionScreen.newScene(primaryStage);
            primaryStage.setScene(optionScene);
        });
        
        
        
        

        VBox layout = new VBox(15);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(startNewGame, loadSavedMapGame, editMap, options, quit);
        layout.getStylesheets().add(getClass().getResource("/UI/application.css").toExternalForm());

        Image mainScreenImage = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage mImage = new BackgroundImage(
            mainScreenImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(1.0, 1.0, true, true, false, false )
        );

        StackPane root = new StackPane();
        root.setBackground(new Background(mImage));
        root.getChildren().add(layout);

        this.getChildren().add(root);


        editMap.setOnAction(e -> {
            MapEditorSetting mapEditor = new MapEditorSetting(primaryStage);
            Scene mapScene = new Scene(mapEditor);
            primaryStage.setScene(mapScene);
            primaryStage.setFullScreen(true);
        });
    }
    public Scene newScene() {
		
		return new Scene(this, Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
	}

}

