package application;
	
import UI.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class Main extends Application {
    @Override
   public void start(Stage primaryStage) {
    	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // changed to fullscreen from static resolution
        MainMenu menu = new MainMenu(primaryStage);
        Scene scene = new Scene(menu, screenWidth, screenHeight);

        primaryStage.setTitle("KU Tower Defense");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();
       
   }

   public static void main(String[] args) {
       launch(args);
   }
}   

