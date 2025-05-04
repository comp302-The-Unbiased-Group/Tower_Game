package application;
	
import UI.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
    @Override
   public void start(Stage primaryStage) {
       MainMenu menu = new MainMenu(primaryStage);
       Scene scene = new Scene(menu, 900, 700);
       scene.getStylesheets().add(getClass().getResource("../UI/application.css").toExternalForm());
       primaryStage.setTitle("KU Tower Defense");
       primaryStage.setScene(scene);
       primaryStage.show();
       
   }

   public static void main(String[] args) {
       launch(args);
   }
}   

