package UI;

import javafx.geometry.Pos;
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

public class EndGameStats extends StackPane {
    private Stage stage;
    private int enemiesKilled;

    public EndGameStats(Stage stage, int enemiesKilled) {
        this.stage = stage;
        this.enemiesKilled = enemiesKilled;

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Text setup
        Text victoryText = new Text("VICTORY");
        victoryText.setFont(Font.font("Arial", 64));
        victoryText.setFill(Color.GREEN);

        Text statsText = new Text("Enemies killed: " + enemiesKilled);
        statsText.setFont(Font.font("Arial", 24));
        statsText.setFill(Color.WHITE);

        // Button setup
        Button backToMenu = new Button("Back to Main Menu");
        backToMenu.setOnMouseClicked(e -> {
            MainMenu mainMenu = new MainMenu(stage);
            Scene menuScene = mainMenu.newScene();
            stage.setScene(menuScene);
            stage.setFullScreen(true); 
        });

        VBox layout = new VBox(30, victoryText, statsText, backToMenu);
        layout.setAlignment(Pos.CENTER);

        // Background
        Image bg = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        BackgroundImage bgImage = new BackgroundImage(
                bg,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(screenWidth, screenHeight, false, false, false, false)
        );

        this.setBackground(new Background(bgImage));
        this.getChildren().add(layout);
    }

    public Scene newScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new Scene(this, screenBounds.getWidth(), screenBounds.getHeight());
    }
}

