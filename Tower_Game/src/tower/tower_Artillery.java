package tower;
import enemy.Enemy;
import java.util.List;

import javafx.scene.image.Image;




public class tower_Artillery extends Tower {
	
    private int aoe_radius = 50;
    private long last_hit_time = 0;

    public tower_Artillery(int x, int y) {
        super(x, y, 120, 70, 1);
    }
    
    @Override
    public void attack(List<Enemy> enemies) {
        long now = System.nanoTime();
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) {
            return; 
        }

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && range_tower(enemy)) {
                for (Enemy aoe_aim : enemies) {
                    if (!aoe_aim.is_alive()) continue;

                    double dx = aoe_aim.getPosition().x - enemy.getPosition().x;
                    double dy = aoe_aim.getPosition().y - enemy.getPosition().y;
                    double distance = Math.sqrt((dx * dx) + (dy * dy));

                    if (distance <= aoe_radius) {
                        aoe_aim.take_damage(15);
                    }
                }
                last_hit_time = now;
                break;
            }
        }
    }
    
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_bomb256.png");
    }

}

   

