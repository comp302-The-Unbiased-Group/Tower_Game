
package application;

import java.awt.Point;
import java.util.List;

public class TrackEnemyWave {
    private int current_wave = 0;
    private long lastWaveTime = 0;
    private boolean waveOn = false;
    private static final long WAVE_DELAY_MS = 4000; 
    private static final long SPAWN_DELAY_MS = 500;
    private List<Point> path;
    private int enemiesToSpawn = 0;
    private int spawnedEnemies = 0;
    private long lastSpawnTime = 0;

    public void setupWaves(List<Point> path) {
        this.path = path;
        current_wave = 0;
        lastWaveTime = System.currentTimeMillis();
        waveOn = false;
        enemiesToSpawn = 0;
        spawnedEnemies = 0;
    }

    public void startWave(List<Enemy> enemies) {
    	waveOn = true;
    	enemiesToSpawn = 5 + current_wave;
        spawnedEnemies = 0;
        lastSpawnTime = System.currentTimeMillis();
        current_wave++;
        lastWaveTime = System.currentTimeMillis();
    }
    
    private void spawnOneEnemy(List<Enemy> enemies) {
    	Enemy enemy;
        if (spawnedEnemies % 2 == 0) {
            enemy = (new enemy_Goblin(path));
        } else {
            enemy = (new enemy_Knight(path));
            }
        
        enemies.add(enemy);
        spawnedEnemies++;
        lastSpawnTime = System.currentTimeMillis();
    }
    
    public void update(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();
        
        if (!waveOn && (currentTime - lastWaveTime >= WAVE_DELAY_MS)) {
            startWave(enemies);
        }
        
        if (waveOn && spawnedEnemies < enemiesToSpawn) {
            if (currentTime - lastSpawnTime >= SPAWN_DELAY_MS) {
            	spawnOneEnemy(enemies);
            }
        }
        
        if (waveOn && spawnedEnemies >= enemiesToSpawn && enemies.isEmpty()) {
            waveOn = false;
            lastWaveTime = currentTime;
        }
    }
}

