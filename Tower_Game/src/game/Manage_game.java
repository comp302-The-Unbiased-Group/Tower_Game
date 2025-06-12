package game;
import enemy.Enemy;
import enemy.Enemy_Healer;
import enemy.enemy_Knight;
import tower.Tower;
import UI.GameOverListener;
import utils.EndGameStatsHandler;
import utils.GoldDropHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Manage_game {
    private Map map;
    public Player player;
    private List<Tower> towers = new ArrayList<>();
    public List<Enemy> enemies = new ArrayList<>();
    private TrackEnemyWave wave_manager = new TrackEnemyWave();
    private int enemies_killed = 0;
    private int maxlives = 0;
    private boolean game_ended = false;



    // listener interfaces
    private GameOverListener gameOverListener;
    private EndGameStatsHandler endGameStatsHandler;
    private GoldDropHandler goldDropHandler;

    public void set_go_Listener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    public void set_gold_handler(GoldDropHandler goldhandler) {
        this.goldDropHandler = goldhandler;
    }

    public void set_gs_handler(EndGameStatsHandler handler) {
        this.endGameStatsHandler = handler;
    }
    public void initialize_player(int initialGold, int initialLives) {
        player = new Player(initialGold, initialLives);
    }

    public void startGame(Map map, int gold,int lives) {
        this.map = map;
        initialize_player(gold, lives);
        wave_manager.setupWaves(map.get_path());
        this.maxlives = lives;
    }

    public void updateGame(double speedMultiplier, double tileSize) {
    	if (!wave_manager.check_if_last_wave()) {
            wave_manager.update(enemies);
        }

        for (Enemy enemy : enemies) {
            if (enemy instanceof enemy_Knight) {
                ((enemy_Knight) enemy).move(speedMultiplier, enemies, tileSize);
            } else {
                enemy.move(speedMultiplier);
            }
            if (enemy instanceof Enemy_Healer) {
                ((Enemy_Healer) enemy).heal(enemies,tileSize);
            }
        }

        for (Tower tower : towers) {
            tower.attack(enemies);
        }

        Iterator<Enemy> enemy_iterator = enemies.iterator();

        while (enemy_iterator.hasNext()) {
            Enemy current_enemy = enemy_iterator.next();

            double enemyX = current_enemy.getPosition().x;
            double enemyY = current_enemy.getPosition().y;

            if (!current_enemy.is_alive()) {
                player.add_gold(current_enemy.getGoldValue());
                enemies_killed++;

                if (Math.random() < 0.20 && goldDropHandler != null) {

                    int max = 25; // half of level 1 archer tower
                    int goldAmount = 2 + (int)(Math.random() * (max));

                    goldDropHandler.droping_gold(goldAmount, enemyX, enemyY);
                }
                enemy_iterator.remove();
            } else if (current_enemy.reached_end()) {
                player.reduce_life();
                enemy_iterator.remove();

                if (player.get_lives() <= 0 && gameOverListener != null) {
                    gameOverListener.onGameOver();
                    return;
                }
            }

        }

        if (!game_ended && wave_manager.check_if_last_wave() && enemies.isEmpty() && player.get_lives() > 0 && endGameStatsHandler != null) {
        		game_ended = true;
        	    endGameStatsHandler.onVictory(enemies_killed);
        	}

    }

 public int get_maxlives(){
        return maxlives;
 }
 public Player getPlayer(){
        return player;
 }

}
