package Meat;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import game.Player;


public class Meat_Drop {

    private Image[] frames = new Image[7];
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 150_000_000;
    private ImageView meatimage;
    private long spawnTime = System.nanoTime();;
    private boolean meat_collected= false;

    public Meat_Drop(int amount, double x, double y, Pane gamePane, Player player) {


        for (int i = 0; i < 7; i++) {
            frames[i] = new Image("file:Tower_Game/ItemDrop_UI/meat_drop0" + i + ".png");
        }



        meatimage = new ImageView(frames[0]);
        meatimage.setX(x);
        meatimage.setY(y);
        meatimage.setFitWidth(64);
        meatimage.setFitHeight(64);


        meatimage.setOnMouseClicked((MouseEvent e) -> {

            if (!meat_collected) {

                meat_collected = true;
                player.add_lives(amount);
                gamePane.getChildren().remove(meatimage);

            }
        });
        gamePane.getChildren().add(meatimage);

    }

    public void update_meatAnimation(long now, Pane gamePane) {

        if (lastFrameTime == 0) {
            lastFrameTime = now;
            return;
        }

        if (now - lastFrameTime > FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % frames.length;
            meatimage.setImage(frames[currentFrame]);
            lastFrameTime = now;
        }

        // Count 10 seconds from spawn
        if ((now - spawnTime) >= 10_000_000_000L) {
            Platform.runLater(() -> gamePane.getChildren().remove(meatimage));

        }
    }


}
