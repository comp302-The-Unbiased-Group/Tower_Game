package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;

import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_Archer extends Tower {

    private long last_hit_time = 0;  // track last time tower fire
    
    public long getLast_hit_time() {
		return last_hit_time;
	}

	public void setLast_hit_time(long last_hit_time) {
		this.last_hit_time = last_hit_time;
	}
	
	public tower_Archer(int x, int y, int range, int cost, int damage, double fire_rate, GameScreen gameScreen) {
        super(x, y, range, cost, damage, fire_rate, gameScreen);
    }

	public tower_Archer(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, Tower.calculateRange((int)(3.0 * tileWidth)), 50, 7, 1.2, gameScreen);
    }
    
    /**
     * Requires: enemies is not null, gameScreen is initialized, fire_rate > 0
     * Modifies: this.last_hit_time, gameScreen (adds a projectile if an enemy is in range)
     * Effects:
     * - If sufficient time has passed since last attack and there is at least one alive enemy in range,
     *   creates a projectile targeting that enemy adds it to gamescreen and updates last_hit_time.
     * - Do nothing if no enemies are in range or if fire_rate cooldown has not passed.
     */
    @Override
    public void attack(List<Enemy> enemies) {
    	
        long now = System.nanoTime();
        
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) 
        	return;
        
        Enemy target = null;
        int maxPathIndex = -1;
        double maxSegmentProgress = -1;
        
        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && is_enemyInRange(enemy)) {
                int pi = enemy.path_index;
                double prog = enemy.getProgressOnCurrentSegment();

                if (pi > maxPathIndex || (pi == maxPathIndex && prog > maxSegmentProgress)) {
                    target = enemy;
                    maxPathIndex = pi;
                    maxSegmentProgress = prog;
                }
            }
        }

        if (target != null) {
            Point2D.Double start = new Point2D.Double(x + 32, y + 32);
            Image img = new Image("file:Tower_Game/Effects/arrow.png");
            int projectile_damage = this.getDamage();
            projectile p = new projectile(start, target, 18.0, projectile_damage, img, "archer", null, this);
            gameScreen.addProjectile(p);
            last_hit_time = now;
        }
    }
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_archer256.png"); 
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
