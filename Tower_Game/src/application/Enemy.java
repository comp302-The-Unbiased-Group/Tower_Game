package application;


public class Enemy {
	
	int armor;
	int speed;
	int health;
	
	
	
	 public Enemy(int armor, int speed, int health) {
		 
	        this.armor = armor;
	        this.speed = speed;
	        this.health = health;
	    }
	
	 
	public int getArmor() {
		return armor;
	}
	
	
	public void setArmor(int armor) {
        this.armor = armor;
    }
	
	
	public int getSpeed() {
        return speed;
    }
	
	
	 public void setSpeed(int speed) {
	        this.speed = speed;
	    }
	 
	 
	 public int getHealth() {
	        return health;
	    }

	 
	 public void setHealth(int health) {
	        this.health = health;
	    }
	
	 
	 public boolean isDead() {  
		 return health <= 0;
	 }
	 
} 