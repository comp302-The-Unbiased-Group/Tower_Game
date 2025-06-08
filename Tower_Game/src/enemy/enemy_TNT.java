package enemy;

import java.awt.Point;
import java.util.List;
import javafx.scene.image.Image;

public class enemy_TNT extends Enemy {

    private Image[] frames = new Image[6];
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 250_000_000; 

    public enemy_TNT(List<Point> path) {
    	
        super(path, 235, 1.6, 50); //drop 50 gold 

        for (int i = 0; i < 6; i++) {
        	
            frames[i] = new Image("file:Tower_Game/Enemies/tnt_" + i + ".png");
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
