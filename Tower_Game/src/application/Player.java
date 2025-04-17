package application;

public class Player {
    private int gold;
    private int life;

    public Player(int startingGold, int startingLife) {
        this.gold = startingGold;
        this.life = startingLife;
    }
    
    public int getGold() {
		return gold;
	}
    
    public void setGold(int gold) {
		this.gold = gold;
	}
	
	public int getLife() {
		return life;
	}
	
	public void setLife(int life) {
		this.life = life;
	}
	
	public void addGold(int amount) {
		this.gold += amount;
	}

	public boolean spendGold(int amount) {
		if (amount <= gold) {
			gold -= amount;
			return true;
		}
		return false;
	}
	
	  public void takeDamage(int amount) {
	        life -= amount;
	        if (life < 0) life = 0;
	    }
	  
	  public boolean isAlive() {
	        return life > 0;
	    }
	  
	  public boolean isGameOver() {
	        return life <= 0;
	    }
	  
	  public void reset() {
	        this.gold = 1000; // or any other starting value
	        this.life = 20; // or any other starting value
	    }
}