package UI;

import javafx.geometry.Pos;
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


public class EndGameStats extends StackPane {
    private Stage stage;
    private int enemiesKilled;

    public EndGameStats(Stage stage, int enemiesKilled) {
        this.stage = stage;
        this.enemiesKilled = enemiesKilled;

        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        Rectangle dimOverlay = new Rectangle(screenWidth, screenHeight);
        dimOverlay.setFill(Color.color(0, 0, 0, 0));

        Image background = new Image("file:Tower_Game/KuTowerDefence1.jpg");
        ImageView bgView = new ImageView(background);
        bgView.setFitWidth(screenWidth);
        bgView.setFitHeight(screenHeight);
        bgView.setOpacity(1);
        
        dimOverlay.setFill(Color.BLACK);
        dimOverlay.setOpacity(0);
        
        FadeTransition overlayFade = new FadeTransition(Duration.seconds(2), dimOverlay);
        overlayFade.setFromValue(0);
        overlayFade.setToValue(0.7);
        overlayFade.setDelay(Duration.seconds(1));
        overlayFade.play();
        
        Text victoryText = new Text("VICTORY!");
        victoryText.setFont(Font.font("Courier New", FontPosture.ITALIC ,128));
        victoryText.setFill(Color.GOLD);
        victoryText.setOpacity(0);

        Text statsText = new Text("Enemies killed: " + enemiesKilled);
        statsText.setFont(Font.font("Courier New", 24));
        statsText.setFill(Color.WHITE);
        statsText.setOpacity(0);
        
        FadeTransition fadeInVictory = new FadeTransition(Duration.seconds(2), victoryText);
        fadeInVictory.setFromValue(0.0);
        fadeInVictory.setToValue(1.0);
        fadeInVictory.setDelay(Duration.seconds(2));
        fadeInVictory.play();

        FadeTransition fadeInStats = new FadeTransition(Duration.seconds(1.5), statsText);
        fadeInStats.setFromValue(0.0);
        fadeInStats.setToValue(1.0);
        fadeInStats.setDelay(Duration.seconds(4));
        fadeInStats.play();

        Button backToMenu = new Button("Main Menu");
        String font = "'Courier New'";
        backToMenu.setFont(Font.font("Courier New", 30));
        backToMenu.setTextFill(Color.WHITE);
        backToMenu.setBackground(Background.EMPTY);
        backToMenu.setBorder(Border.EMPTY);
        backToMenu.setOpacity(0);
                
        backToMenu.setOnMouseEntered(e -> {
            backToMenu.setStyle("-fx-font-family: " + font + "; -fx-effect: dropshadow(gaussian, gold, 10, 0.5, 0, 0);");
        });
        
        backToMenu.setOnMouseExited(e -> {
            backToMenu.setStyle("-fx-font-family: " + font + "; -fx-text-fill: white; -fx-font-size: 30px; -fx-background-color: transparent;");
        });
        
        FadeTransition fadeInButton = new FadeTransition(Duration.seconds(1.5), backToMenu);
        fadeInButton.setFromValue(0.0);
        fadeInButton.setToValue(1.0);
        fadeInButton.setDelay(Duration.seconds(5.5));
        fadeInButton.play();
        
        backToMenu.setOnMouseClicked(e -> {
            MainMenu mainMenu = new MainMenu(stage);
            Scene menuScene = mainMenu.newScene();
            stage.setScene(menuScene);
            stage.setFullScreen(true); 
        });

        VBox layout = new VBox(30, victoryText, statsText, backToMenu);
        layout.setAlignment(Pos.CENTER);
        
        this.getChildren().addAll(bgView, dimOverlay, layout);
    }

    public Scene newScene() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new Scene(this, screenBounds.getWidth(), screenBounds.getHeight());
    }
}

