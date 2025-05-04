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
    private int enemies_killed = 0;


    // listener interfaces
    private GameOverListener gameOverListener;
    private EndGameStatsHandler endGameStatsHandler;

    public void set_go_Listener(GameOverListener listener) {
        this.gameOverListener = listener;
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
    }

    public void updateGame(double speedMultiplier) {
        wave_manager.update(enemies);

        for (Enemy enemy : enemies) {
            enemy.move(speedMultiplier);
        }

        for (Tower tower : towers) {
            tower.attack(enemies);
        }

        Iterator<Enemy> enemy_iterator = enemies.iterator();

        while (enemy_iterator.hasNext()) {
            Enemy current_enemy = enemy_iterator.next();

            if (!current_enemy.is_alive()) {
                player.add_gold(current_enemy.getGoldValue());
                enemies_killed++;
                enemy_iterator.remove();
            } else if (current_enemy.reached_end()) {
                player.reduce_life();
                enemy_iterator.remove();

                if (player.get_lives() <= 0 && gameOverListener != null) {
                    gameOverListener.onGameOver();
                }
            }
            else if (enemy_iterator.hasNext() == false && wave_manager.check_if_last_wave()) {

                    endGameStatsHandler.onVictory(enemies_killed);

            }
        }
    }


}
