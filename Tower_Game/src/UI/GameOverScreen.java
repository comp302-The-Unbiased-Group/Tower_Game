package UI;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Platform;

public class GameOverScreen extends StackPane {

    public GameOverScreen(Stage primaryStage) {
        
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Background image
        Image backgroundImage = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(screenWidth, screenHeight, false, false, false, false)
        );
        setBackground(new Background(bgImage));

        // GAME OVER Text
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFill(Color.RED);
        gameOverText.setFont(Font.font("Arial", 80));

        // Buttons
        Button mainMenuButton = new Button("MAIN MENU");
        Button quitButton = new Button("QUIT");

        mainMenuButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(primaryStage);
            Scene mainMenuScene = mainMenu.newScene();  
            primaryStage.setScene(mainMenuScene);
            primaryStage.setFullScreen(true); // 
        });

        quitButton.setOnAction(e -> Platform.exit());

        VBox layout = new VBox(20, gameOverText, mainMenuButton, quitButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        getChildren().add(layout);
    }

    public Scene newScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new Scene(this, screenBounds.getWidth(), screenBounds.getHeight());
    }
}
