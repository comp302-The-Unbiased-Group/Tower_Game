package tower;

import enemy.Enemy;

import java.util.List;

import javafx.scene.image.Image;



public class tower_Archer extends Tower {

    private long last_hit_time = 0;  // track last time tower fire

    public tower_Archer(int x, int y) {
        super(x, y, 100, 50, 2); // ı adjust fire_rate as 2 shots per second
    }

    @Override
    public void attack(List<Enemy> enemies) {
        long now = System.nanoTime();
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) {
            return; // means not enough time has passed
        }

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && range_tower(enemy)) {
                enemy.take_damage(10);
                last_hit_time = now;  // it resets timer
                break;
            }
        }
    }
   
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_archer256.png"); 
    }
}