package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Manage_game {
    private Map map;
    public Player player;
    private List<Tower> towers = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    private TrackEnemyWave wave_manager = new TrackEnemyWave();

    public void startGame(Map map, Player player) {
        this.map = map;
        this.player = player;
        wave_manager.setupWaves(map.get_path());
    }

    public void updateGame(double speedMultiplier) {
        wave_manager.update(enemies);
        
        for (Enemy enemy : enemies) {
            enemy.move(speedMultiplier);
        }
        
        for (Tower tower : towers) {
            tower.attack(enemies);
        }
        
        // creating an iterator to loop through all enemies
        Iterator<Enemy> enemy_iterator = enemies.iterator();
        
        while (enemy_iterator.hasNext()) {
            Enemy current_enemy = enemy_iterator.next();
            
            // if enemy is dead, give player gold and remove it
            if (!current_enemy.is_alive()) {
                player.add_gold(current_enemy.getGoldValue());
                enemy_iterator.remove();
            } 
            // if enemy reached end, reduce player's life and remove it
            else if (current_enemy.reached_end()) {
                player.reduce_life();
                enemy_iterator.remove();
            }
        }
    }

    public void spawnWave() {
        wave_manager.spawnWave(enemies);
    }
}

