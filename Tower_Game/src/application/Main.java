package application;
	
import javafx.scene.control.*;
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
	Button setting;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("Tower Defense");
			
			startNewGame = new Button("Start New Game");
            editMap = new Button("Edit Map");
            setting = new Button("Settings");
			
			startNewGame.setMinWidth(150);
            editMap.setMinWidth(150);
            setting.setMinWidth(150);
            
            VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
            
            
            layout.setPadding(new Insets(20));
            
            
            layout.getChildren().addAll(startNewGame, editMap, setting);
			
			Scene scene = new Scene(layout, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		

		
		
	}

}
