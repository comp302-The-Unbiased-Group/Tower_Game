package application;

import java.util.List;



public class tower_Mage extends Tower {
    public tower_Mage(int x, int y) {
        super(x, y, 80, 60, 3);
    }

    @Override
    public void attack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (range_tower(enemy)) {
                enemy.take_damage(20);
                break;
            }
        }
    }
}