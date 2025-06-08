package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;
import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_Mage extends Tower {
	
	private long last_hit_time = 0;
	
    public tower_Mage(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.0 * tileWidth), 50, 25, 1, gameScreen);
    }

    
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
            Image img = new Image("file:Tower_Game/Effects/fireball1.png");
            int projectile_damage = this.getDamage();
            projectile p = new projectile(start, target, 14.0, projectile_damage, img, "mage", null, this);
            gameScreen.addProjectile(p);
            last_hit_time = now;

            
        }
    }
    
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_spell256.png");
    }
}