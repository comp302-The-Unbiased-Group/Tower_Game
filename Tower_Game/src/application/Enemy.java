package application;


import java.awt.Point;
import java.util.List;


public abstract class Enemy {
	
    protected int hp;
    protected int max_hp;
    protected double speed;
    protected Point position;
    protected int gold_value;
    protected int path_index;
    protected List<Point> path;
    protected boolean fastMode = false; //for toggle enemy speed in the game

    public Enemy(List<Point> path, int hp, double speed, int gold_value) {
        this.path = path;
        this.hp = hp;
        this.max_hp = hp;
        this.speed = speed;
        this.gold_value = gold_value;
        this.path_index = 0;
        this.position = new Point(path.get(0)); // start at first path point
    }
    
    //controls movement behavior of enemy
    public void move() {
    	
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
        if (distance < speed) {
            position.setLocation(target);
            path_index++;
        } else {
        	// else, move a small step in the direction of the target
            double direction_x = (distance_x / distance); //direction vectors,they are unit vectors
            double direction_y = (distance_y / distance);
            position.translate((int) (direction_x * speed), (int) (direction_y * speed));
        }
    }
    
    public void toggleSpeed() {
    	
    	if (fastMode) {
    		
    		speed /= 2; //slow down the enemy
    		
    	} else { 
    		
    		speed  *= 2; //speed up the enemy	
    	}
    	fastMode = !fastMode; //reset the speed toggle
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

    public Point getPosition() {
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
}