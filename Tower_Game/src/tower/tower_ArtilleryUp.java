package tower;
import enemy.Enemy;
import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;
import java.awt.geom.Point2D;




public class tower_ArtilleryUp extends tower_Artillery {
	
    
    private long last_hit_time = 0;

    public tower_ArtilleryUp(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.4 * tileWidth), 100, 18, 1, gameScreen);  //20% larger range and 20% more damage
        
    }
    
  
    @Override
    public void attack(List<Enemy> enemies) {
        long now = System.nanoTime();
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) return;

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && is_enemyInRange(enemy)) {
                Point2D.Double start = new Point2D.Double(x + 32, y + 32);
                Image img = new Image("file:Tower_Game/Effects/bomb.png.png");
                int projectile_damage = this.getDamage();
                projectile p = new projectile(start, enemy, 12.0, projectile_damage, img, "artillery", enemies,this);
                gameScreen.addProjectile(p);
                last_hit_time = now;
                break;
            }
        }
    }
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/artillery up.png");
    }

}

   

