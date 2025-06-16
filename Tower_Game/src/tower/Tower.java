package tower;
import enemy.Enemy;


import java.util.List;

import UI.GameScreen;
import javafx.scene.image.Image;
import utils.OptionsHandler;

public abstract class Tower {
    public int x, y;
    protected int range;
    protected int cost;
    protected int damage;
    protected double fire_rate;
	protected GameScreen gameScreen;
    
    

    public Tower(int x, int y, int range, int cost, int damage, int fire_rate, GameScreen gameScreen) {
        this.x = x;
        this.y = y;
        this.range = range;
        this.cost = cost;
        this.damage = damage;
        this.fire_rate = fire_rate;
        this.gameScreen = gameScreen;
    }

    public void setGameScreen(GameScreen gs) {
        this.gameScreen = gs; }
    
    public abstract void attack(List<Enemy> enemies);
    
    public abstract Image getImage();

    public boolean is_enemyInRange(Enemy enemy) {
        double dx = enemy.getPosition().x - x;
        double dy = enemy.getPosition().y - y;
        double distance = Math.sqrt((dx * dx) + (dy * dy));
        return distance <= range;
    }

    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }


    public int getX() {
    	return x; 
    }
    public int getY() {
    	return y; 
    }
    public int getRange() {
    	return range; 
    	}
    public int getDamage() { 
    	return damage; 
    	}
    public double getFire_rate() {
		return fire_rate;
	}

	public void setFire_rate(double fire_rate) {
		this.fire_rate = fire_rate;
	}
	
	
	
	 public void setRange(int range) {
		 this.range = range;
		 }
	 
	 public static int calculateRange(int baseRange) {
		    String rangeOption = OptionsHandler.getInstance().getTowerRange();
		    if (rangeOption == null) 
		    	
		    	return baseRange;
		    
		    return switch (rangeOption) {
		        case "Short" -> (int)(baseRange * 0.75);
		        case "Long" -> (int)(baseRange * 1.3);
		        default -> baseRange;
		    };
		}




}

	
	
	
	
	
	