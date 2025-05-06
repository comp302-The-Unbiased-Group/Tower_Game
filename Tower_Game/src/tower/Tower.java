package tower;
import enemy.Enemy;

import java.util.List;

import javafx.scene.image.Image;

public abstract class Tower {
    public int x, y;
    protected int range;
    protected int cost;
    protected int fire_rate;
    
    

    public Tower(int x, int y, int range, int cost, int fire_rate) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.cost = cost;
        this.fire_rate = fire_rate;
    }

    public abstract void attack(List<Enemy> enemies);
    
    public abstract Image getImage();

    public boolean range_tower(Enemy enemy) {
        double dx = enemy.getPosition().x - x;
        double dy = enemy.getPosition().y - y;
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        return distance <= range;
    }
    
    public int getX() {
    	return x; 
    }
    public int getY() {
    	return y; 
    }
}

	
	
	
	
	
	