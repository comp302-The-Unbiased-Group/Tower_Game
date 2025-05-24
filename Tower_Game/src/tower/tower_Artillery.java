package tower;
import enemy.Enemy;
import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;
import java.awt.geom.Point2D;




public class tower_Artillery extends Tower {
	
    private int aoe_radius = 50;
    private long last_hit_time = 0;

    public tower_Artillery(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.0 * tileWidth), 60, 5, 1, gameScreen);
    }
    
  
    @Override
    public void attack(List<Enemy> enemies) {
        long now = System.nanoTime();
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) return;

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && is_enemyInRange(enemy)) {
                Point2D.Double start = new Point2D.Double(x + 32, y + 32);
                Image img = new Image("file:Tower_Game/Effects/bomb.png.png");
                projectile p = new projectile(start, enemy, 12.0, 15, img, "artillery", enemies,this);
                gameScreen.addProjectile(p);
                last_hit_time = now;
                break;
            }
        }
    }
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_bomb256.png");
    }

}

   

