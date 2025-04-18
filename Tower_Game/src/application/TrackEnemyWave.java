package application;


import java.awt.Point;
import java.util.List;



public class TrackEnemyWave {
	private int current_wave = 0;

    public void setupWaves() {
        current_wave = 0;
    }

    public void spawnWave(List<Enemy> enemies, List<Point> path) {
    	
        for (int i = 0; i < 5 + current_wave; i++) {
            if (i % 2 == 0) {
            	enemies.add(new enemy_Goblin(path));
            }
            else {
            	enemies.add(new enemy_Knight(path));
            }
        }
        current_wave++;
    }
    
    public void update(List<Enemy> enemies) {
        // will continue on next phase
    }

}
