package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;

import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_ArcherUp extends tower_Archer {

    private long last_hit_time = 0;  // track last time tower fire

    public tower_ArcherUp(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, tileWidth, gameScreen);
    	this.range = (int)(1.5 * this.range);  // 50% range increase
        this.fire_rate = 2 * this.fire_rate;
        this.cost = 100;
    }
    

    @Override
    public void attack(List<Enemy> enemies) {
        long now = System.nanoTime();
        
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) return;

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && is_enemyInRange(enemy)) {
                Point2D.Double start = new Point2D.Double(x + 32, y + 32);
                Image img = new Image("file:Tower_Game/Effects/arrow.png"); 
                int projectile_damage = this.getDamage();
                projectile p = new projectile(start, enemy, 18.0, projectile_damage, img, "archer", null,this);
                gameScreen.addProjectile(p);
                last_hit_time = now;
                break;
            }
        }
    }
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/archer up.png"); 
    }
}