package tower;

import enemy.Enemy;
import java.util.List;

import javafx.scene.image.Image;



public class tower_Mage extends Tower {
	
	private long last_hit_time = 0;
	
    public tower_Mage(int x, int y) {
        super(x, y, 80, 60, 1);
    }

    @Override
    public void attack(List<Enemy> enemies) {
        long now = System.nanoTime();
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) {
            return; 
        }

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && range_tower(enemy)) {
                enemy.take_damage(15);  
                last_hit_time = now;     
                break;
            }
        }
    }
    
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_spell256.png");
    }
}