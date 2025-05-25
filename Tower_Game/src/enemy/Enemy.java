package enemy;


import java.awt.Point;
import java.util.List;
import java.awt.geom.Point2D;


public abstract class Enemy {
	
    protected int hp;
    protected int max_hp;
    protected double base_speed;
    protected Point2D.Double position;
    protected int gold_value;
    protected int path_index;
    protected List<Point> path;
    protected boolean fastMode = false; //for toggle enemy speed in the game
    private boolean slowed_enemy = false;
    private long slowed_end_time = 0;


    public Enemy(List<Point> path, int hp, double base_speed, int gold_value) {
        this.path = path;
        this.hp = hp;
        this.max_hp = hp;
        this.base_speed = base_speed;
        this.gold_value = gold_value;
        this.path_index = 0;
        this.position = new Point2D.Double(path.get(0).x, path.get(0).y);
    }
    
    //controls movement behavior of enemy
    public void move(double speedMultiplier) {
    	
    	// if reached the last point, stop moving
    	boolean reached_last_point = (path_index + 1 >= path.size());
        if ( reached_last_point) {
        	return;
        }
        
        // get next target point
        Point target = path.get(path_index + 1);
        
        // calculate direction vector (x and y direction distances)
        double distance_x = target.x - position.x;
        double distance_y = target.y - position.y;
        
        // Euclidean distance to target
        double squared_x = distance_x * distance_x;
        double squared_y = distance_y * distance_y;

        double distance = Math.sqrt(squared_x + squared_y);
        
     // if close enough, snap to target and move to next path point
        if (distance < getSpeed() * speedMultiplier) {
            position.setLocation(target);
            path_index++;
        } else {
        	// else, move a small step in the direction of the target
            double direction_x = (distance_x / distance); //direction vectors,they are unit vectors
            double direction_y = (distance_y / distance);
            position.x += direction_x * (getSpeed() * speedMultiplier); 
            position.y += direction_y * (getSpeed() * speedMultiplier); 
        }
    }
    
   

    public void take_damage(int damage) {
        hp -= damage;
    }

    public boolean is_alive() {
        return hp > 0;
    }

    public boolean reached_end() {
        return path_index >= path.size() - 1;
    }

    public Point2D.Double getPosition() { 
        return position;
    }

    public int getGoldValue() {
        return gold_value;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return max_hp;
    }
    
    public void setSpeed(double newSpeed) {
        this.base_speed = newSpeed;
    }
    
    public boolean hasSynergy() {
        return false; // By default, enemies have no synergy
    }
    public double getSpeed() {
        double effective_speed = base_speed;
        if (isSlowed()) 
        	effective_speed *= 0.8;
        return effective_speed;
    }


    
    public boolean isSlowed() {
        if (slowed_enemy && System.currentTimeMillis() > slowed_end_time) {
            slowed_enemy = false;
        }
        return slowed_enemy;
    }
    
    public void applySlow(long durationMillis) {
    	if (!slowed_enemy) {
    		 slowed_enemy = true;
    		 slowed_end_time = System.currentTimeMillis() + durationMillis;
        }
    	else {
    		slowed_end_time = System.currentTimeMillis() + durationMillis;
    	}
    }


    // return to starting path index
    public void teleportback() {
        this.position = new Point2D.Double(path.get(0).x, path.get(0).y);
        this.path_index = 0;
    }
}