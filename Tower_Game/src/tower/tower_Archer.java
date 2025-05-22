package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;

import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_Archer extends Tower {

    private long last_hit_time = 0;  // track last time tower fire

    public tower_Archer(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.0 * tileWidth), 60, 2, 1, gameScreen);
    }
    

    @Override
    public void attack(List<Enemy> enemies) {
    	
        long now = System.nanoTime();
        
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) return;

        for (Enemy enemy : enemies) {
            if (enemy.is_alive() && range_tower(enemy)) {
                Point2D.Double start = new Point2D.Double(x + 32, y + 32);
                Image img = new Image("file:Tower_Game/Effects/arrow.png"); 
                projectile p = new projectile(start, enemy, 18.0, 10, img, "archer", null);
                gameScreen.addProjectile(p);
                last_hit_time = now;
                break;
            }
        }
    }
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_archer256.png"); 
    }
}