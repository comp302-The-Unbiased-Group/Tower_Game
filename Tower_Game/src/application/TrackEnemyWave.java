
package application;

import java.awt.Point;
import java.util.List;

public class TrackEnemyWave {
    private int current_wave = 0;
    private long lastWaveTime = 0;
    private boolean waveOn = false;
    private static final long WAVE_DELAY_MS = 4000; 
    private List<Point> path;

    public void setupWaves(List<Point> path) {
        this.path = path;
        current_wave = 0;
        lastWaveTime = System.currentTimeMillis();
        waveOn = false;
    }

    public void spawnWave(List<Enemy> enemies) {
    	waveOn = true;
        for (int i = 0; i < 5 + current_wave; i++) {
            if (i % 2 == 0) {
                enemies.add(new enemy_Goblin(path));
            } else {
                enemies.add(new enemy_Knight(path));
            }
        }
        current_wave++;
        lastWaveTime = System.currentTimeMillis();
    }
    
    public void update(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();
        
        if (!waveOn && (currentTime - lastWaveTime >= WAVE_DELAY_MS)) {
            spawnWave(enemies);
        }
        
        if (waveOn && enemies.isEmpty()) {
        	waveOn = false;
            lastWaveTime = currentTime; 
        }
    }
}

