package enemy;

import java.awt.Point;
import java.util.List;
import javafx.scene.image.Image;
import utils.OptionsHandler;

public class enemy_Knight extends Enemy {

    private Image[] frames = new Image[6];
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private static final long FRAME_DURATION = 150_000_000;


    // speed of each knight instance
    protected double basespeed = 2.5;
    private double synergyboost;
    private boolean synergy_on;
    public static final double synergy_multiplier = 1.25;

    public enemy_Knight(List<Point> path) {
        super(path, 60, 2.5, 10);


        for (int i = 0; i < 6; i++) {
            frames[i] = new Image("file:Tower_Game/Enemies/knight_" + i + ".png");
        }
        this.basespeed = OptionsHandler.getInstance().getbaseknight_speed_multiplier();
        this.synergyboost = (OptionsHandler.getInstance().getbasegoblin_speed_multiplier() + OptionsHandler.getInstance().getbaseknight_speed_multiplier()) / 2.0;
        this.synergy_on = false;
    }

    // move function specific for knight enemies
    // find nearby enemies and check if they are goblins for synergy
    // based on the result we update movement with superclass method

    public void move(double speedMultiplier, List<Enemy> allEnemies, double tileWidth) {
        boolean goblin_is_close = false;

        for (Enemy e : allEnemies) {
            if (e != this && e.is_alive()) {


                double dist = position.distance(e.getPosition());
                if (dist < tileWidth) {
                    //
                    if (e instanceof enemy_Goblin) {
                        goblin_is_close = true;
                        break;
                    }
                }
            }
        }

        if (goblin_is_close && !synergy_on) {
            this.synergy_on = true;
            base_speed = synergyboost;
        } else if (!goblin_is_close && synergy_on) {
            this.synergy_on = false;
            base_speed = basespeed;
        }


        super.move(speedMultiplier);
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
    
    public boolean hasSynergy() {
        return synergy_on;
    }
    @Override
    public double getSpeed() {
        double effective_speed = base_speed;
      
        if (hasSynergy()) // first apply synergy then slow
        	effective_speed *= synergy_multiplier;
        if (isSlowed()) 
        	effective_speed *= 0.8; // 20% slower
        return effective_speed;
    }
}