package UI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;

public class GameOverScreen extends StackPane {

    public GameOverScreen(Stage primaryStage) {
        // Background image
        Image backgroundImage = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true)
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
            Scene mainMenuScene = new Scene(mainMenu, 1280, 720);
            primaryStage.setScene(mainMenuScene);
        });

        quitButton.setOnAction(e -> Platform.exit());

        VBox layout = new VBox(20, gameOverText, mainMenuButton, quitButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        getChildren().add(layout);
    }

    public Scene newScene() {
        return new Scene(this, 1280, 720);
    }
}
