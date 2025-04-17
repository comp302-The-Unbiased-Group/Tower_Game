package application;
	
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	
	Button startNewGame;
	Button editMap;
	Button options;
	Button quit;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tower Defense");
			
			startNewGame = new Button("NEW GAME");
            editMap = new Button("MAP EDITOR");
            options = new Button("OPTIONS");
            quit = new Button("QUIT");
            
            
            startNewGame.setId("startNewGame");
            editMap.setId("editMap");
            options.setId("setting");
            quit.setId("quit");
            
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
            
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
