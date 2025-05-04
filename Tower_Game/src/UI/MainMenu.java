package UI;
import application.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainMenu extends StackPane {

    Button startNewGame;
    Button editMap;
    Button options;
    Button quit;

    public MainMenu(Stage primaryStage) {
        startNewGame = new Button("NEW GAME");
        editMap = new Button("MAP EDITOR");
        options = new Button("OPTIONS");
        quit = new Button("QUIT");

        startNewGame.setId("startNewGame");
        editMap.setId("editMap");
        options.setId("setting");
        quit.setId("quit");

       
        startNewGame.setOnAction(e -> {
            

            GameScreen game = new GameScreen(primaryStage); 
            Scene gameScene = new Scene(game, 1280, 720);
            primaryStage.setScene(gameScene);
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
    public Scene newScene() {
		
		return new Scene(this, 580, 580);
	}
}


