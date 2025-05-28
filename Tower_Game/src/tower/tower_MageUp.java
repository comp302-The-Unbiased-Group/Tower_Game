package tower;
import java.awt.geom.Point2D;
import enemy.Enemy;
import enemy.enemy_Knight;

import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;



public class tower_MageUp extends tower_Mage {
	
	private long last_hit_time = 0;
	
    public tower_MageUp(int x, int y,int tileWidth, GameScreen gameScreen) {
    	super(x, y, tileWidth, gameScreen);
        this.range = (int)(2.0 * tileWidth); //same as level 1
        this.fire_rate = 1; //same as level 1
        this.cost = 100;
    }

    
    @Override
    public void attack(List<Enemy> enemies) {
    	
        long now = System.nanoTime();
        
        if ((now - last_hit_time) < (1_000_000_000 / fire_rate)) 
        	return;

        for (Enemy enemy : enemies) {
        	
            if (enemy.is_alive() && is_enemyInRange(enemy)) {
            	
                Point2D.Double start = new Point2D.Double(x + 32, y + 32);
                Image img = new Image("file:Tower_Game/Effects/fireball2.png");
                int projectile_damage = this.getDamage();
                projectile p = new projectile(start, enemy, 14.0, projectile_damage, img, "mage", null,this);
                if (enemy instanceof enemy_Knight) {
                    enemy.applySlow(4000);
                }
                gameScreen.addProjectile(p);
                last_hit_time = now;
                if (enemy.is_alive()){
                    if(Math.random() < 0.03) {
                        enemy.teleportback();
                    }}
                break;
            }
        }
    }
    
    @Override
    public Image getImage() {
        return new Image("file:Tower_Game/Towers/mage up.png");
    }
}