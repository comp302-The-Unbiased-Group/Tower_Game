package UI;

import javafx.animation.TranslateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.util.Duration;

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

        Image arrowImage = new Image("file:Tower_Game/UI_Special_effects/loss_backTo_main_button.png");
        ImageView arrowImageView = new ImageView(arrowImage);


        arrowImageView.setFitWidth(200);
        arrowImageView.setFitHeight(100);
        arrowImageView.setPreserveRatio(true);


        Button mainMenuButton = new Button("", arrowImageView);
        mainMenuButton.setStyle("-fx-background-color: transparent;");


        mainMenuButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(primaryStage);
            Scene mainMenuScene = mainMenu.newScene();  
            primaryStage.setScene(mainMenuScene);
            primaryStage.setFullScreen(true); // 
        });

        Button quitButton = new Button("QUIT");
        quitButton.setOnAction(e -> Platform.exit());

        VBox layout = new VBox(20, gameOverText, mainMenuButton, quitButton);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        // Blood flow overlay
        Image bloodFlow = new Image("file:Tower_Game/UI_Special_effects/loss_bloodui.png");
        ImageView bloodOverlay = new ImageView(bloodFlow);


        bloodOverlay.setFitWidth(screenWidth);
        bloodOverlay.setFitHeight(screenHeight * 1.5);




        bloodOverlay.setTranslateY(-screenHeight * 0.5);


        TranslateTransition drip = new TranslateTransition(Duration.seconds(6), bloodOverlay);
        drip.setFromY(-screenHeight * 0.5);
        drip.setToY(0);
        drip.setCycleCount(1);
        drip.play();


        this.getChildren().add(bloodOverlay);

        getChildren().add(layout);
    }

    public Scene newScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new Scene(this, screenBounds.getWidth(), screenBounds.getHeight());
    }
}
