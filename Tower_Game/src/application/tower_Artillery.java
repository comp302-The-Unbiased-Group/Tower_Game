package application;

import java.util.List;




public class tower_Artillery extends Tower {
    private int aoe_radius = 50;

    public tower_Artillery(int x, int y) {
        super(x, y, 120, 70, 4);
    }
    
    @Override
    public void attack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (range_tower(enemy)) {
            	
                for (Enemy aoe_aim : enemies) {
                    double dx = aoe_aim.getPosition().x - enemy.getPosition().x;
                    double dy = aoe_aim.getPosition().y - enemy.getPosition().y;
                    double distance = Math.sqrt((dx * dx) + (dy * dy));
                    
                    if (distance <= aoe_radius) {
                        aoe_aim.take_damage(15);
                    }
                }
                break;
            }
        }
    }
}

   

