package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;
import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_Mage extends Tower {
	
	private long last_hit_time = 0;
	
    public tower_Mage(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, (int)(2.0 * tileWidth), 60, 2, 1, gameScreen);
    }

    
    @Override
    public void attack(List<Enemy> enemies) {
    	
        long now = System.nanoTime();
        
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) return;

        for (Enemy enemy : enemies) {
        	
            if (enemy.is_alive() && is_enemyInRange(enemy)) {
            	
                Point2D.Double start = new Point2D.Double(x + 32, y + 32);
                Image img = new Image("file:Tower_Game/Effects/fireball.png.png");
                projectile p = new projectile(start, enemy, 14.0, 8, img, "mage", null,this);
                gameScreen.addProjectile(p);
                last_hit_time = now;

                if (enemy.is_alive()){
                   if(Math.random() < 0.33) {
                    enemy.teleportback();
                }}

                break;
            }
        }
    }
    
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/Tower_spell256.png");
    }
}