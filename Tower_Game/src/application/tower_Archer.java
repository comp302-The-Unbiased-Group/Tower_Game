package application;

import java.util.List;



public class tower_Archer extends Tower {
    public tower_Archer(int x, int y) {
        super(x, y, 100, 50, 2);
    }

    @Override
    public void attack(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (range_tower(enemy)) {
                enemy.take_damage(10);
                break;
            }
        }
    }
}