package tower;
import enemy.Enemy;
import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;
import java.awt.geom.Point2D;




public class tower_Artillery extends Tower {
	
    
    private long last_hit_time = 0;
    
    public tower_Artillery(int x, int y, int range, int cost, int damage, int fire_rate, GameScreen gameScreen) {
        super(x, y, range, cost, damage, fire_rate, gameScreen);
    }

    public tower_Artillery(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.0 * tileWidth), 50, 30, 1, gameScreen);
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
                if (enemy.path_index > maxPathIndex || (enemy.path_index == maxPathIndex && enemy.getProgressOnCurrentSegment() > maxSegmentProgress)) {
                    target = enemy;
                    maxPathIndex = enemy.path_index;
                    maxSegmentProgress = enemy.getProgressOnCurrentSegment();
                }
            }
        }

        if (target != null) {
            Point2D.Double start = new Point2D.Double(x + 32, y + 32);
            Image img = new Image("file:Tower_Game/Effects/bomb.png.png");
            int projectile_damage = this.getDamage();
            projectile p = new projectile(start, target, 12.0, projectile_damage, img, "artillery", enemies, this);
            gameScreen.addProjectile(p);
            last_hit_time = now;
        }
    }

    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_bomb256.png");
    }

}

   

