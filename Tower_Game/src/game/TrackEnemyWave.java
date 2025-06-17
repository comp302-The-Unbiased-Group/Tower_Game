
package game;
import enemy.*;
import utils.OptionsHandler;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class TrackEnemyWave {
	

	
    private boolean firstWave = true;
    protected static int current_wave = 0;
    private long lastWaveTime = 0;
    private boolean waveOn = false;
    private static final long FIRST_DELAY_MS = 4000;
    private long waveDelayMs = 4000;

    private static final long SPAWN_DELAY_MS = 500;
    private List<Point> path;
    
    private long lastSpawnTime = 0;
    
    private List<waveSetting> waves;
    
    private int goblins_should_Spawn = 0;
    private int knights_should_Spawn = 0;
    private int tnts_should_Spawn = 0;
    private int healer_should_Spawn = 0;
    private int barrel_should_Spawn = 0;

    private int goblinsSpawned = 0;
    private int knightsSpawned = 0;
    private int tntsSpawned = 0;
    private int healerSpawned = 0;
    private int barrelSpawned = 0;


    public void setupWaves(List<Point> path) {
        this.path = path;
        firstWave = true;
        current_wave = 0;
        lastWaveTime = System.currentTimeMillis();
        waveOn = false;
        waveDelayMs = OptionsHandler.getInstance().getWaveDelayMs();


        goblins_should_Spawn = 0;
        knights_should_Spawn = 0;
        tnts_should_Spawn = 0;
        healer_should_Spawn = 0;
        barrel_should_Spawn = 0;

        goblinsSpawned = 0;
        knightsSpawned = 0;
        tntsSpawned = 0;
        healerSpawned = 0;
        barrelSpawned = 0;

        
        int waveCount = OptionsHandler.getInstance().getWaveCount();
        waves = new java.util.ArrayList<>();
        for (int i = 0; i < waveCount; i++) {
            
            waves.add(
                new waveSetting(
                    3 + i * 2,    
                    i,          
                    i % 2,       
                    i / 2,        
                    (i > 5 ? 1 : 0) 
                )
            );
        }
    }

    public void startWave(List<Enemy> enemies) {
        firstWave = false;
        waveOn = true;
        
        if (current_wave < waves.size()) {
            waveSetting config = waves.get(current_wave);
            goblins_should_Spawn = config.goblins;
            knights_should_Spawn = config.knights;
            tnts_should_Spawn = config.tnts;
            healer_should_Spawn = config.healers;
            barrel_should_Spawn = config.barrels;
        } else {
            goblins_should_Spawn = 0;
            knights_should_Spawn = 0;
            tnts_should_Spawn = 0;
            healer_should_Spawn = 0;
            barrel_should_Spawn = 0;

        }
        goblinsSpawned = 0;
        knightsSpawned = 0;
        tntsSpawned = 0;
        healerSpawned =0;
        barrelSpawned = 0;

        lastSpawnTime = System.currentTimeMillis();
        current_wave++;
        lastWaveTime = System.currentTimeMillis();
    }


    private void spawnOneEnemy(List<Enemy> enemies) {
    	
    	if (tntsSpawned < tnts_should_Spawn) {
    		
            enemies.add(new enemy_TNT(path,OptionsHandler.getInstance().getTntSpeed() * OptionsHandler.getInstance().getspeedmultiplier()));
            tntsSpawned++;}
    	        
            else if (goblinsSpawned < goblins_should_Spawn) {
    	       enemies.add(new enemy_Goblin(path, OptionsHandler.getInstance().getGoblinSpeed() * OptionsHandler.getInstance().getspeedmultiplier()));
    	       goblinsSpawned++;
    	} else if (knightsSpawned < knights_should_Spawn) {
    	       enemies.add(new enemy_Knight(path, OptionsHandler.getInstance().getKnightSpeed() * OptionsHandler.getInstance().getspeedmultiplier()));
    	       knightsSpawned++;
    	 }
        else if (healerSpawned < healer_should_Spawn) {
            enemies.add(new Enemy_Healer(path, OptionsHandler.getInstance().getHealerSpeed() * OptionsHandler.getInstance().getspeedmultiplier()));
            healerSpawned++;
        }
        else if (barrelSpawned < barrel_should_Spawn) {
            enemies.add(new Barrel_Enemy(path, OptionsHandler.getInstance().getBarrelSpeed() * OptionsHandler.getInstance().getspeedmultiplier()));
            barrelSpawned++;
        }
    	  lastSpawnTime = System.currentTimeMillis();
    	    
    }

    public void update(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();

        if (firstWave && (currentTime - lastWaveTime >= FIRST_DELAY_MS)) {
            startWave(enemies);
        } else if (!waveOn && !firstWave && (currentTime - lastWaveTime >= waveDelayMs)) {
            startWave(enemies);
        }


        if (waveOn && (goblinsSpawned < goblins_should_Spawn || knightsSpawned < knights_should_Spawn || healerSpawned < healer_should_Spawn || barrelSpawned < barrel_should_Spawn)) {
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

