package enemy;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;
import javafx.scene.image.Image;

public class Barrel_Enemy extends Enemy {

    private Image[] frames = new Image[7];
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 150_000_000;


    public Barrel_Enemy(List<Point> path, double speed) {

        super(path, 120, speed, 5);

        for (int i = 0; i < 7; i++) {
            frames[i] = new Image("file:Tower_Game/Enemies/barrel" + i + ".png");
        }
    }

    public void updateAnimation(long now) {

        if (lastFrameTime == 0) {
            lastFrameTime = now;
            return;
        }

        if (now - lastFrameTime > FRAME_DURATION) {

            currentFrame = (currentFrame + 1) % frames.length;
            lastFrameTime = now;
        }
    }

    public Image getCurrentImage() {

        return frames[currentFrame];
    }
}
