
package game;
import utils.OptionsHandler;
import enemy.Enemy;
import enemy.enemy_Goblin;
import enemy.enemy_Knight;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class TrackEnemyWave {
    private boolean firstWave = true;
    protected static int current_wave = 0;
    private long lastWaveTime = 0;
    private boolean waveOn = false;
    private static final long FIRST_DELAY_MS = 4000;
    private static final long WAVE_DELAY_MS = 4000;
    private static final long SPAWN_DELAY_MS = 500;
    private List<Point> path;
    
    private long lastSpawnTime = 0;
    
    private List<waveSetting> waves = Arrays.asList(
            new waveSetting(3, 0),    // Wave 1
            new waveSetting(5, 0),    // Wave 2
            new waveSetting(5, 1),   // Wave 3
            new waveSetting(7, 2),   // Wave 4
            new waveSetting(9, 4),   // Wave 5
            new waveSetting(8, 6),    // Wave 6
            new waveSetting(12, 6),   // Wave 7
            new waveSetting(6, 10)    // Wave 8
        );
    
    private int goblins_should_Spawn = 0;
    private int knights_should_Spawn = 0;
    private int goblinsSpawned = 0;
    private int knightsSpawned = 0;
    


    public void setupWaves(List<Point> path) {
        this.path = path;
        firstWave = true;
        current_wave = 0;
        lastWaveTime = System.currentTimeMillis();
        waveOn = false;
        
        goblins_should_Spawn = 0;
        knights_should_Spawn = 0;
        goblinsSpawned = 0;
        knightsSpawned = 0;
    }

    public void startWave(List<Enemy> enemies) {
        firstWave = false;
        waveOn = true;
        
        if (current_wave < waves.size()) {
            waveSetting config = waves.get(current_wave);
            goblins_should_Spawn = config.goblins;
            knights_should_Spawn = config.knights;
        } else {
            goblins_should_Spawn = 0;
            knights_should_Spawn = 0;
        }
        goblinsSpawned = 0;
        knightsSpawned = 0;
        lastSpawnTime = System.currentTimeMillis();
        current_wave++;
        lastWaveTime = System.currentTimeMillis();
    }


    private void spawnOneEnemy(List<Enemy> enemies) {
    	        
    	if (goblinsSpawned < goblins_should_Spawn) {
    	       enemies.add(new enemy_Goblin(path));
    	       goblinsSpawned++;
    	} else if (knightsSpawned < knights_should_Spawn) {
    	       enemies.add(new enemy_Knight(path));
    	       knightsSpawned++;
    	 }
    	  lastSpawnTime = System.currentTimeMillis();
    	    
    }

    public void update(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();

        if (firstWave && (currentTime - lastWaveTime >= FIRST_DELAY_MS)) {
            startWave(enemies);
        } else if (!waveOn && !firstWave && (currentTime - lastWaveTime >= WAVE_DELAY_MS)) {
            startWave(enemies);
        }

        if (waveOn && (goblinsSpawned < goblins_should_Spawn || knightsSpawned < knights_should_Spawn)) {
            if (currentTime - lastSpawnTime >= SPAWN_DELAY_MS) {
                spawnOneEnemy(enemies);
            }
        }
        if (waveOn && goblinsSpawned >= goblins_should_Spawn && knightsSpawned >= knights_should_Spawn && enemies.isEmpty()) {
            waveOn = false;
            lastWaveTime = currentTime;
        }
    }


    public boolean check_if_last_wave() {
        if (current_wave == OptionsHandler.getInstance().getWaveCount() && !waveOn) {
            return true;
        }
        return false;
    }

    public static int get_wavecount() {
        return current_wave;
    }
}

