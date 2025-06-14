package Gold;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import game.Player;


public class Gold_Drop {

    private Image[] frames = new Image[7];
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 150_000_000;
    private ImageView goldimage;
    private long spawnTime = System.nanoTime();;
    private boolean gold_collected= false;

    public Gold_Drop(int amount, double x, double y, Pane gamePane, Player player) {


        for (int i = 0; i < 7; i++) {
            frames[i] = new Image("file:Tower_Game/ItemDrop_UI/goldDrop" + i + ".png");
        }



        goldimage = new ImageView(frames[0]);
        goldimage.setX(x);
        goldimage.setY(y);
        goldimage.setFitWidth(64);
        goldimage.setFitHeight(64);


        goldimage.setOnMouseClicked((MouseEvent e) -> {

            if (!gold_collected) {

                gold_collected = true;
                player.add_gold(amount);
                gamePane.getChildren().remove(goldimage);

            }
        });
        gamePane.getChildren().add(goldimage);

    }

    public void update_goldAnimation(long now, Pane gamePane) {

        if (lastFrameTime == 0) {
            lastFrameTime = now;
            return;
        }

        if (now - lastFrameTime > FRAME_DURATION) {
            currentFrame = (currentFrame + 1) % frames.length;
            goldimage.setImage(frames[currentFrame]);
            lastFrameTime = now;
        }

        // Count 10 seconds from spawn
        if ((now - spawnTime) >= 10_000_000_000L) {
            Platform.runLater(() -> gamePane.getChildren().remove(goldimage));

        }
    }


}