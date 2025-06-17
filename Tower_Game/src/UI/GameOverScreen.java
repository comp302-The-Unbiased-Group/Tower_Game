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
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.scene.shape.Rectangle;
public class GameOverScreen extends StackPane {

    public GameOverScreen(Stage primaryStage) {
        
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Background image
        Rectangle dimOverlay = new Rectangle(screenWidth, screenHeight);
        dimOverlay.setFill(Color.color(0, 0, 0, 0));

        Image background = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        ImageView bgView = new ImageView(background);
        bgView.setFitWidth(screenWidth);
        bgView.setFitHeight(screenHeight);
        bgView.setOpacity(1);
        
        FadeTransition overlayFade = new FadeTransition(Duration.seconds(2), dimOverlay);
        overlayFade.setFromValue(0);
        overlayFade.setToValue(0.7);
        overlayFade.setDelay(Duration.seconds(0.5));
        overlayFade.play();
        
        dimOverlay.setFill(Color.BLACK);
        dimOverlay.setOpacity(0);
        


        // GAME OVER Text
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFill(Color.RED);
        gameOverText.setFont(Font.font("Courier New", FontPosture.ITALIC ,128));
        gameOverText.setOpacity(0);
        
        FadeTransition fadeInGameOver = new FadeTransition(Duration.seconds(2), gameOverText);
        fadeInGameOver.setFromValue(0.0);
        fadeInGameOver.setToValue(1.0);
        fadeInGameOver.setDelay(Duration.seconds(4));
        fadeInGameOver.play();

        // Buttons

        Image arrowImage = new Image("file:Tower_Game/UI_Special_effects/loss_backTo_main_button.png");
        ImageView arrowImageView = new ImageView(arrowImage);


        arrowImageView.setFitWidth(200);
        arrowImageView.setFitHeight(100);
        arrowImageView.setPreserveRatio(true);


        Button mainMenuButton = new Button("", arrowImageView);
        mainMenuButton.setOpacity(0);
        mainMenuButton.setStyle("-fx-background-color: transparent;");
        
        mainMenuButton.setOnMouseEntered(e -> {
            mainMenuButton.setStyle("-fx-background-color: transparent; -fx-effect: dropshadow(gaussian, red, 10, 0.5, 0, 0);");
        });

        mainMenuButton.setOnMouseExited(e -> {
            mainMenuButton.setStyle("-fx-background-color: transparent; -fx-effect: none;");
        });

        
        FadeTransition fadeInButton = new FadeTransition(Duration.seconds(1.5), mainMenuButton);
        fadeInButton.setFromValue(0.0);
        fadeInButton.setToValue(1.0);
        fadeInButton.setDelay(Duration.seconds(5.5));
        fadeInButton.play();


        mainMenuButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(primaryStage);
            Scene mainMenuScene = mainMenu.newScene();  
            primaryStage.setScene(mainMenuScene);
            primaryStage.setFullScreen(true); // 
        });

        VBox layout = new VBox(20, gameOverText, mainMenuButton);
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
        drip.setDelay(Duration.seconds(0.5));
        drip.play();

        this.getChildren().addAll(bgView, dimOverlay, bloodOverlay, layout);

    }

    public Scene newScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new Scene(this, screenBounds.getWidth(), screenBounds.getHeight());
    }
}
