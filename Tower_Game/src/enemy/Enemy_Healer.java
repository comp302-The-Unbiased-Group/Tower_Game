package enemy;

import java.awt.Point;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy_Healer extends Enemy {

    private Image[] frames = new Image[6];
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 250_000_000;
    private long healedAt = 0;
    private static final long heal_icon_duration = 5000;
    private boolean hashealed = false;

    private boolean healing_on;

    public Enemy_Healer(List<Point> path) {

        super(path, 75, 3.5, 50); //add 100 gold  to player

        for (int i = 0; i < 6; i++) {

            frames[i] = new Image("file:Tower_Game/Enemies/healer" + (i+1 )+ ".png");
        }
        this.healing_on = false;
        this.hashealed = false;
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
    public void heal(List<Enemy> allEnemies, double tileWidth) {
        boolean enemy_is_close = false;

        for (Enemy e : allEnemies) {
            if (e != this && e.is_alive()) {

                double dist = position.distance(e.getPosition());

                if (dist < (tileWidth*2.5) && e.getHp() < e.getMaxHp() / 2) {
                    enemy_is_close = true;
                    e.healed(e.getMaxHp()/3);
                    this.hashealed = true;
                    healedAt = System.currentTimeMillis();
                    break;

                }
            }
        }


        this.healing_on = enemy_is_close;
    }

    public Image getCurrentImage() {
        return frames[currentFrame];
    }

    public boolean isHealing_on() {
        if (hashealed) {
            if (this.healing_on || (System.currentTimeMillis() - healedAt > heal_icon_duration)) {
                return true;
            }
        }
        return false;
    }
}

